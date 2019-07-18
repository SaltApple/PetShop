$(function () {
    //http://localhost:63342/AnimalShop/client/shop/product.html?cate=DOGS
    var url=window.location.href;
    //为了知道我在主页面上到底点了什么图标
    var index=url.substring(url.lastIndexOf('/')+1);//按最后一个  /  分解
    var page=index.split("?")[0];//得到操作的页面
    //alert(str1)
    if(page.indexOf("product")!=-1) {//代表操作的是product.html
        //var pro1 = index.split("?")[1];
        //product.html?cate=1
        var petMes=url.substring(url.lastIndexOf('?')+1);//得到问号后的内容 cate=1
        var petid = petMes.split("=")[1];//按问号分割取第二个 即 1
        queryProductByPetId(petid)
    }

    if(page.indexOf("items")!=-1){
        var petMes1=url.substring(url.lastIndexOf('?')+1);//得到问号后的内容 productid=7
        var productid = petMes1.split("=")[1];//按问号分割取第二个 即 1
        queryItemsByProductId(productid)
    }

    if(page.indexOf("detailed")!=-1){
        //http://localhost:63342/MyShop/Client/shop/detailed.html?itemid=18&productid=14
        var petMes2=url.substring(url.lastIndexOf('?')+1);//得到问号后的内容 itemid=10&productid=7
        var productids = petMes2.split("&")[0];//按 & 分割取第一个 即productid=7
        var itemid = productids.split("=")[1];//按 = 分割取第二个 即7

        var itemids = petMes2.split("&")[1];//按 & 分割取第一个 即itemid=10
        var productid1 = itemids.split("=")[1];//按 = 分割取第二个 即7
        queryDetailedByProductIdAndItemId(productid1,itemid)
    }
})
//*************************************************************************************
//*************************************************************************************
function queryDetailedByProductIdAndItemId(productid1,itemid) {
    //alert(productid1+"--"+itemid)
    var url="http://localhost:8080/pets/detailedQuery/"+productid1+"/"+itemid
    $.ajax({
        url:url,
        type:"post",
        contentType:"application/json",
        statusCode:{
            200:function (data) {
                showDetail(data)
            }
        }
    })
}
function showDetail(data) {
    $.each(data,function (index,val) {
        $("#pic").attr("src",'../images/'+val.products.picture)
        $("#productno").html(val.products.productNo);//为前端字段赋值用.html
        $("#price").html(val.unitPrice);
        $("#descn").html(val.products.description+"-"+val.introductions);

        $("#cart_itemid").val(val.itemId);
        $("#cart_productid").val(val.products.productId);
    })

}
//*************************************************************************************
//*************************************************************************************
function queryItemsByProductId(productid) {
    var url="http://localhost:8080/pets/itemsQuery/"+productid
    $.ajax({
        url:url,
        type:"post",
        contentType:"application/json",
        statusCode:{
            200:function (data) {
                showItems(data)
            }
        }
    })
}
function showItems(data) {
    $("#itemTable>tbody").empty()
    var lastid=$("#itemTable>thead>tr:last").attr("id")
    var str=""
    $.each(data,function (index,val) {
        str+="<tr bgcolor='#FFFF88' id='"+(++lastid)+"'>" +
                "<td>" +
                    "<b> " +
                    "<a href='detailed.html?itemid="+val.itemId+"&productid="+
                        val.products.productId+"'>"+val.itemNo+"</a>" +
                    "</b>" +
                "</td>" +

                "<td>" +
                    val.products.productNo+
                "</td>" +

                "<td>" +
                    val.products.productName+
                "</td>" +

                "<td>" +
                    val.unitPrice+
                "</td>" +

                "<td>" +
                    "<img border='0' src='../images/"+val.products.picture+"' />" +
                "</td>" +

                "<td>" +
                    val.products.description+"--"+val.introductions+
                "</td>" +

                // "<td>" +
                //     "<a href='cart.html'>" +
                //          "<img border='0' src='../images/button_add_to_cart.gif' />" +
                //     "</a>" +
                // "</td>" +
            "</tr>"
    })
    // alert(str)
    $("#itemTable>tbody").append(str)
}
//*************************************************************************************
//*************************************************************************************
function queryProductByPetId(petid) {
    var url="http://localhost:8080/pets/productQuery/"+petid
    $.ajax({
        url:url,
        contentType:"application/json",
        type:"post",
        statusCode:{
            200:function (data) {
                showProduct(data)
            }
        }
    })
}
function showProduct(data) {
    var str=""
    $("#proTable>tbody").empty()
    var lastId=$("#proTable>thead>tr:last").attr("id")
    $.each(data,function (index,val) {
        str+=
            "<tr bgcolor='#FFFF88' id='"+(++lastId)+"'>" +
                "<td>" +
                    "<b><a href='items.html?productid="+val.productId+"'>"+val.productNo+"</a></b>" +
                "</td>" +

                "<td>" +
                    val.productName+
                "</td>" +

                "<td>" +
                    val.description+
                "</td>" +

                "<td>" +
                    "<img src='../images/"+val.picture+"'>"+
                "</td>" +
            "</tr>"
    })
    $("#proTable>tbody").append(str)
}
//*****************************************************************************************