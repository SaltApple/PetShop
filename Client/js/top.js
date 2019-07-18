$(function () {
    $.ajax({
        url:"../commons/top.html",
        dataType:"text",
        type:"post",
        statusCode:{
            200:function (data) {//data是commons/top.html中的代码
                $("#top").html(data)
            }
        }
    })
})