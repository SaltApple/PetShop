package com.shop.server.Mapper;

import com.shop.server.Model.Cart;
import com.shop.server.Model.CartExample;
import com.shop.server.Model.CartKey;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

public interface CartMapper {
    @SelectProvider(type=CartSqlProvider.class, method="countByExample")
    long countByExample(CartExample example);

    @DeleteProvider(type=CartSqlProvider.class, method="deleteByExample")
    int deleteByExample(CartExample example);

    @Delete({
        "delete from cart",
        "where user_id = #{userId,jdbcType=INTEGER}",
          "and item_id = #{itemId,jdbcType=INTEGER}",
          "and order_id = #{orderId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(CartKey key);

    @Insert({
        "insert into cart (user_id, item_id, ",
        "order_id, quantity)",
        "values (#{userId,jdbcType=INTEGER}, #{itemId,jdbcType=INTEGER}, ",
        "#{orderId,jdbcType=INTEGER}, #{quantity,jdbcType=INTEGER})"
    })
    int insert(Cart record);

    @InsertProvider(type=CartSqlProvider.class, method="insertSelective")
    int insertSelective(Cart record);

    @SelectProvider(type=CartSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="user_id", property="userId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="item_id", property="itemId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="order_id", property="orderId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="quantity", property="quantity", jdbcType=JdbcType.INTEGER),
            @Result(column="item_id", property="item",
            one=@One(select = "com.shop.server.Mapper.ItemMapper.selectByPrimaryKey"))
    })
    List<Cart> selectByExample(CartExample example);

    @Select({
        "select",
        "user_id, item_id, order_id, quantity",
        "from cart",
        "where user_id = #{userId,jdbcType=INTEGER}",
          "and item_id = #{itemId,jdbcType=INTEGER}",
          "and order_id = #{orderId,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="user_id", property="userId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="item_id", property="itemId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="order_id", property="orderId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="quantity", property="quantity", jdbcType=JdbcType.INTEGER)
    })
    Cart selectByPrimaryKey(CartKey key);

    @UpdateProvider(type=CartSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Cart record, @Param("example") CartExample example);

    @UpdateProvider(type=CartSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Cart record, @Param("example") CartExample example);

    @UpdateProvider(type=CartSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Cart record);

    @Update({
        "update cart",
        "set quantity = #{quantity,jdbcType=INTEGER}",
        "where user_id = #{userId,jdbcType=INTEGER}",
          "and item_id = #{itemId,jdbcType=INTEGER}",
          "and order_id = #{orderId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Cart record);
}