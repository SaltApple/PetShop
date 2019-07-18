function add(userid) {
    var url="http://localhost:8080/cart/addCart"
    var my={
        "userId":userid,
        "itemId":$("#cart_itemid").val(),
        "quantity":$("#nums").val(),
        "item":{
            "productId":$("#cart_productid").val()
        }
    }
    //alert($("#cart_itemid").val()+"--"+$("#nums").val()+"--"+$("#cart_productid").val())
    var mycart=JSON.stringify(my)
    //alert(mycart)
    $.ajax({
        url:url,
        type:"post",
        contentType:"application/json",
        data:mycart,
        statusCode:{
            200:function () {
                window.location="../shop/cart.html"
            },
            404:function () {
                alert("请登录")
            }
        }
    })
}
//*******************************************************************************************
//*******************************************************************************************
function show(userid) {
    var url="http://localhost:8080/cart/showCart/"+userid
        $.ajax({
            url:url,
            type:"post",
            dataType:"text json",
            statusCode:{
                200:function (data) {
                    showCart(data)
                }
            }
        })
}
//*******************************************************************************************
function showCart(data) {
    $("#cart_table>tbody").empty()
    var myid=$("#cart_table>thead>tr:last").attr("id")
    var str=""
    var total=0
    $.each(data,function (index,val) {
        total+=val.item.unitPrice*val.quantity
        str+=
            "<tr bgcolor=\"#FFFF88\" id=''"+(++myid)+">" +
                "<td>" +
                    "<b>" +
                        val.itemId+
                    "</b>" +
                "</td>" +

                "<td>" +
                    val.item.introductions+
                "</td>" +

                "<td>" +
                    val.item.products.productNo+
                    "<input type='hidden' value='"+val.orderId+"' name='subOid'>"+
                "</td>" +

                "<td align=\"center\">" +
                    val.item.products.description+
                "</td>" +

                "<td>" +
                    "<img src='../images/"+val.item.products.picture+"'>"+
                "</td>" +

                "<td align=\"center\">" +
                    val.item.unitPrice+
                "</td>" +

                "<td align=\"center\">" +
                    "<input type='number' style=\"width:80px;height:20px;\" " +
                        "id='qty' min='1' value='"+val.quantity+"' " +
                         "onchange='update(this"+","+val.itemId+","+val.orderId+")'>"+
                "</td>" +

                "<td>" +
                    "<input type='image' src=\"../images/button_remove.gif\" " +
                    "onclick='del("+val.item.itemId+","+val.orderId+")'/>" +
                "</td>" +
            "</tr>"

    })
    $("#cart_table>tbody").append(str)
    $("#total").html(total.toFixed(2))
}
//*******************************************************************************************
//*******************************************************************************************
function del(itemid,orderid) {

    var url="http://localhost:8080/account/verify"
    $.ajax({
        url:url,
        type:"post",
        statusCode: {
            200:function (data) {
                deleteCart(data.toString().split(":")[0],orderid,itemid)
            },
            404:function (data) {
                $("#loginName").html("welcome"+" "+"游客");
                window.location="../shop/main.html"
            }
        }
    })
}
//*******************************************************************************************
function deleteCart(userid,orderid,itemid) {
    var url="http://localhost:8080/cart/deleteCart";
    var delcart={
        "userId":userid,//左端的键要和model及mapper中存的相同
        "orderId":orderid,
        "itemId":itemid
    };
    console.log(userid+" "+orderid+" "+itemid)
    var cart=JSON.stringify(delcart);
    console.log(cart)
    $.ajax({
        contentType:"application/json",
        url:url,
        data:cart,
        type:"post",
        statusCode:{
            200:function(){
                //alert(data.toString().split(":")[0]);
                window.location="../shop/cart.html";
            },
            404:function(){
                $("#loginName").html("welcome"+" "+"游客");
                window.location="../shop/login.html";
            }
        }
    });
}
//*******************************************************************************************
//*******************************************************************************************
function update(my,itemId,orderId){
    //alert(my.value+"--"+userId+"--"+itemId+"--"+orderId+"--"+quantity)
    var url="http://localhost:8080/account/verify"
    $.ajax({
        url:url,
        type:"post",
        statusCode: {
            200:function (data) {
                updateCart(my.value,data.split(":")[0],itemId,orderId)
            },
            404:function () {
                $("#loginName").html("welcome"+" "+"游客");
                window.location="../shop/main.html"
            }
        }
    })
}
//*******************************************************************************************
function updateCart(quantity,userId,itemId,orderId) {
    var url="http://localhost:8080/cart/updateCart"
    var cart={
        "userId":userId,
        "orderId":orderId,
        "itemId":itemId,
        "quantity":quantity
    }
    var newCart=JSON.stringify(cart)
    $.ajax({
        url:url,
        type:"post",
        contentType:"application/json",
        data:newCart,
        statusCode:{
            200:function () {
                window.location="../shop/cart.html"
            }
        }
    })
}
//*******************************************************************************************
//*******************************************************************************************
function submit(userid) {
    var url="http://localhost:8080/cart/submitCart";
    var myOrders={
        "userId":userid,
        "orderId":$("*[name='subOid']").val(),//
        "totalPrice":$("#total").text()
    };
    var orders=JSON.stringify(myOrders);
    $.ajax({
        url:url,
        type:"post",
        contentType:"application/json",
        data:orders,
        statusCode:{
            200:function () {
                alert("提交成功")
                window.location="../shop/main.html"
            },
            404:function () {
                alert("提交失败")
            }

        }
    })
}