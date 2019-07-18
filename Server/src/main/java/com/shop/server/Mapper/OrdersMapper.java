package com.shop.server.Mapper;

import com.shop.server.Model.Orders;
import com.shop.server.Model.OrdersExample;
import com.shop.server.Model.OrdersKey;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface OrdersMapper {
    @SelectProvider(type=OrdersSqlProvider.class, method="countByExample")
    long countByExample(OrdersExample example);

    @DeleteProvider(type=OrdersSqlProvider.class, method="deleteByExample")
    int deleteByExample(OrdersExample example);

    @Delete({
        "delete from orders",
        "where order_id = #{orderId,jdbcType=INTEGER}",
          "and user_id = #{userId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(OrdersKey key);

    @Insert({
        "insert into orders (order_id, user_id, ",
        "order_date, total_price)",
        "values (#{orderId,jdbcType=INTEGER}, #{userId,jdbcType=INTEGER}, ",
        "#{orderDate,jdbcType=DATE}, #{totalPrice,jdbcType=DECIMAL})"
    })
    int insert(Orders record);

    @InsertProvider(type=OrdersSqlProvider.class, method="insertSelective")
    int insertSelective(Orders record);

    @SelectProvider(type=OrdersSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="order_id", property="orderId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="order_date", property="orderDate", jdbcType=JdbcType.DATE),
        @Result(column="total_price", property="totalPrice", jdbcType=JdbcType.DECIMAL)
    })
    List<Orders> selectByExample(OrdersExample example);

    @Select({
        "select",
        "order_id, user_id, order_date, total_price",
        "from orders",
        "where order_id = #{orderId,jdbcType=INTEGER}",
          "and user_id = #{userId,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="order_id", property="orderId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="order_date", property="orderDate", jdbcType=JdbcType.DATE),
        @Result(column="total_price", property="totalPrice", jdbcType=JdbcType.DECIMAL)
    })
    Orders selectByPrimaryKey(OrdersKey key);

    @UpdateProvider(type=OrdersSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Orders record, @Param("example") OrdersExample example);

    @UpdateProvider(type=OrdersSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Orders record, @Param("example") OrdersExample example);

    @UpdateProvider(type=OrdersSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Orders record);

    @Update({
        "update orders",
        "set order_date = #{orderDate,jdbcType=DATE},",
          "total_price = #{totalPrice,jdbcType=DECIMAL}",
        "where order_id = #{orderId,jdbcType=INTEGER}",
          "and user_id = #{userId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Orders record);
}