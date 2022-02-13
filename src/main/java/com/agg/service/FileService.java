package com.agg.service;

import com.agg.config.FileStatic;
import com.agg.config.RespBean;
import com.agg.model.FileSplit;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.UUID;

/**
 * FileName：   FileService
 * Author：     zhanhaoyang
 * Date：       2020/11/2114:13
 * Description：对文件进行处理和调用语法分析的接口都写在这，也在这里调用JPA将进行数据库的数据存储
 * History:
 * <author>  <time>  <version>  <desc>
 */
@Service
public class FileService {
    //TODO FileSaveRepository fileSaveRepository;

    //上传成功返回成功信息，失败返回失败信息
    public RespBean saveWith(MultipartFile[] multipartFiles){
        for (MultipartFile multipartFile : multipartFiles){
            // TODO 在这里添加对文件进行处理与调用接口的代码
            //获取上传文件名,包含后缀
            String originalFilename = multipartFile.getOriginalFilename();
            //获取后缀
            String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
            //保存的文件名
            String dFileName = UUID.randomUUID()+substring;
            //保存路径
            //springboot 默认情况下只能加载 resource文件夹下静态资源文件
            // TODO 改成你自己的静态资源文件路径 or 设置springboot加载文件地址：这个要你自己设置，因为我不知道你电脑情况
            //String path = "F:/latexchecker_back/src/main/resources/static";
            //生成保存文件
            //System.out.println(uploadFile);
            //将上传文件保存到路径
            File uploadFile = new File(FileStatic.FILE_PATH +dFileName);
            try {
                multipartFile.transferTo(uploadFile);
            } catch (IOException e) {
                e.printStackTrace();
                return RespBean.error("出现异常，上传失败");
            }
        }
        return RespBean.ok("上传成功");
    }

    public RespBean analysisResult(){
        //TODO 拆分字符串



        //TODO 这里保存接口传回来的分析文件,这三个null都要根据实际情况去改哈(调用方法去调用你的接口)
        MultipartFile result = null;
        Integer status = null;
        String message = null;

        return new RespBean(status,message,result);
    }

//    private FileSplit splitArticle(String fileName){
//        //TODO 去数据库找出存储的文章ID
//        //String fileSaveName = fileSaveRepository.getFileSaveNameByFileName(fileName);
//
//        //TODO 用找到的存储文件的名字加上FILE_PATH去找到文件并读取到一个字符串内；
//        String fileString = null;
//
//        //TODO 进行拆分操作
//        FileSplit fileSplit = new FileSplit();
//        //TODO 调用dealWithSentence去设定句子的字符串数组
//        dealWithSentence(fileSplit,fileString);
//        //控制台看拆分结果
//        System.out.println(fileSplit.toString());
//
//        return fileSplit;
//    }

    private void dealWithSentence(FileSplit fileSplit, String fileString){
        //TODO 先对传进来的文章的字符串进行拆分处理
        String[] sentenceResult = null;

        fileSplit.setSentenceSplits(sentenceResult);
    }
}
