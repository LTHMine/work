

/**
 * js动态加载js css文件,可以配置文件后辍，防止浏览器缓存
 * @param {obj} config   加载资源配置
 * @param {string} version  资源后辍配置
 */
function jsCssLoader(config,version) {
    this.css = config.css;
    this.scripts = config.scripts;
    this.head = document.getElementsByTagName('head')[0];

    this.load = function() {
        this.loadCSS();
        this.loadScript();
    }
    this.loadCSS = function() {
        var that = this;
        this.css.forEach(function(csslink) {
            document.write(' ')
        });
    }
    this.loadScript = function() {
        var that = this;
        this.scripts.forEach(function(scriptlink){
            document.write('');
        });
    }
    this.load();
};
jsCssLoader({
    css: [
        'index.css',
    ],
    scripts: [
        'index.js',
    ]
},new Date().getTime());




layui.use('element', function(){
    var element = layui.element;
    //…
});
var $ = document.getElementById;

var file_sta = document.getElementById("file_sta");
var category = document.getElementById("category");
var i =false;
var sta=1;
var formsta=0;
layui.use('form', function(){
    var form = layui.form;
    //下拉框异步刷新
    form.on('select(stu_id)', function(data){
        var $ = layui.$;
        var stu_idd = document.getElementById("stu_idd");
        stu_idd.setAttribute("value",data.value);
        $.ajax({
            type:'get',
            url:'/findStuID',
            data:{data:data.value},
            success: function(rel) {
                $(document).ready(function(){
                    var selects = document.getElementById("stu_name");  //获取id为stu_name的select
                    for (var i = 0; i < selects.options.length; i++){  //先将所有的option设置为未选中
                        selects.options[i].selected = false;
                    }
                    for (var i = 0; i < selects.options.length; i++){   //找到相应的value值 设置为选中
                        if (selects.options[i].value == rel.id){
                            selects.options[i].selected = true;
                            break;
                        }
                    }
                    layui.form.render('select');  //layui更新select
                    // layui.form.render();
                });
            }
        });

    });
    form.on('select(stu_name)', function(data){
        var $ = layui.$;
        stu_idd.value=data.value;
        $.ajax({
            type:'get',
            url:'/findStuID',
            data:{data:data.value},
            success: function(rel) {
                $(document).ready(function(){
                    var selects = document.getElementById("stu_id");  //获取id为stu_id的select
                    for (var i = 0; i < selects.options.length; i++){  //先将所有的option设置为未选中
                        selects.options[i].selected = false;
                    }
                    for (var i = 0; i < selects.options.length; i++){   //找到相应的value值 设置为选中
                        if (selects.options[i].value == rel.id){
                            selects.options[i].selected = true;
                            break;
                        }
                    }
                    layui.form.render('select');  //layui更新select
                });
            }
        });

    });
});

let indexa; //存储文件index
let UPLOAD_FILES = {}; //存储文件列表
var uploadInst;
var Init;
layui.use('upload', function(){
    var upload = layui.upload;
    //执行实例
    var uploadRender = upload.render();
    var stu_idd = document.getElementById("stu_idd");
    var workL = document.getElementById("workL");
    Init=function() {
        document.getElementById("submitbtngroup").innerHTML = '<button id="upl" lay-verify="" type="button"\n' +
            '                                class="layui-btn load" lay-data="{url: \'/upload\', accept: \'file\', exts:\'doc|docx|pdf|rar|zip\' }" >\n' +
            '                            <i class="layui-icon">&#xe67c;</i>选择文件\n' +
            '                        </button>';

        uploadInst = upload.render(
            {
                elem: '.load' //绑定
                , auto: false
                ,drag:false
                , method: 'POST'
                , accept: "file"
                , exts: "doc|docx|pdf|zip|rar"
                , bindAction: '#FormAction'
                , data: {
                    "category": category.value,
                    "stu_idd": stu_idd.value
                }
                , choose: function (obj) {
                    // uploadRender.config.elem.next()[0].value='';
                    UPLOAD_FILES = obj.pushFile();
                    uploadInst.reload({  //重要
                        data: {
                            "category": category.value,
                            "stu_idd": stu_idd.value,
                            "workL": workL.value
                        }
                    });

                    obj.preview(function (index, file, result) {  //预读
                        var filename = document.getElementById("filename");
                        filename.innerText = file.name;
                        indexa = index;
                        file_sta.value = 1;
                        // console.log(file);
                        if (stu_idd.value == "0" || workL.value == "0") {
                            layer.alert('同学，请先选择学号或姓名再进行文件选择', {
                                icon: 6
                                , closeBtn: 0
                            }
                            // , function () {
                            //     //choose方法只调用一次，第二次选择不会调用了，原因未知
                            //     setTimeout("fle()", 300);
                            // }
                            );
                            document.getElementById("upl").remove();
                            console.log("为0，删除");
                            delete UPLOAD_FILES[indexa];
                            var filename = document.getElementById("filename");
                            filename.innerText = "未选择任何文件";
                            file_sta.value = 0;
                            Init();

                        } else
                            layer.msg('选择文件成功，请点击下方按钮立即上传', {icon: 1, time: 1000});
                    });
                }
                , before: function (obj) {

                }
                , done: function (res, index, upload) {
                    //上传完毕回调
                    if (res.code == "成功") {
                        // alert("sec");
                        console.log("上传成功"); //需要改动
                        // var upp = document.getElementById("upp");
                        // upp.innerText="上传成功，请提交";
                    }
                }
                , error: function (res, index, upload) {
                    //请求异常回调
                    if (res.code == "失败") {
                        console.log("上传失败");
                    }
                }
            }
        );

    }
    Init();

});



