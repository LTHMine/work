layui.use('element', function(){
    var element = layui.element;

    //…
});

function downFile() {
    var downf = document.getElementById("downf");
    window.location="/downFile?id="+downf.value;
}

layui.use('table', function(){
    var table = layui.table;
    table.init('demo', {
        // height: 315 //设置高度
        // ,limit: 10 //注意：请务必确保 limit 参数（默认：10）是与你服务端限定的数据条数一致
        //支持所有基础参数
    });
});


