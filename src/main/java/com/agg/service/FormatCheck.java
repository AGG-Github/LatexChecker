package com.agg.service;

import com.agg.config.Dictionary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class FormatCheck {


    /*学号检查 normal为默认值*/
    public static void stuNumCheck(String str, int row) {
        if (str.startsWith("\\studentnum")) {
            String test = str.substring(str.indexOf("{") + 1, str.indexOf("}"));
            if (test.equals(Dictionary.normalStuNum)) {
                System.out.println("Format error at row " + row + " : The studentnumber should be chenged.");
                Dictionary.formatResult.add("Format error at row " + row + " : The studentnumber should be chenged.");
            }
        }
        return;
    }

    /*中文关键字检查*/
    public static void zhKeywordCheck(String str, int row) {
        if (str.startsWith("\\keywords")) {
            String test = str.substring(str.indexOf("{") + 1, str.indexOf("}"));
            //去除标点符号
            test = test.replaceAll("\\pP|\\pS", "");
            //去除空格
            test = test.replaceAll(" ", "");
            if (LanguageCheck.getLanguage(test) == 1) {
                System.out.println("Format error at row " + row + " : The keywords should be in chinese.");
                Dictionary.formatResult.add("Format error at row " + row + " : The keywords should be in chinese.");
            }
        }
        return;
    }

    /*英文关键字检查*/
    public static void enKeywordCheck(String str, int row) {
        if (str.startsWith("\\englishkeywords")) {
            String test = str.substring(str.indexOf("{") + 1, str.indexOf("}"));
            //去除标点符号
            test = test.replaceAll("\\pP|\\pS", "");
            //去除空格
            test = test.replaceAll(" ", "");
            if (LanguageCheck.getLanguage(test) == 0) {
                System.out.println("Format error at row " + row + " : The keywords should be in english.");
                Dictionary.formatResult.add("Format error at row " + row + " : The keywords should be in english.");
            }
        }
        return;
    }

    /*section检查*/
    public static void sectionCheck(List<String> l) {
        int len = l.size();
        l.add("");
        int sub = 0;
        int subsub = 0;
        int subrow = 0;
        int subsubrow = 0;
        int i = 0;
        while (i < len) {
            String test = new String();
            test = l.get(i);
            if (test.startsWith("\\section")) {
                i++;
                test = l.get(i);
                while (!test.startsWith("\\section") && i < len) {
                    if (test.startsWith("\\subsection")) {
                        i++;
                        subrow = i;
                        sub++;
                        test = l.get(i);
                        while (!test.startsWith("\\subsection") && !test.startsWith("\\section") && i < len) {
                            if (test.startsWith("\\subsubsection")) {
                                i++;
                                subsubrow = i;
                                subsub++;
                                test = l.get(i);
                            } else {
                                i++;
                                test = l.get(i);
                            }

                        }
                        if (subsub == 1) {
                            System.out.println("Format error at row " + subsubrow + " : Please check if it's a single subsubsection");
                            Dictionary.formatResult.add("Format error at row " + subsubrow + " : Please check if it's a single subsubsection");
                        }
                        subsub = 0;
                    } else {
                        i++;
                        test = l.get(i);
                    }

                }
                if (sub == 1) {
                    System.out.println("Format error at row " + subrow + " : Please check if it's a single subsection");
                    Dictionary.formatResult.add("Format error at row " + subrow + " : Please check if it's a single subsection");
                }
                sub = 0;
            } else {
                i++;
                test = l.get(i);
            }
        }
    }

    /*abstract检查，主要针对“paper”这个词的含义*/
    public static void abstractCheck(List<String> l) {
        int i = 0;
        boolean ifcheck = false;
        while (i < l.size()) {
            String test = new String();
            test = l.get(i);
            if (test.startsWith("\\end{englishabstract}")) {
                ifcheck = false;
            }
            if (ifcheck) {
                if (test.indexOf("paper") != -1) {
                    System.out.println("Format error at row " + (i + 1) + " characters " + (test.indexOf("paper") + 1) + "-" + (test.indexOf("paper") + 5) +
                            " : Please check if it means \"thesis\", if so, ues \"thesis\" or \"dissertation\"");
                    Dictionary.formatResult.add("Format error at row " + (i + 1) + " characters " + (test.indexOf("paper") + 1) + "-" + (test.indexOf("paper") + 5) +
                            " : Please check if it means \"thesis\", if so, ues \"thesis\" or \"dissertation\"");
                }
                if (test.indexOf("Paper") != -1) {
                    System.out.println("Format error at row " + (i + 1) + " characters " + (test.indexOf("Paper") + 1) + "-" + (test.indexOf("Paper") + 5) +
                            " : Please check if it means \"Thesis\", if so, ues \"Thesis\" or \"Dissertation\"");
                    Dictionary.formatResult.add("Format error at row " + (i + 1) + " characters " + (test.indexOf("Paper") + 1) + "-" + (test.indexOf("Paper") + 5) +
                            " : Please check if it means \"Thesis\", if so, ues \"Thesis\" or \"Dissertation\"");
                }
            }
            if (test.startsWith("\\begin{englishabstract}")) {
                ifcheck = true;
            }
            i++;

        }

    }

    /*引用检查*/
    public static void refCheck(List<String> l) {
        List<String> label = new ArrayList();
        boolean ifcheck = false;
        for (int i = 0; i < l.size(); i++) {
            String test = l.get(i);
            if (test.startsWith("\\end")) {
                ifcheck = false;
            }
            if (ifcheck) {
                while (test.contains("\\label")) {
                    String lab = test.substring(test.indexOf("{", test.indexOf("\\label")) + 1, test.indexOf("}", test.indexOf("\\label")));
                    label.add(lab);
                    test = test.substring(test.indexOf("\\label") + 6);
                }
            }
            if (test.startsWith("\\begin")) {
                ifcheck = true;
            }
        }
        for (int i = 0; i < l.size(); i++) {
            String test = l.get(i);
            while (test.contains("\\ref")) {
                String ref = test.substring(test.indexOf("{", test.indexOf("\\ref")) + 1, test.indexOf("}", test.indexOf("\\ref")));
                if (!label.contains(ref)) {
                    System.out.println("Format error at row " + (i + 1) + " characters " + (test.indexOf("\\ref") + 1) + "-" + (test.indexOf("}", test.indexOf("\\ref")) + 1) +
                            " : Please check if it's a nonexistent label");
                    Dictionary.formatResult.add("Format error at row " + (i + 1) + " characters " + (test.indexOf("\\ref") + 1) + "-" + (test.indexOf("}", test.indexOf("\\ref")) + 1) +
                            " : Please check if it's a nonexistent label");
                }
                test = test.substring(test.indexOf("\\ref") + 4);
            }
        }
    }

    /*日期年份检查*/
    public static void yearCheck(String str, int row) {
        if (str.startsWith("\\submitdate") || str.startsWith("\\defenddate")) {
            String test = str.substring(str.indexOf("{") + 1, str.indexOf("{") + 5);
            Calendar date = Calendar.getInstance();
            String year = String.valueOf(date.get(Calendar.YEAR));
            if (!test.equals(year)) {
                System.out.println("Format error at row " + row + " : Please check if the year number is wrong.");
                Dictionary.formatResult.add("Format error at row " + row + " : Please check if the year number is wrong.");
            }
        }
        return;
    }

    /*英文标题检查*/
    public static void enTitleCheck(String str, int row) {
        if (str.startsWith("\\englishtitle")) {
            List<String> words = new ArrayList();
            String temp = str.substring(str.indexOf("{") + 1, str.indexOf(" "));
            words.add(temp);
            str = str.substring(str.indexOf(" ") + 1);
            while (str.length() > 0) {
                if (str.contains(" ")) {
                    temp = str.substring(0, str.indexOf(" "));
                    words.add(temp);
                    str = str.substring(str.indexOf(" ") + 1);
                } else {
                    temp = str.substring(0, str.indexOf("}"));
                    words.add(temp);
                    str = str.substring(str.indexOf("}") + 1);
                }
            }
            for (int i = 0; i < words.size(); ++i) {
                String test = words.get(i);
                int ifcapitalize = 0;
                char[] testchar = test.toCharArray();
                if (testchar[0] < 'A' || testchar[0] > 'z' || (testchar[0] > 'Z' && testchar[0] < 'a')) {
                    ifcapitalize = 2;
                    System.out.println("Format warnning at row " + row + " " +
                            ": Please check if the word \"" + test + "\" is wrong wroten.");
                    Dictionary.formatResult.add("Format warnning at row " + row + " " +
                            ": Please check if the word \"" + test + "\" is wrong wroten.");
                    break;
                } else if (testchar[0] >= 'A' && testchar[0] <= 'Z') {
                    ifcapitalize = 1;
                }

                for (int j = 1; j < testchar.length; j++) {
                    if (testchar[j] < 'A' || testchar[j] > 'z' || (testchar[j] > 'Z' && testchar[j] < 'a')) {
                        System.out.println("Format warnning at row " + row + " " +
                                ": Please check if the word \"" + test + "\" is wrong wroten.");
                        Dictionary.formatResult.add("Format warnning at row " + row + " " +
                                ": Please check if the word \"" + test + "\" is wrong wroten.");
                        ifcapitalize = 2;
                        break;
                    }
                    if (testchar[j] >= 'A' && testchar[j] <= 'Z') {
                        System.out.println("Format error at row " + row + " " +
                                ": Please check if the word \"" + test + "\" is wrong wroten.");
                        Dictionary.formatResult.add("Format error at row " + row + " " +
                                ": Please check if the word \"" + test + "\" is wrong wroten.");
                        ifcapitalize = 2;
                        break;
                    }
                }
                if (ifcapitalize == 0) {
                    if (i == 0 || i == words.size() - 1) {
                        System.out.println("Format error at row " + row + " " +
                                ": The first or last word \"" + test + "\" of english title should be capital.");
                        Dictionary.formatResult.add("Format error at row " + row + " " +
                                ": The first or last word \"" + test + "\" of english title should be capital.");
                    } else {
                        String temptest = test.toLowerCase();
                        if (!Dictionary.enLowercaseWords.contains(temptest)) {
                            System.out.println("Format error at row " + row + " " +
                                    ": Please check if the first character of word \"" + test + "\" should be capital.");
                            Dictionary.formatResult.add("Format error at row " + row + " " +
                                    ": Please check if the first character of word \"" + test + "\" should be capital.");
                        }
                    }

                } else if (ifcapitalize == 1) {
                    String temptest = test.toLowerCase();
                    if (Dictionary.enLowercaseWords.contains(temptest)) {
                        System.out.println("Format error at row " + row + " " +
                                ": Please check if the word \"" + test + "\" should be lowercase.");
                        Dictionary.formatResult.add("Format error at row " + row + " " +
                                ": Please check if the word \"" + test + "\" should be lowercase.");
                    }
                }
            }

        }
    }

    /*参考文献检查*/
    public static void citeCheck(List<String> l) {
        List<String> cite = new ArrayList();
        for (int i = 0; i < l.size(); i++) {
            String test = l.get(i);
            while (test.contains("\\cite")) {
                String c = test.substring(test.indexOf("{", test.indexOf("\\cite")) + 1, test.indexOf("}", test.indexOf("\\cite")));
                if (!cite.contains(c)) {
                    cite.add(c);
                }
                test = test.substring(test.indexOf("\\cite") + 5);
            }
        }
        if (cite.size() < 30) {
            System.out.println("Format error: The number of citation in this paper is less than 30.");
            Dictionary.formatResult.add("Format error: The number of citation in this paper is less than 30.");
        }
    }

    /*“如图”引用检查*/
    public static void rutuCheck(String str, int row) {
        String origin = str;
        for (int i = 0; i < Dictionary.rutuCheckList.size(); i++) {
            String temp = Dictionary.rutuCheckList.get(i);
            int len = 0;
            str = origin;
            while (str.indexOf(temp) != -1) {
                String test = str.substring(str.indexOf(temp) + 2,
                        (str.indexOf(temp) + 7 <= str.length() - 1 ? str.indexOf(temp) + 7 : str.length() - 1));
                if (!test.contains("ref")) {
                    System.out.println("Format warnning at row " + row + " characters " +
                            (str.indexOf(temp) + len) + "-" + (str.indexOf(temp) + 7 + len) +
                            " : Please check if it means \"如图所示\", if so, ues \"ref\".");
                    Dictionary.formatResult.add("Format warnning at row " + row + " characters " +
                            (str.indexOf(temp) + len) + "-" + (str.indexOf(temp) + 7 + len) +
                            " : Please check if it means \"如图所示\", if so, ues \"ref\".");
                }
                len += str.indexOf(temp) + 2;
                str = str.substring(str.indexOf(temp) + 2);
            }
        }

    }


    /*表格参数检查*/
    public static void tableCheck(List<String> l) {
        int i = 0;
        boolean ifcheck = false;
        while (i < l.size()) {
            String test = new String();
            test = l.get(i);
            if (test.startsWith("\\end{table}")) {
                ifcheck = false;
            }
            if (ifcheck) {
                if (test.indexOf("\\parindent") != -1) {
                    System.out.println("Format warnning at row " + (i + 1) + " characters " +
                            (test.indexOf("\\parindent") + 1) + "-" + (test.indexOf("\\parindent") + 10) +
                            " : The content in the table cannot be indented in the first line");
                    Dictionary.formatResult.add("Format warnning at row " + (i + 1) + " characters " +
                            (test.indexOf("\\parindent") + 1) + "-" + (test.indexOf("\\parindent") + 10) +
                            " : The content in the table cannot be indented in the first line");
                }
                if (test.indexOf("~\\\\") != -1) {
                    System.out.println("Format warnning at row " + (i + 1) + " characters " +
                            (test.indexOf("~\\\\") + 1) + "-" + (test.indexOf("~\\\\") + 3) +
                            " : There can be no blank rows in the table");
                    Dictionary.formatResult.add("Format warnning at row " + (i + 1) + " characters " +
                            (test.indexOf("~\\\\") + 1) + "-" + (test.indexOf("~\\\\") + 3) +
                            " : There can be no blank rows in the table");
                }
                if (test.indexOf("\\shadowbox") != -1) {
                    System.out.println("Format warnning at row " + (i + 1) + " characters " +
                            (test.indexOf("\\shadowbox") + 1) + "-" + (test.indexOf("\\shadowbox") + 10) +
                            " : Shaded text cannot be used in the table");
                    Dictionary.formatResult.add("Format warnning at row " + (i + 1) + " characters " +
                            (test.indexOf("\\shadowbox") + 1) + "-" + (test.indexOf("\\shadowbox") + 10) +
                            " : Shaded text cannot be used in the table");
                }
                if (test.indexOf("\\specialrule") != -1) {
                    System.out.println("Format warnning at row " + (i + 1) + " characters " +
                            (test.indexOf("\\specialrule") + 1) + "-" + (test.indexOf("\\specialrule") + 11) +
                            " : Please check if the margins of the table meet the standards");
                    Dictionary.formatResult.add("Format warnning at row " + (i + 1) + " characters " +
                            (test.indexOf("\\specialrule") + 1) + "-" + (test.indexOf("\\specialrule") + 11) +
                            " : Please check if the margins of the table meet the standards");
                }
                if (test.indexOf("\\vspace") != -1) {
                    System.out.println("Format warnning at row " + (i + 1) + " characters " +
                            (test.indexOf("\\vspace") + 1) + "-" + (test.indexOf("\\vspace") + 7) +
                            " : Please check if the margins of the table meet the standards");
                    Dictionary.formatResult.add("Format warnning at row " + (i + 1) + " characters " +
                            (test.indexOf("\\vspace") + 1) + "-" + (test.indexOf("\\vspace") + 7) +
                            " : Please check if the margins of the table meet the standards");
                }
            }
            if (test.startsWith("\\begin{table}")) {
                ifcheck = true;
            }
            i++;
        }
    }

    /*表格宽度检查*/
    public static void tableWidthCheck(List<String> l) {
        int i = 0;
        boolean ifcheck1 = false;
        boolean ifcheck2 = false;
        while (i < l.size()) {
            String test = new String();
            test = l.get(i);
            if (test.startsWith("\\end{table}")) {
                ifcheck1 = false;
            }
            if (test.startsWith("\\end{table*}")) {
                ifcheck2 = false;
            }
            if (ifcheck1) {
                if (test.startsWith("\\begin{tabular}")) {
                    String[] nums = test.split("\\D");
                    int sum = 0;
                    for (String s : nums) {
                        if (!s.equals("")) {
                            sum += Integer.parseInt(s);
                        }
                    }
                    if (sum > 150) {
                        System.out.println("Format warnning at row " + (i + 1) +
                                ": The width of the table may exceed the border.");
                        Dictionary.formatResult.add("Format warnning at row " + (i + 1) +
                                ": The width of the table may exceed the border.");
                    }
                }
            }
            if (ifcheck2) {
                if (test.startsWith("\\begin{tabular}")) {
                    String[] nums = test.split("\\D");
                    int sum = 0;
                    for (String s : nums) {
                        if (!s.equals("")) {
                            sum += Integer.parseInt(s);
                        }
                    }
                    if (sum > 150) {
                        System.out.println("Format warnning at row " + (i + 1) +
                                ": The width of the table may exceed the border.");
                        Dictionary.formatResult.add("Format warnning at row " + (i + 1) +
                                ": The width of the table may exceed the border.");
                    }
                }
            }
            if (test.startsWith("\\begin{table}")) {
                ifcheck1 = true;
            }
            if (test.startsWith("\\begin{table*}")) {
                ifcheck2 = true;
            }
            i++;
        }
    }


}