package com.agg.service;

import com.agg.config.Dictionary;
import org.languagetool.JLanguageTool;
import org.languagetool.language.AmericanEnglish;
import org.languagetool.language.Chinese;
import org.languagetool.rules.RuleMatch;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class LanguageCheck {
    public static int getLanguage(String str) {
        //去除标点符号
        str = str.replaceAll("\\pP|\\pS", "");
        //去除空格
        str = str.replaceAll(" ", "");
        /*
        英文返回1
        中文返回0
        中英混杂按中文处理
        目的是为了防止类似“项目采用Springboot框架”被误判为英文
        而英文叙述中不会出现中文
        返回类型为int可以进行语言种类上的扩展
        */
        if (str.matches("^[A-Za-z0-9]+$")) {
            return 1;
        } else {
            return 0;
        }
    }

    /*判断是否为标点符号*/
    public static boolean isPunctuation(char c) {
        String str = String.valueOf(c);
        return str.matches("\\pP|\\pS");
    }

    /*判断是否中文标点*/
    public static boolean isChinesePunctuation(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (c == '%' || c == '\\' || c == '{' || c == '}' || c == '/' || c == '-' ||
                c == '+' || c == '*' || c == '~' || c == '=' || c == '|' || c == '[' ||
                c == ']' || c == '@' || c == '#' || c == '(' || c == ')' || c == '&' ||
                c == '$' || c == '_' || c == '<' || c == '>') {
            return true;
        }
        if (ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_FORMS
                || ub == Character.UnicodeBlock.VERTICAL_FORMS) {
            return true;
        } else {
            return false;
        }
    }

    /*中文标点检查*/
    public static void zhPunctuationCheck(String str, int row) {
        for (int i = 0; i < str.length(); i++) {
            if (isPunctuation(str.charAt(i)) && !isChinesePunctuation(str.charAt(i))) {
                System.out.println("zh Potential error at row " + row + " characters " + i + "-" + (i + 1)
                        + ": The character '" + str.charAt(i) + "' is not a standard punctuation.");
                Dictionary.languageResult.add("zh Potential error at row " + row + " characters " + i + "-" + (i + 1)
                        + ": The character '" + str.charAt(i) + "' is not a standard punctuation.");
            }
        }
    }

    /*保留词处理*/
    public static List<String> dealReservedWords(List<String> passage) {
        List<String> newp = new ArrayList<String>();
        for (int i = 0; i < passage.size(); i++) {
            String temp = passage.get(i);
            for (int j = 0; j < Dictionary.reservedWords.size(); j++) {
                String word = Dictionary.reservedWords.get(j);
                String replace = "";
                if (temp.contains(word)) {
                    for (int a = 0; a < word.length(); ++a) {
                        replace += " ";
                    }
                    temp = temp.replace(word, replace);
                }
            }
            newp.add(temp);
        }
        return newp;
    }

    /*语法、标点检查*/
    public static void grammarCheck(List<String> passage) throws IOException {
        JLanguageTool langTool_zh = new JLanguageTool(new Chinese());
        JLanguageTool langTool_en = new JLanguageTool(new AmericanEnglish());
        for (int i = 0; i < passage.size(); i++) {
            String lantest = new String();
            lantest = passage.get(i);
            if (lantest.startsWith("%")) {
                continue;
            }
            /*判断中英文*/
            if (getLanguage(lantest) == 1) {
                /*英文语法*/
                List<RuleMatch> matches_en = langTool_en.check(passage.get(i));
                for (RuleMatch match : matches_en) {
                    boolean ifReserve = false;
                    if (match.getMessage().contains("you repeated a whitespace")) {
                        continue;
                    }
                    if (Dictionary.reservedWords.contains(lantest.substring(match.getFromPos() , match.getToPos()))) {
                        continue;
                    }
//                    for (int j = 0; j < Dictionary.reservedWords.size(); j++) {
//                        if (lantest.substring(match.getFromPos() - 1, match.getToPos()) == Dictionary.reservedWords.get(j)) {
//                            ifReserve = true;
//                            break;
//                        }
//                    }

                    System.out.println("en Potential error at row " + (i + 1) + " characters " +
                            match.getFromPos() + "-" + match.getToPos() + "   [*  " + lantest.substring(match.getFromPos(), match.getToPos()) + "  *] " + ": " +
                            match.getMessage());
                    Dictionary.languageResult.add("en Potential error at row " + (i + 1) + " characters " +
                            match.getFromPos() + "-" + match.getToPos() + "   [*  " + lantest.substring(match.getFromPos(), match.getToPos()) + "  *] " + ": " +
                            match.getMessage());
                    String suggestion = match.getSuggestedReplacements().toString();
                    suggestion = suggestion.substring(0, (suggestion.length() < 100 ? suggestion.length() : 100));

                    System.out.println("Suggested correction(s): " +
                            suggestion);
                    Dictionary.languageResult.add("Suggested correction(s): " +
                            suggestion);

                }
            } else {
                /*中文语法*/
                List<RuleMatch> matches_zh = langTool_zh.check(passage.get(i));
                for (RuleMatch match : matches_zh) {
                    System.out.println("zh Potential error at row " + (i + 1) + " characters " +
                            match.getFromPos() + "-" + match.getToPos() + "   [*  " + lantest.substring(match.getFromPos(), match.getToPos()) + "  *] " + ": " +
                            match.getMessage());
                    Dictionary.languageResult.add("zh Potential error at row " + (i + 1) + " characters " +
                            match.getFromPos() + "-" + match.getToPos() + "   [*  " + lantest.substring(match.getFromPos(), match.getToPos()) + "  *] " + ": " +
                            match.getMessage());
                    System.out.println("Suggested correction(s): " +
                            match.getSuggestedReplacements());
                    Dictionary.languageResult.add("Suggested correction(s): " +
                            match.getSuggestedReplacements());
                }
                /*中文标点*/
                zhPunctuationCheck(passage.get(i), i + 1);
            }
        }
    }

}
