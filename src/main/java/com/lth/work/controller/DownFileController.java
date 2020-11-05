package com.lth.work.controller;

import com.lth.work.pojo.Homew;
import com.lth.work.pojo.TableCode;
import com.lth.work.pojo.workTable;
import com.lth.work.service.HomewService;
import com.lth.work.service.StudentService;
import com.lth.work.service.TworkService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class DownFileController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private TworkService tworkService;

    @Autowired
    private HomewService homewService;

    /**
     * 资料下载按钮调用方法
     * @param id
     * @param response
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/downFile")
    public void downloadFile(@RequestParam("id")Integer id, HttpServletResponse response) throws UnsupportedEncodingException {
        System.out.println("我进方法了");
        Homew works = homewService.findById(id);
        String url=works.getHome_path();
        String name=works.getHomework();
        name = name + url.substring( url.indexOf("."),url.length());
        System.out.println(url);
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

    /**
     * 资料下载表格调用接口
     * @return
     */
    @RequestMapping("/tableFile")
    public ModelAndView tableFile() {
        TableCode tableCode = new TableCode();
        ModelAndView modelAndView= new ModelAndView("workDown");

        List<Homew> Homew_all = homewService.findAll(); //获取原始数据

        List<workTable> all = new ArrayList<workTable>(); //存放接口要求的格式数据
        for (Homew homew : Homew_all) {
            workTable work = new workTable();
            work.setId(homew.getId());
            //需要改动
            if(homew.getCategory()==1){
                work.setCategory("Python");
            }else if(homew.getCategory()==2){
                work.setCategory("网页");
            }else if(homew.getCategory()==3) {
                work.setCategory("网安");
            }

//            work.setCategory(homew.getCategory()==1?"Python":"网页");

            work.setSign(homew.getUploadName());
            work.setWorkName(homew.getHomework());

            //名字和上传日期
            work.setSign(homew.getUploadName());
            String date = homew.getUploadDate();
            date=date.substring( date.indexOf("-")+1,date.length());
            work.setUploadDate(date);
            all.add(work);
        }

        tableCode.setCode(0);
        tableCode.setMsg("");
        tableCode.setCount(all.size());
        tableCode.setData(all);
        modelAndView.addObject("all",all);
        return modelAndView;
    }


    @RequestMapping("/tableSearch")
    public ModelAndView tableSearch(@RequestParam(value = "search",required = false,defaultValue="null") String search) {
        boolean sta=true;
        if(search.equals("null")){
            System.out.println("空");
            sta=false;
        }else{
            System.out.println("模糊查询"+search);
        }
        TableCode tableCode = new TableCode();
        ModelAndView modelAndView= new ModelAndView("workDown");

        List<Homew> Homew_all = homewService.findAll(); //获取原始数据

        List<workTable> all = new ArrayList<workTable>(); //存放接口要求的格式数据
        for (Homew homew : Homew_all) {
            workTable work = new workTable();
            work.setId(homew.getId());
            //需要改动
            work.setCategory(homew.getCategory()==1?"Python":"网页");
            work.setSign(homew.getUploadName());
            work.setWorkName(homew.getHomework());

            //名字和上传日期
            work.setSign(homew.getUploadName());
            String date = homew.getUploadDate();
            date=date.substring( date.indexOf("-")+1,date.length());
            work.setUploadDate(date);
            all.add(work);
        }

//        for (workTable workTable : all) {
//            if(workTable.getId() == ){
//
//            }
//        }
        System.out.println(all);
        if(sta) {
            System.out.println("查询");
            all = search(search, all); // 模糊查询
        }
        System.out.println(all);
        tableCode.setCode(0);
        tableCode.setMsg("");
        tableCode.setCount(all.size());
        tableCode.setData(all);
        modelAndView.addObject("all",all);
//        System.out.println(all);
        return modelAndView;
    }



    public List<workTable> search(String name,List<workTable> list){
        List<workTable> results = new ArrayList<workTable>();
        Pattern pattern = Pattern.compile(name);
        for(int i=0; i < list.size(); i++){
            Matcher matcher = pattern.matcher((list.get(i)).getWorkName());
            if(matcher.find()){
                results.add(list.get(i));
            }
        }
        return results;
    }

}
