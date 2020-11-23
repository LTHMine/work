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


function searchStu() {
    var $ = layui.$;
    console.log($("#stu_id").val());
    $.ajax({
        type:'get',
        url:'/searchStu',
        data:{stu_id:$("#stu_id").val()},
        success: function(rels) {
            var result=document.getElementById("result");
            var htmld=
                '<thead>\n' +
                '    <tr>\n' +
                '      <th>作业名称</th>\n' +
                '      <th>是否已交</th>\n' +
                '    </tr> \n' +
                '  </thead>'
            ;
            for(var i=0;i<rels.length;i++){
                htmld+='<tr><td>'+rels[i].workName+'</td><td>'+rels[i].workStatu+'</td></tr>';
            }
            result.innerHTML=htmld;
        }
    });
}