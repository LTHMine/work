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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    @RequestMapping("/homew-list")
    public ModelAndView homew_list(PageRequest pageQuery,String sort){
        if(sort!=null && sort.equals("null")){
            sort=null;
        }
        List<Category> cateAll = cateService.findAll();
        ModelAndView modelAndView=new ModelAndView("/admin/homew");
        if (pageQuery.getPageNum()==0)
            pageQuery.setPageNum(1);
        if (pageQuery.getPageSize()==0)
            pageQuery.setPageSize(8);
        PageResult page = homewService.findPage(pageQuery,sort);
        if (page.getPageNum() > page.getTotalPages())
            pageQuery.setPageNum(page.getTotalPages());
        page = homewService.findPage(pageQuery,sort);
        List<Homew> all= (List<Homew>) page.getContent();

        for (Homew homew : all) {
            Category cate = cateService.findById(Integer.parseInt(homew.getCategory()));
            homew.setCategory(cate.getCategory());
            homew.setHome_status(homew.getHome_status().equals("1")?"已启用":"已停用");
        }
        modelAndView.addObject("cateAll",cateAll);
        modelAndView.addObject("all",all);
        modelAndView.addObject("len",all.size());
        modelAndView.addObject("page",page);
        return modelAndView;
    }

    /**
     * 更改发布作业的状态
     */
    @RequestMapping("/homew_status")
    @ResponseBody
    public uploadJson change_status(Integer id,Integer status){
        Homew byId = homewService.findById(id);
        byId.setHome_status(status.toString());
        homewService.updateHomew(byId);
        uploadJson json=new uploadJson();
        json.setCode("1");
        json.setMsg("修改成功");
        return json;
    }

    /**
     * 打开增加已发布作业页面
     */
    @RequestMapping("/homew-add")
    public ModelAndView homew_add(){
        ModelAndView modelAndView=new ModelAndView("/admin/homew-add");
        List<Category> CateAll = cateService.findAll();
        modelAndView.addObject("CateAll",CateAll);
        return modelAndView;
    }

    /**
     * 作业上传
     */
    String path=null;
    @RequestMapping("/uploadFile")
    @ResponseBody
    public uploadJson uploadFile(HttpServletRequest request) throws IOException {
        uploadJson json=new uploadJson();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile multiFile = multipartRequest.getFile("file");
        String oldfilename = multiFile.getOriginalFilename(); //获取文件名称
        String leftPath = "C:/workPath/";  //服务器环境
//        String leftPath = "/Users/wanan/";
        path=leftPath+oldfilename;
        File file = new File(leftPath, oldfilename);
        multiFile.transferTo(file); //保存文件
        json.setMsg(oldfilename);
        json.setCode( "成功");
        System.out.println(oldfilename);


        return json;
    }

    /**
     * 新增作业数据
     */
    @RequestMapping("/homew-insert")
    @ResponseBody
    public uploadJson homew_insert(String uploadName,String homework,String category){
        uploadJson json=new uploadJson();
        Homew homew=new Homew();
        homew.setHome_status("1"); //状态
        homew.setCategory(category); //类别
        homew.setHomework(homework); //作业名称
        homew.setUploadName(uploadName); //上传者名字
        Date date=new Date();//此时date为当前的时间
        SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd");//设置当前时间的格式，为年-月-日
        homew.setUploadDate(dateFormat.format(date));  //上传时间

        String leftPath = path;
        homew.setHome_path(leftPath); //路径
        System.out.println(homew);
        homewService.insertHomew(homew);
        json.setCode("1");
        json.setMsg("添加成功");
        return json;
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
            pageQuery.setPageSize(8);
        ModelAndView modelAndView=new ModelAndView("/admin/twork");
        PageResult page = tworkService.findPage(pageQuery);
        if (page.getPageNum() > page.getTotalPages())
            pageQuery.setPageNum(page.getTotalPages());
        page = tworkService.findPage(pageQuery);
        List<Twork> all= (List<Twork>) page.getContent();
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
