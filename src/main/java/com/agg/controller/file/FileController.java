package com.agg.controller.file;

import com.agg.config.Dictionary;
import com.agg.config.FileStatic;
import com.agg.config.RespBean;
import com.agg.service.FileCheck;
import com.agg.service.FileService;
import com.google.common.io.Files;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * FileName：   FileController
 * Author：     agg
 * Date：       2020/11/2114:04
 * Description：这里写与前端文件传输相关的接口
 * History:
 * <author>  <time>  <version>  <desc>
 */
@RestController
@RequestMapping("/file")
@CrossOrigin
public class FileController {
    @Autowired
    private FileService fileService;

    /**
     * @Author: agg
     * @Date: 2020/11/21
     * @Description: 前端上传文件调用该接口，后端执行将文件保存下来的操作（这里暂时没用数据库，用的文件形式的存储）
     * @Params: [multipartFile]
     * @Return: com.agg.config.RespBean
     */
    @PostMapping("/upload")
    public RespBean fileUpload(@RequestBody MultipartFile[] files) {
        return fileService.saveWith(files);
    }

    @PostMapping("/upload2")
    public String upload(@RequestPart("file") MultipartFile file, @RequestPart("username") String username) {
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }
        String fileName = file.getOriginalFilename();
        File f = new File(Dictionary.filePath + username + "/");
        if (!f.exists()) {
            f.mkdirs();
        }
        File dest = new File(f , fileName);
        try {
            file.transferTo(dest);
            return "上传成功";
        } catch (IOException e) {
            e.printStackTrace();
            return "上传失败";
        }
    }


    @GetMapping("/deal")
    public String filedeal(String num) throws IOException {
        System.out.println(num);
        FileCheck.FileDeal(num);
        return "finish!";

    }

    @GetMapping("/download")
    public void fileload(String num, HttpServletResponse response) throws IOException {
        File file = new File(Dictionary.filePath + num + "/"+"result.txt");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Disposition", "attachment; fileName=" + file.getName());
        byte[] bytes = Files.toByteArray(file);
        OutputStream out=new BufferedOutputStream(response.getOutputStream());
        out.write(bytes);
        out.close();
    }

    @GetMapping("/download2")
    public void fileload2(String num, HttpServletResponse response) throws IOException {
        File file = new File(Dictionary.filePath + num + "/"+"final.txt");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Disposition", "attachment; fileName=" + file.getName());
        byte[] bytes = Files.toByteArray(file);
        OutputStream out=new BufferedOutputStream(response.getOutputStream());
        out.write(bytes);
        out.close();
    }
}
