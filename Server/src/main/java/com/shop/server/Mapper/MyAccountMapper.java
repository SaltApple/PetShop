package com.shop.server.Mapper;

import com.shop.server.Model.Account;
import com.shop.server.Model.AccountExample;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;

import java.util.List;
import java.util.Map;

/**
 * 创建者:Lethe   日期: 2019/2/20
 */
public interface MyAccountMapper {
    @SelectProvider(type=MyAccountSQLProvider.class, method="login")
    @Results({
            @Result(column="user_id", property="userId", jdbcType= JdbcType.INTEGER, id=true),
            @Result(column="user_name", property="userName", jdbcType=JdbcType.VARCHAR),
            @Result(column="password", property="password", jdbcType=JdbcType.VARCHAR),
            @Result(column="email", property="email", jdbcType=JdbcType.VARCHAR),
            @Result(column="real_name", property="realName", jdbcType=JdbcType.VARCHAR),
            @Result(column="address", property="address", jdbcType=JdbcType.VARCHAR)
    })
    Account login(Map<String,String> mes);
}
