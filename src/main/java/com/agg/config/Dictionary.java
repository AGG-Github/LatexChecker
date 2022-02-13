package com.agg.config;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Dictionary {
    /*源文件路径*/
    public static String filePath;

    static {
        filePath = "C:/IDEA_workspace/latexchecker/src/main/resources/static/";
    }

    /*保留词list*/
    public static List<String> reservedWords = new ArrayList<String>();

    static {
        reservedWords.add("documentclass");
        reservedWords.add("captionsetup");
        reservedWords.add("classification");
        reservedWords.add("udc");
        reservedWords.add("nlctitlea");
        reservedWords.add("nlctitleb");
        reservedWords.add("nlctitlec");
        reservedWords.add("supervisorinfo");
        reservedWords.add("chairman");
        reservedWords.add("reviewera");
        reservedWords.add("reviewerb");
        reservedWords.add("reviewerc");
        reservedWords.add("reviewerd");
        reservedWords.add("title");
        reservedWords.add("titlea");
        reservedWords.add("titleb");
        reservedWords.add("author");
        reservedWords.add("telphone");
        reservedWords.add("email");
        reservedWords.add("studentnum");
        reservedWords.add("grade");
        reservedWords.add("graduateyear");
        reservedWords.add("supervisor");
        reservedWords.add("supervisortelphone");
        reservedWords.add("major");
        reservedWords.add("researchfield");
        reservedWords.add("department");
        reservedWords.add("institute");
        reservedWords.add("submitdate");
        reservedWords.add("defenddate");
        reservedWords.add("date");
        reservedWords.add("englishtitle");
        reservedWords.add("englishauthor");
        reservedWords.add("englishsupervisor");
        reservedWords.add("englishmajor");
        reservedWords.add("englishdepartment");
        reservedWords.add("englishinstitute");
        reservedWords.add("englishdate");
        reservedWords.add("abstracttitlea");
        reservedWords.add("abstracttitleb");
        reservedWords.add("englishabstracttitlea");
        reservedWords.add("englishabstracttitleb");
        reservedWords.add("blind");
        reservedWords.add("newcommand");
        reservedWords.add("begin");
        reservedWords.add("makenlctitle");
        reservedWords.add("maketitle");
        reservedWords.add("makeenglishtitle");
        reservedWords.add("frontmatter");
        reservedWords.add("par");
        reservedWords.add("keywords");
        reservedWords.add("end");
        reservedWords.add("englishkeywords");
        reservedWords.add("vspace");
        reservedWords.add("tableofcontents");
        reservedWords.add("listoffigures");
        reservedWords.add("listoftables");
        reservedWords.add("mainmatter");
        reservedWords.add("chapter");
        reservedWords.add("section");
        reservedWords.add("cite");
        reservedWords.add("item");
        reservedWords.add("centering");
        reservedWords.add("includegraphics");
        reservedWords.add("caption");
        reservedWords.add("label");
        reservedWords.add("subsection");
        reservedWords.add("textwidth");
        reservedWords.add("scriptsize");
        reservedWords.add("toprule");
        reservedWords.add("textbf");
        reservedWords.add("midrule");
        reservedWords.add("bottomrule");
        reservedWords.add("scriptsize");
        reservedWords.add("frac");
        reservedWords.add("subsubsection");
        reservedWords.add("ref");
        reservedWords.add("nocite");
        reservedWords.add("backmatter");
        reservedWords.add("bibliography");
        reservedWords.add("appendix");
        reservedWords.add("backmatter");
        reservedWords.add("noindent");
        reservedWords.add("makelicense");
        reservedWords.add("englishabstract");
        reservedWords.add("tablenotes");
        reservedWords.add("");
        reservedWords.add("");
        reservedWords.add("");
        reservedWords.add("");
        reservedWords.add("");

    }

    /*英文标题小写单词list*/
    public static List<String> enLowercaseWords = new ArrayList<String>();

    static {
        enLowercaseWords.add("a");
        enLowercaseWords.add("an");
        enLowercaseWords.add("the");
        enLowercaseWords.add("to");
        enLowercaseWords.add("above");
        enLowercaseWords.add("after");
        enLowercaseWords.add("along");
        enLowercaseWords.add("among");
        enLowercaseWords.add("as");
        enLowercaseWords.add("at");
        enLowercaseWords.add("below");
        enLowercaseWords.add("but");
        enLowercaseWords.add("by");
        enLowercaseWords.add("down");
        enLowercaseWords.add("for");
        enLowercaseWords.add("front");
        enLowercaseWords.add("in");
        enLowercaseWords.add("into");
        enLowercaseWords.add("of");
        enLowercaseWords.add("on");
        enLowercaseWords.add("onto");
        enLowercaseWords.add("or");
        enLowercaseWords.add("out");
        enLowercaseWords.add("over");
        enLowercaseWords.add("per");
        enLowercaseWords.add("since");
        enLowercaseWords.add("till");
        enLowercaseWords.add("up");
        enLowercaseWords.add("under");
        enLowercaseWords.add("until");
        enLowercaseWords.add("upon");
        enLowercaseWords.add("via");
        enLowercaseWords.add("while");
        enLowercaseWords.add("with");
        enLowercaseWords.add("again");
        enLowercaseWords.add("also");
        enLowercaseWords.add("and");
        enLowercaseWords.add("hence");
        enLowercaseWords.add("if");
        enLowercaseWords.add("so");
        enLowercaseWords.add("than");
        enLowercaseWords.add("that");
        enLowercaseWords.add("thus");
        enLowercaseWords.add("when");
        enLowercaseWords.add("where");
        enLowercaseWords.add("yet");
    }

    /*默认学号*/
    public static String normalStuNum;

    static {
        normalStuNum = "xxxxxxx";
    }

    /*如图所示检查列表*/
    public static List<String> rutuCheckList = new ArrayList<String>();

    static {
        rutuCheckList.add("如图");
        rutuCheckList.add("如上图");
        rutuCheckList.add("如下图");
        rutuCheckList.add("见图");
        rutuCheckList.add("见下图");
        rutuCheckList.add("见上图");
    }

    /*分类缓存检查结果*/
    public static List<String> formatResult = new ArrayList<>();

    static {
        formatResult.add(" ");
        formatResult.add(" ");
        formatResult.add("//////////////////////////////////////////////////////////////////////////////////////");
        formatResult.add("Format check result: ");
        formatResult.add("//////////////////////////////////////////////////////////////////////////////////////");
        formatResult.add(" ");
    }

    public static List<String> languageResult = new ArrayList<>();

    static {
        languageResult.add("//////////////////////////////////////////////////////////////////////////////////////");
        languageResult.add("Language check result: ");
        languageResult.add("//////////////////////////////////////////////////////////////////////////////////////");
        languageResult.add(" ");
    }

}