//
// function downFile() {
//     var workL = document.getElementById("workL");
//     window.location="/downFile?id="+"4"+"&category="+category.value;
// }

function setImgitemClick(){
    if(file_sta.value==0){
        layer.msg('删着好玩吗', {icon: 0,time: 1000});
    }else {
        layer.msg('删除成功', {icon: 1,time: 1000});
        document.getElementById("upl").remove();
        delete UPLOAD_FILES[indexa];
        filename.innerText="未选择任何文件";
        file_sta.value=0;
        Init();
    }
}

function sub() {
    var stuID = document.getElementById("stu_idd");
    var category = document.getElementById("category");
    var workL = document.getElementById("workL");
    if (file_sta.value=="0"){
        layer.alert('请上传你的作业！', {
            skin: 'layui-layer-lan'
            ,closeBtn: 0
            ,anim: 4 //动画类型
        });
        return false;//拒绝上传
    }
    var $ = layui.$;
    $.ajax({
        type:'get',
        url:'/check',
        data:{stuID:stuID.value,
            category:category.value,
            workL:workL.value
        },
        success: function(rel) {
            // console.log(UPLOAD_FILES);
            var filename =  document.getElementById("filename");
            if(rel.code=="失败" && filename.textContent!="未选择任何文件"){
                sta=0;
                formsta=1;
                layer.msg('你已经交过作业了，这次为你覆盖上次的作业',{icon: 1,time:2000});
                setTimeout("all()",2000);
                setTimeout("mooy(3)",1000);
                //已存在，是否继续上传
                // layer.confirm('你已上传过作业，是否覆盖继续上传', {
                //     btn: ['是(5s后自动选择)','否'] //按钮
                //     ,skin: 'layui-layer-lan'
                // }, function(){
                //     // layer.msg( );
                //     setTimeout("mooy(3)",2000); //读秒后运行函数mooy()
                //     layer.close(layer.index);
                //     layer.alert('提交成功',{icon: 1,time:3000});
                // }, function(){
                //     i= true;
                //     layer.alert('取消提交',{icon: 1,time: 2000});
                //     setTimeout("mooy(sta)",100); //读秒后运行函数mooy()
                // });

            }
            // console.log("没有选择文件 "+rel.msg); //是否继续上传做操作
        }
    });
    setTimeout("al()",100); //读秒后运行函数mooy()
    setTimeout("mooy(sta)",5000); //读秒后运行函数mooy()

}



function mooy(sta){ //提交表单
    console.log(sta); //3 0
    if(i){
        if(sta==1)
            layer.alert('取消提交',{icon: 1});
        return false;
    }
    if(sta!=0) {
        if(formsta==0) {
            sb();
            formsta=1;
        }
    }
    if(sta!=3) {
        if(formsta==0) {
            sb();
            formsta=1;
        }
        setTimeout("fle(category.value)",300); //读秒后运行函数mooy()
    }
}

function fle(url) {

    if(url==2)
        url="webWork";
    if(url==1)
        url="index";
    if(url==3)
        url="netSecurity";
    // window.location.reload("http://localhost:8080/"+url);  //本地
    window.location.replace("http://LTHMine.club/"+url);  //服务器
    // window.location.reload("http://192.168.0.101:8080/"+url);   //台式
}
function sb() {
    var uo = document.getElementById('form_s');
    uo.submit();
}
function al() {

    if(sta!=0){
        layer.alert('提交成功，5s后网页将自动刷新',{icon: 1});
    }
}

function all() {
    layer.msg('上传成功',{icon: 1,time:2000});
}




function showInfo(type,code){
    window.location.href = "__URL__/show"+type+"/id/"+code;
}
function  Trim(strValue)
{
    return   strValue.replace(/^s*|s*$/g,"");
}
function SetCookie(sName,sValue)
{
    document.cookie = sName + "=" + escape(sValue);
}
function GetCookie(sName)
{
    var aCookie = document.cookie.split(";");
    for(var　i=0;　i　< aCookie.length;　i++)
    {
        var aCrumb = aCookie[i].split("=");
        if(sName　== Trim(aCrumb[0]))
        {
            return unescape(aCrumb[1]);
        }
    }
    return null;
}
function scrollback()
{
    if(GetCookie("scroll")!=null){document.body.scrollTop=GetCookie("scroll")}
}