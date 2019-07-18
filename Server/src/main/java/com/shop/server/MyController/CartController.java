package com.shop.server.MyController;

import com.shop.server.Model.Cart;
import com.shop.server.Model.Orders;
import com.shop.server.MyService.CartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 创建者:Lethe   日期: 2019/3/2
 */
@Controller
@CrossOrigin
@RequestMapping(name = "cartController",value = "/cart")
@Api(value = "account",description = "宠物操作")
public class CartController {
    @Autowired
    private CartService service;

    @ApiOperation(value = "addCart",notes = "宠物")
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "成功"),
            @ApiResponse(code = 404,message = "失败")})
    @PostMapping("/addCart")
    private ResponseEntity<Void> add(@RequestBody Cart cart){
        service.addCart(cart);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "pets",notes = "宠物")
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "成功"),
            @ApiResponse(code = 404,message = "失败")})
    @PostMapping("/showCart/{userid}")
    private ResponseEntity<List> show(@PathVariable String userid){
        String maxid=service.getMaxOrderId(userid);
        if (maxid==null){
            maxid="01";
        }
        String orderid=maxid.substring(1);
        String flag=maxid.substring(0,1);
        int oid=Integer.parseInt(orderid);
        List<Cart> list;
        list=service.showCart(userid,orderid);
        if (flag.equals("1")){//已经提交的用户前端显示的购物车内应没有东西
            oid++;
            list=service.showCart(userid,oid+"");
        }
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @ApiOperation(value = "pets",notes = "宠物")
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "成功"),
            @ApiResponse(code = 404,message = "失败")})
    @PostMapping("/deleteCart")
    private ResponseEntity<Void> deleteCart(@RequestBody Cart cart){
        service.del(cart);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "pets",notes = "宠物")
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "成功"),
            @ApiResponse(code = 404,message = "失败")})
    @PostMapping("/updateCart")
    private ResponseEntity<Void> updateCart(@RequestBody Cart cart){
        service.update(cart);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "pets",notes = "宠物")
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "成功"),
            @ApiResponse(code = 404,message = "失败")})
    @PostMapping("/submitCart")
    private ResponseEntity<Void> submitCart(@RequestBody Orders orders){
        service.submit(orders);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
