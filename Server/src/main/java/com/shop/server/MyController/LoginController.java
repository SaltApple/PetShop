package com.shop.server.MyController;

import com.shop.server.Model.Account;
import com.shop.server.MyConfig.MyConst;
import com.shop.server.MyService.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建者:Lethe   日期: 2019/2/19
 */
@RestController
@CrossOrigin
@RequestMapping(name = "用户登录",value = "/account")
@Api(value = "account",description = "账户操作")
public class LoginController {
    private String sessionId=MyConst.USERCOOKIE+":"+MyConst.SESSIONID1;
    @Autowired
    private LoginService service;

    @ApiOperation(value = "login",notes = "账户登陆")
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "登陆成功"),
            @ApiResponse(code = 404,message = "登陆失败")})
    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account){
        Account account1=new Account();
        Map<String,String> map=new HashMap<>();
        map.put("userName",account.getUserName());
        map.put("password",account.getPassword());
        account1=service.login(map);
        if (account1!=null)
        {
            service.addCookie(account1, sessionId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "verify",notes = "账户校验")
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "校验成功"),
            @ApiResponse(code = 404,message = "校验失败")})
    @PostMapping("/verify")
    public ResponseEntity<String> verify(){
        String result=service.verifyCookie(sessionId);
        System.out.println(result);

        if (result.equals("gc")){
            return new ResponseEntity<>("gc",HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(result,HttpStatus.OK);
        }
//        if (result.indexOf("1")!=-1){//代表result内有1  也就是用户未过期
//            return new ResponseEntity<>("true",HttpStatus.OK);
//        }else{
//            return new ResponseEntity<>("false",HttpStatus.NOT_FOUND);
//        }
    }

    @ApiOperation(value = "out",notes = "账户退出")
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "退出成功"),
            @ApiResponse(code = 404,message = "退出失败")})
    @PostMapping("/out")
    public ResponseEntity<Void> out() {
        service.delCookie(sessionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "register",notes = "账户注册")
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "注册成功"),
            @ApiResponse(code = 404,message = "注册失败")})
    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody Account account) {
        service.register(account);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
