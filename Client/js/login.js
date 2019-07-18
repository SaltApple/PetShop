$(function () {
    $("#submit1").click(function () {
        var name=$("#username").val()
        var pass=$("#password").val()
        if($.trim(name)==""||name.length==0){
            alert("你的用户名为空");
            return
        }
        if($.trim(pass)==""||pass.length==0){
            alert("你的密码为空");
            return
        }

        var url="http://localhost:8080/account/login"
        var data=JSON.stringify($("#form1").serializeObject())
        $.ajax({
            contentType:"application/json",
            url:url,
            type:"post",
            data:data,
            statusCode:{
                200:function () {
                    alert("登陆成功")
                    window.location="../shop/main.html"
                },
                404:function () {
                    alert("登陆失败")
                    window.location="../shop/login.html";
                }
            }
        })
    })
})