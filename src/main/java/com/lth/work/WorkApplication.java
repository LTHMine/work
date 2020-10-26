package com.lth.work;

import org.junit.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@MapperScan("com.lth.work.mapper")
public class WorkApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(WorkApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(WorkApplication.class);
    }

    @Test
    public void test(){
                    String oldfilename = "06-李天豪.pdf"; //获取文件名称
            String strh = oldfilename.substring( oldfilename.indexOf("."),oldfilename.length());
        System.out.println(strh);
    }
}


