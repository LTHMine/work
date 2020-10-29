package com.lth.work;

import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
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

    private static Logger logger= LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
    @Test
    public  void asd() {
        for(int i=0;i<3;i++){
            // 记录trace级别的信息
            logger.trace("log4j2日志输出：This is trace message.");
            // 记录debug级别的信息
            logger.debug("log4j2日志输出：This is debug message.");
            // 记录info级别的信息
            logger.info("log4j2日志输出：This is info message.");
            // 记录error级别的信息
            logger.error("log4j2日志输出：This is error message.");
        }
    }
}


