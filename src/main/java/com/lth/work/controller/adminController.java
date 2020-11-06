package com.lth.work.controller;


import com.lth.work.pojo.*;
import com.lth.work.service.CateService;
import com.lth.work.service.HomewService;
import com.lth.work.service.StudentService;
import com.lth.work.service.TworkService;
import com.lth.work.util.PageRequest;
import com.lth.work.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class adminController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private TworkService tworkService;
    @Autowired
    private HomewService homewService;
    @Autowired
    private CateService cateService;

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


    /**
     * 学生列表 静态
     * @return
     */
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


    /**
     * 学生列表 动态
     * @return
     */
    @RequestMapping("/member-list1")
    public String member_list1(){
        return "/admin/member-list1";
    }

    /**
     * 增加学生
     * @return
     */
    @RequestMapping("/member-add")
    public String member_add(){
        return "/admin/member-add";
    }


    /**
     * 增加学生
     * @param id
     * @param name
     * @param password
     * @return
     */
    @RequestMapping("/addStu")
    @ResponseBody
    public uploadJson addStu(Integer id,String name,String password){
        uploadJson json = new uploadJson();
        Student student=new Student();
        student.setId(id);
        student.setName(name);
        student.setPassword(password);
        System.out.println(student);
        studentService.addStudent(student);
        json.setMsg("增加成功");
        json.setCode("1");
        return json;
    }


    /**
     * 查询所有学生
     * @return
     */
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


    /**
     * 修改学生交作业的状态
     * @param id
     * @param field
     * @param value
     * @return
     */
    @RequestMapping("/updateStu")
    @ResponseBody
    public uploadJson updateStu(Integer id,String field,String value){
        uploadJson json = new uploadJson();
        studentService.modifyStatus(id,field);
        json.setCode("1");
        json.setMsg("修改成功");
        return json;
    }

    /**
     * 删除学生
     * @param id
     * @return
     */
    @RequestMapping("/delStu")
    @ResponseBody
    public uploadJson delStu(Integer id){
        System.out.println(id);
        uploadJson json = new uploadJson();
        studentService.delStudent(id);
        json.setCode("1");
        json.setMsg("删除成功");
        return json;
    }



    /**
     * 已发布作业
     * @return
     */
    @RequestMapping("/twork-list")
    public String twork_list(PageRequest pageQuery){
        if (pageQuery.getPageNum()==0)
            pageQuery.setPageNum(1);
        if (pageQuery.getPageSize()==0)
            pageQuery.setPageSize(10);

        List<Homew> all = homewService.findAll();
        return "/admin/twork1";
    }


    /**
     * 已交作业列表
     * @param pageQuery
     * @return
     */
    @RequestMapping("/twork-list1")
    public ModelAndView twork_list1(PageRequest pageQuery){
        if (pageQuery.getPageNum()==0)
            pageQuery.setPageNum(1);
        if (pageQuery.getPageSize()==0)
            pageQuery.setPageSize(10);
        ModelAndView modelAndView=new ModelAndView("/admin/twork");
        PageResult page = tworkService.findPage(pageQuery);
        if (page.getPageNum() > page.getTotalPages())
            pageQuery.setPageNum(page.getTotalPages());
        page = tworkService.findPage(pageQuery);
        List<Twork> all=page.getContent();
        for (Twork twork : all) {
            //将作业id转换成作业名称
            Homew byId = homewService.findById(Integer.parseInt(twork.getHomework_id()));
            twork.setHomework_id(byId.getHomework());
            //将作业类型转换成类型名称
            Category byId1 = cateService.findById(Integer.parseInt(twork.getCategory()));
            twork.setCategory(byId1.getCategory()); //在显示页面上显示作业类型中文名称
        }
        modelAndView.addObject("all",all);
        modelAndView.addObject("page",page);
        modelAndView.addObject("len",all.size());
        return modelAndView;
    }


    @RequestMapping("/logout")
    public String logout(HttpServletRequest request){
        request.getSession().invalidate();  //清空登陆
        return "/admin/login";
    }

}
