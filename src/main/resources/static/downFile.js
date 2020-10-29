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
        page:true,
        limit:8,
        limits:[8]
        // height: 315 //设置高度
        // ,limit: 10 //注意：请务必确保 limit 参数（默认：10）是与你服务端限定的数据条数一致
        //支持所有基础参数
    });
});

function changeFile(sta) {
    var table_sta = document.getElementById("table_sta");
    console.log(table_sta.value);
    if(sta == 1){
        table_sta.value="homework";
    }else if(sta == 2) {
        table_sta.value="literature";
    }
}

//
