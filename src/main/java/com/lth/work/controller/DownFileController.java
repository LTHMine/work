package com.lth.work.controller;

import com.lth.work.pojo.Homew;
import com.lth.work.service.HomewService;
import com.lth.work.service.StudentService;
import com.lth.work.service.TworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

@Controller
public class DownFileController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private TworkService tworkService;

    @Autowired
    private HomewService homewService;

    @RequestMapping("/downFile") //下载 完成   需要删除或修改，直接放到服务器中会好一点，不用io流
    public void downloadFile(@RequestParam("id")Integer id, @RequestParam("category")Integer category, HttpServletResponse response) throws UnsupportedEncodingException {
        System.out.println("我进方法了");
        Homew works = homewService.findById(id, category);
        String url=works.getHome_path();
        String name=works.getHomework();
        name = name + url.substring( url.indexOf("."),url.length());
        File file=new File(url);
        System.out.println("请求下载作业");
        if (file.exists()) {
            // 配置文件下载
            System.out.println(file);
            response.reset();
            response.setHeader("Access-Control-Allow-Origin", "*"); //跨越请求
            response.setContentType("multipart/form-data");
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            // 下载文件能正常显示中文
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(name, "UTF-8"));
            // 实现文件下载
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
//                out.clear();      //清空缓存的内容
//                out=pageContext.pushBody();  //更新PageContext的out属性的内容
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
                System.out.println("Download  successfully!");
//                return "successfully";

            } catch (Exception e) {
                System.out.println("Download  failed!");
//                return "failed";

            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    @GetMapping("/tableFile")
    @ResponseBody
    public List<Homew> tableFile(){
        return homewService.findAll();
    }

}
