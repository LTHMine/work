layui.use('element', function(){
    var element = layui.element;

    //…
});

function downFile() {
    // var workL = document.getElementById("workL");
    window.location="/downFile?id="+"4"+"&category="+"1";
}

layui.use('table', function(){
    var table = layui.table;

    //第一个实例
    table.render({
        elem: '#work_home'
        ,height: 550
        ,url: '/tableFile' //数据接口
        ,page: true //开启分页
        ,cols: [[ //表头
            {field: 'id', title: 'ID', width:80, sort: true, fixed: 'left'}
            ,{field: 'workName', title: '文件名', width:490}
            ,{field: 'category', title: '分类', width:80, sort: true}
            ,{field: 'sign', title: '上传者', width: 80}
            ,{field: 'uploadDate', title: '上传日期', width: 110, sort: true}
            ,{field: '', title: '', width: 145}
        ]]
    });

});
