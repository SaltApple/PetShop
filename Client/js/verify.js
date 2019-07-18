$(function () {
    //http://localhost:63342/AnimalShop/client/shop/product.html?cate=DOGS
    var loc=window.location.href;
    //为了知道我在主页面上到底点了什么图标
    var index=loc.substring(loc.lastIndexOf('/')+1);//按最后一个  /  分解
    var page=index.split("?")[0];//得到操作的页面
    //alert(str1)

    var url="http://localhost:8080/account/verify"
    $.ajax({
        url:url,
        contentType:"application/json",
        type:"post",
        statusCode:{
            200:function (data) {
                var name=data.split(":")[1]
                $("#loginName").html("welcome"+name)
                var userid=data.split(":")[0]

                if(page.indexOf("detailed")!=-1){
                    $("#detail_submit").click(function () {
                        add(userid)
                    })
                }

                if(page.indexOf("cart")!=-1) {
                    show(userid)
                    $("#sub").click(function () {
                        submit(userid)
                    })
                }


            },
            404:function (data) {
                $("#loginName").html("welcome游客")
                if(page.indexOf("detailed")!=-1){
                    $("#detail_submit").click(function () {
                        alert("游客不能购买")
                        window.location="../shop/main.html"
                    })
                }
                if(page.indexOf("cart")!=-1) {
                    alert("用户过期请重新登录")
                    window.location="../shop/main.html"
                    $("#sub").click(function () {
                        alert("用户过期请重新登录")
                        window.location="../shop/main.html"
                    })
                }


            }
        }
    })
})