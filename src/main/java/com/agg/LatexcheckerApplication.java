package com.agg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//主入口
//@SpringBootApplication：标注这个类是一个springboot的应用  启动类下的所有资源被导入
@SpringBootApplication
public class LatexcheckerApplication {

    public static void main(String[] args) {
        //启动springboot应用
        SpringApplication.run(LatexcheckerApplication.class, args);
    }

}
