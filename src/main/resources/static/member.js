layui.use('table', function(){
    var table = layui.table;
    table.render({
        elem: '#test'
        ,url: '/admin/getStu'
        ,toolbar: '#toolbarDemo'

        ,cols: [[
                {type:'checkbox'},
                {field: 'id', title: 'ID',  sort: true}
                ,{field: 'name', title: '用户名'}
                ,{field: 'password', title: '密码'}
                ,{field: 'python_status', title: 'python作业', sort:true, edit:'text'}
                ,{field: 'web_status', title: 'web作业', sort:true, edit:'text'}
                ,{field: 'net_status', title: '网安作业',  sort: true,edit:'text'}
            ]]

    });
});

layui.use('laydate', function() {
            var laydate = layui.laydate;
            //执行一个laydate实例
            laydate.render({
                elem: '#start' //指定元素
            });
            //执行一个laydate实例
            laydate.render({
                elem: '#end' //指定元素
            });
        });

layui.use('table', function() {
        var table = layui.table;
        //监听单元格编辑
        table.on('edit(test)',
            function(obj) {
                var $ = layui.$;
                var value = obj.value //得到修改后的值
                    ,
                    data = obj.data //得到所在行所有键值
                    ,
                    field = obj.field; //得到字段
                // layer.msg('[ID: ' + data.id + '] ' + field + ' 字段更改为：' + value);
                $.ajax({
                    type:"POST",
                    url:"/admin/updateStu",
                    data:{id:data.id,field:field,value:value},
                    dataType:"json",
                    success:function(data){
                        layer.msg("修改成功");
                    },
                    error:function(data){
                        alert("发生错误："+ data.status);
                    }
                });
            });
        //头工具栏事件
        table.on('toolbar(test)',
            function(obj) {
                var checkStatus = table.checkStatus(obj.config.id);
                switch (obj.event) {
                    case 'getCheckData':
                        var data = checkStatus.data;
                        layer.alert(JSON.stringify(data));
                        break;
                    case 'getCheckLength':
                        var data = checkStatus.data;
                        layer.msg('选中了：' + data.length + ' 个');
                        break;
                    case 'isAll':
                        layer.msg(checkStatus.isAll ? '全选': '未全选');
                        break;
                };
            });
    });

