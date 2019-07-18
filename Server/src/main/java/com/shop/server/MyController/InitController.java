package com.shop.server.MyController;

import com.shop.server.Model.Account;
import com.shop.server.MyService.InitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 创建者:Lethe   日期: 2019/2/23
 */
@RestController
@CrossOrigin
@RequestMapping(name = "初始化",value = "/init")
@Api(value = "account",description = "账户操作")
public class InitController {
    @Autowired
    private InitService service;

    @ApiOperation(value = "login", notes = "账户登陆")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "登陆成功"),
            @ApiResponse(code = 404, message = "登陆失败")})
    @PostMapping("/inits")
    public ResponseEntity<Account> login() {
        service.init();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
