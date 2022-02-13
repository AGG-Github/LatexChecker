package com.agg.service;

import com.agg.config.Dictionary;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileCheck {
    public static void FileDeal(String username) throws IOException {
        /*多文件处理，合并tex文件*/
        List<String> passage = new ArrayList();
        //StringBuffer buffer = new StringBuffer();
        BufferedReader bf = new BufferedReader(new FileReader(Dictionary.filePath + username + "/" + "sample.tex"));
        String s = null;
        String inputFileName = null;
        while ((s = bf.readLine()) != null) {//使用readLine方法，一次读一行
            if (s.contains("\\input")) {
                do {
                    inputFileName = s.substring(s.indexOf('/') + 1, s.indexOf('}'));
                } while (inputFileName.contains("\\"));
                BufferedReader bfInput = new BufferedReader(new FileReader(Dictionary.filePath + username + "/" + inputFileName));
                String sInput=null;
                while((sInput = bfInput.readLine()) != null){
                    //sInput=sInput.trim();
                    passage.add(sInput);
                }

            } else {
                //s=s.trim();
                passage.add(s);
            }
        }
        /*存储合并文件*/
        String fileName=Dictionary.filePath + username + "/" +"final.txt";
        File file =new File(fileName);
        file.createNewFile();
        String fileInput = new String();
        FileWriter fw=new FileWriter(file);
        for (int i = 0; i < passage.size(); i++){
            fileInput = passage.get(i)+ System.getProperty("line.separator");
            fw.write(fileInput);
        }
        fw.flush();
        fw.close();




        //System.out.println(passage);


        //comment in to use statistical ngram data:
        //langTool.activateLanguageModelRules(new File("/data/google-ngram-data"));

        LanguageCheck lc = new LanguageCheck();
        FormatCheck fc = new FormatCheck();
        /*检查段落问题*/
        fc.sectionCheck(passage);
        /*摘要"the paper"检查*/
        fc.abstractCheck(passage);
        /*引用检查*/
        fc.refCheck(passage);
        /*参考文献检查*/
        fc.citeCheck(passage);
        /*表格参数检查*/
        fc.tableCheck(passage);
        /*表格宽度检查*/
        fc.tableWidthCheck(passage);

        for (int i = 0; i < passage.size(); i++) {
            String formattest = new String();
            formattest = passage.get(i);
            /*过滤注释*/
            if (formattest.startsWith("%")) {
                continue;
            }

            /*学号检查*/
            fc.stuNumCheck(formattest, i + 1);
            /*日期年份检查*/
            fc.yearCheck(formattest, i + 1);
            /*中文关键字检查*/
            fc.zhKeywordCheck(formattest, i + 1);
            /*英文关键字检查*/
            fc.enKeywordCheck(formattest, i + 1);
            /*英文标题检查*/
            fc.enTitleCheck(formattest, i + 1);
            /*“如图”引用检查*/
            fc.rutuCheck(formattest, i + 1);

        }

        /*保留词处理*/
        //passage=lc.dealReservedWords(passage);

        /*语法、标点检查*/
        lc.grammarCheck(passage);

        /*生成检查结果报表*/
        String resultFileName= Dictionary.filePath + username + "/" +"result.txt";
        File resultFile =new File(resultFileName);
        resultFile.createNewFile();
        String resultFileInput = new String();
        FileWriter rfw=new FileWriter(resultFile);
        for (int i = 0; i < Dictionary.languageResult.size(); i++){
            resultFileInput = Dictionary.languageResult.get(i)+ System.getProperty("line.separator");
            rfw.write(resultFileInput);
        }
        rfw.flush();
        for (int i = 0; i < Dictionary.formatResult.size(); i++){
            resultFileInput = Dictionary.formatResult.get(i)+ System.getProperty("line.separator");
            rfw.write(resultFileInput);
        }
        //rfw.flush();


        rfw.close();
    }

}
