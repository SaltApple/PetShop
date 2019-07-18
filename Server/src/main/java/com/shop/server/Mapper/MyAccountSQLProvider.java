package com.shop.server.Mapper;

import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

/**
 * 创建者:Lethe   日期: 2019/2/20
 */
public class MyAccountSQLProvider {
    public String login(Map<String,String> mes){
        return new SQL(){
            {
                SELECT("*");
                FROM("account a");
                WHERE("a.user_name=#{userName,jdbcType=VARCHAR} and a.password=#{password,jdbcType=VARCHAR}");
            }
        }.toString();
    }
}
