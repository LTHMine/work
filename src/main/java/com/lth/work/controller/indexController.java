package com.lth.work.controller;

import com.lth.work.pojo.Homew;
import com.lth.work.pojo.Twork;
import com.lth.work.pojo.Student;
import com.lth.work.pojo.uploadJson;
import com.lth.work.service.HomewService;
import com.lth.work.service.TworkService;
import com.lth.work.service.StudentService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Controller

public class indexController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private TworkService tworkService;

    @Autowired
    private HomewService homewService;

    @RequestMapping("/test")
    public String test(){
        return "test";
    }


    @RequestMapping("/")
    public String index( HttpServletResponse response) {
        return "redirect:/index";
    }


    @PostMapping("/formSubmit")
    public String  formSubmit(Integer stu_id,Integer category,String stu_name,Integer workL,Integer difficulty,String beiz){
        System.out.println("表单提交成功");
        //修改学生作业状态
        String url=null;
        String work_name = null;
        if(category==1) {
            url="index";
            work_name = "python_status";
        }else if (category==2){
            url="webWork";
            work_name="web_status";
        }
        studentService.modifyStatus(stu_id,work_name); //设置学生状态为已交
        System.out.println("学生状态修改为已交");

        Homew work = homewService.findById(workL, category);
        if(work==null){
            System.out.println("更新难易度失败，未找到数据库作业记录");
        }else {
            //更新难易度
            System.out.println("更新难易度成功");
            if (difficulty == 1)
                work.setEasy(work.getEasy() + 1);
            else if (difficulty == 2)
                work.setNormal(work.getNormal() + 1);
            else if (difficulty == 3)
                work.setDifficulty(work.getDifficulty() + 1);
            homewService.updateHomew(work);
        }

        return "redirect:/"+url;
    }



    @RequestMapping("/index")
    public ModelAndView index(){
        List<Student> students = studentService.findAll();
        List<Integer> stu_id = new ArrayList<Integer>();
        List<String> stu_name = new ArrayList<String>();
        for (Student student : students) {
            stu_id.add(student.getId());
            stu_name.add(student.getName());
        }
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("stuList",students);
        modelAndView.addObject("stu_idList",stu_id);
        modelAndView.addObject("stu_nameList",stu_name);
        return modelAndView;
    }


    @GetMapping("/webWork")
    public ModelAndView webWork(){
        List<Student> students = studentService.findAll();
        List<Integer> stu_id = new ArrayList<Integer>();
        List<String> stu_name = new ArrayList<String>();
        for (Student student : students) {
            stu_id.add(student.getId());
            stu_name.add(student.getName());
        }
        ModelAndView modelAndView = new ModelAndView("webWork");
        modelAndView.addObject("stuList",students);
        modelAndView.addObject("stu_idList",stu_id);
        modelAndView.addObject("stu_nameList",stu_name);
        return modelAndView;
    }


    @GetMapping("/findStuID")
    @ResponseBody
    public Student findById(HttpServletRequest request){
        Integer Id= Integer.valueOf(request.getParameter("data"));
        Student stuById = studentService.findById(Id);
        return stuById;
    }

//    @GetMapping("/findStuName")
//    @ResponseBody
//    public Student findByName(HttpServletRequest request){
//        String name= request.getParameter("data");
//        System.out.println(name);
//        Student stuByName = studentService.findByName(name);
//        return stuByName;
//    }


    @PostMapping("/upload")
    @ResponseBody
    synchronized public uploadJson uploadFile(HttpServletRequest request) throws IOException, JSONException {
        uploadJson json =new uploadJson();
        Integer category= Integer.valueOf(request.getParameter("category"));
        Integer stu_idd= Integer.valueOf(request.getParameter("stu_idd"));
        Integer workL= Integer.valueOf(request.getParameter("workL"));
        String beiz= request.getParameter("beiz");
        System.out.println("作业类型："+category+","+"学生id："+stu_idd+","+"作业代号："+workL+","+"备注："+beiz);
        Twork twork =new Twork();
        twork.setBeiz(beiz);
        twork.setCategory(category);
        twork.setStuID(stu_idd);
        twork.setHomework_id(workL);
        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            //获取下载的文件   C:\第四次作业
            MultipartFile multiFile = multipartRequest.getFile("file");
            String oldfilename = multiFile.getOriginalFilename(); //获取文件名称
            String strh = oldfilename.substring( oldfilename.indexOf("."),oldfilename.length());
            Student stu = studentService.findById(stu_idd);
            String filename = stu_idd+"-"+stu.getName()+strh; //重命名

            String leftPath = "C:\\第四次作业";  //服务器环境
//            String leftPath = "C:\\第四次作业web";  //本地环境
//            String leftPath = "/Users/wanan/Desktop";  //本地环境

            File file = new File(leftPath, filename);
            String path=file.getPath(); //路径
            twork.setWorkPath(path);
            twork.setWorkName(filename);
            multiFile.transferTo(file); //保存文件
            json.setMsg( "上传成功");
            json.setCode( "成功");
            //将作业信息存储到数据库
            List<Twork> tworks = tworkService.findByidCateHome(stu_idd, category, workL);
            Integer stat=1;
            for (Twork tworka : tworks) {
                System.out.println("数据库查到作业记录");
                if(tworka.getStuID()==stu_idd && tworka.getCategory()==category && tworka.getHomework_id()==workL){
                    stat=0;
                }
            }
            if(stat==1)
                tworkService.insert(twork);
            System.out.println(json);
            } catch (Exception e) {
            e.printStackTrace();
            json.setMsg( "上传失败，请重新上传或邮件发送给1445047090@qq.com");
            json.setCode( "失败");
            }
        return json;
        }

    @GetMapping("/check")
    @ResponseBody
    public uploadJson checkTwork(Integer stuID,Integer category,Integer workL){
        uploadJson json = new uploadJson();
        List<Twork> tworks = tworkService.findByidCateHome(stuID, category, workL);
        json.setCode( "成功");
        json.setCode( "不是重复上传");
        for (Twork twork : tworks) {
            System.out.println(twork);
            if(twork.getStuID()==stuID && twork.getCategory()==category && twork.getHomework_id()==workL){
                System.out.println("重复");
                json.setMsg( "你已经上传过作业了，本次为你覆盖上次作业");
                json.setCode( "失败");}
        }
        return json;
    }


    @RequestMapping("/downFile") //下载 完成   需要删除或修改，直接放到服务器中会好一点，不用io流
    public String downloadFile(@RequestParam("id")Integer id,@RequestParam("category")Integer category,HttpServletResponse response) throws UnsupportedEncodingException {
        Homew works = homewService.findById(id, category);
        String url=works.getHome_path();
        String name=works.getHomework();
        File file=new File(url);
        response.reset();
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(name, "UTF-8"));
        response.setHeader("Access-Control-Allow-Origin", "*"); //跨越请求
        response.setContentType("application/force-download"); //下载
        response.setContentType("multipart/form-data");
        try {
            //接下来时构造下载流的常规操作，我也不懂，就照着写吧hhhh
            InputStream inStream = new FileInputStream(url);
            OutputStream os = response.getOutputStream();
            byte[] buff = new byte[1024];
            int len = -1;
            while ((len = inStream.read(buff)) > 0) {
                os.write(buff, 0, len);
            }
            os.flush();
            os.close();
            inStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return "-2";
        }
        return "test";
    }

    @RequestMapping("/litera")
    public String litera(){
        return "litera";
    }


}



