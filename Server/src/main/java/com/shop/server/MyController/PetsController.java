package com.shop.server.MyController;

import com.shop.server.Model.Account;
import com.shop.server.Model.Item;
import com.shop.server.Model.Pets;
import com.shop.server.Model.Products;
import com.shop.server.MyService.LoginService;
import com.shop.server.MyService.PetsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 创建者:Lethe   日期: 2019/2/23
 */
@RestController
@CrossOrigin
@RequestMapping(name = "宠物操作",value = "/pets")
@Api(value = "account",description = "宠物操作")
public class PetsController {
    @Autowired
    private PetsService service;

    @ApiOperation(value = "pets",notes = "宠物")
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "成功"),
            @ApiResponse(code = 404,message = "失败")})
    @PostMapping("/queryPets")
    public ResponseEntity<List> queryPet(){
        List<Pets> list=service.queryPet();
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @ApiOperation(value = "productQuery",notes = "商品查询")
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "成功"),
            @ApiResponse(code = 404,message = "失败")})
    @PostMapping("/productQuery/{petid}")
    public ResponseEntity<List> proQuery(@PathVariable String petid){
        List<Products> list=service.proQuery(petid);
        return new ResponseEntity<>(list,HttpStatus.OK);
    }


    @ApiOperation(value = "itemsQuery",notes = "商品查询")
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "成功"),
            @ApiResponse(code = 404,message = "失败")})
    @PostMapping("/itemsQuery/{productid}")
    public ResponseEntity<List> iteQuery(@PathVariable String productid){
        List<Item> list=service.iteQuery(productid);
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @ApiOperation(value = "detailedQuery",notes = "商品查询")
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "成功"),
            @ApiResponse(code = 404,message = "失败")})
    @PostMapping("/detailedQuery/{productid}/{itemid}")
    public ResponseEntity<List> detailQuery(@PathVariable String productid,@PathVariable String itemid){
        List<Item> list=service.detailQuery(productid,itemid);
        return new ResponseEntity<>(list,HttpStatus.OK);
    }
}
