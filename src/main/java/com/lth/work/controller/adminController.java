package com.lth.work.controller;


import com.lth.work.pojo.Student;
import com.lth.work.pojo.studentTable;
import com.lth.work.pojo.uploadJson;
import com.lth.work.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class adminController {

    @Autowired
    private StudentService studentService;

    @RequestMapping("/login") //跳转到登陆页面
    public String admin(){
        return "admin/login";
    }

    @RequestMapping("/toLogin") //登陆请求
    public String login(String username, String password, HttpServletResponse response, HttpServletRequest request){
        Student stu = studentService.findByName(username);
        if (stu==null){
            System.out.println("用户名或密码错误");
        }else if (stu.getPassword().equals(password)){
            request.getSession().setAttribute("users",username);//用户名存入该用户的session 中
            System.out.println("登陆成功");
            return "redirect:/admin/index";
        }else {
            System.out.println("用户名或密码错误");
        }
        return "/admin/login";
    }

    @RequestMapping("/index")
    public String index(){
        return "/admin/index";
    }

    @RequestMapping("/welcome")
    public String welcome(){
        return "/admin/welcome";
    }

    @RequestMapping("/member-list")
    public ModelAndView member_list(){
        ModelAndView modelAndView=new ModelAndView("/admin/member-list");
        List<Student> all = studentService.findAll();
        studentTable stus = new studentTable();
        stus.setCode(0);
        stus.setMsg("查询成功");
        stus.setCount(all.size());
        stus.setData(all);
        modelAndView.addObject("all",all);
        return modelAndView;
    }



    @RequestMapping("/member-list1")
    public String member_list1(){
        return "/admin/member-list1";
    }

    @RequestMapping("/member-add")
    public String member_add(){
        return "/admin/member-add";
    }





    @RequestMapping("/getStu")
    @ResponseBody
    public studentTable getStu(){
        List<Student> all = studentService.findAll();
        studentTable stus = new studentTable();
        stus.setCode(0);
        stus.setMsg("查询成功");
        stus.setCount(all.size());
        stus.setData(all);
        return stus;
    }

    @RequestMapping("/updateStu")
    @ResponseBody
    public uploadJson updateStu(Integer id,String field,String value){
        uploadJson json = new uploadJson();
        studentService.modifyStatus(id,field);
        json.setCode("1");
        json.setMsg("修改成功");
        return json;
    }

    @RequestMapping("/member-del")
    public String member_del(){
        return "/admin/member-del";
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request){
        request.getSession().invalidate();  //清空登陆
        return "/admin/login";
    }

}
