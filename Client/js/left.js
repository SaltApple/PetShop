$(function () {
    $.ajax({
        url:"../commons/left.html",
        dataType:"text",
        type:"post",
        statusCode:{
            200:function (data) {//data是commons/top.html中的代码
                $("#left").html(data)
            }
        }
    })
})