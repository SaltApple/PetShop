package com.shop.server.Mapper;

import com.shop.server.Model.Item;
import java.util.List;

import com.shop.server.Model.ItemExample;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

public interface ItemMapper {
    @SelectProvider(type=ItemSqlProvider.class, method="countByExample")
    long countByExample(ItemExample example);

    @DeleteProvider(type=ItemSqlProvider.class, method="deleteByExample")
    int deleteByExample(ItemExample example);

    @Delete({
        "delete from item",
        "where item_id = #{itemId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer itemId);

    @Insert({
        "insert into item (item_id, item_no, ",
        "product_id, unit_price, ",
        "introductions)",
        "values (#{itemId,jdbcType=INTEGER}, #{itemNo,jdbcType=VARCHAR}, ",
        "#{productId,jdbcType=INTEGER}, #{unitPrice,jdbcType=REAL}, ",
        "#{introductions,jdbcType=VARCHAR})"
    })
    int insert(Item record);

    @InsertProvider(type=ItemSqlProvider.class, method="insertSelective")
    int insertSelective(Item record);

    @SelectProvider(type=ItemSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="item_id", property="itemId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="item_no", property="itemNo", jdbcType=JdbcType.VARCHAR),
        @Result(column="product_id", property="productId", jdbcType=JdbcType.INTEGER),
        @Result(column="unit_price", property="unitPrice", jdbcType=JdbcType.REAL),
        @Result(column="introductions", property="introductions", jdbcType=JdbcType.VARCHAR),
        @Result(column="product_id", property="products",
        one = @One(select = "com.shop.server.Mapper.ProductsMapper.selectByPrimaryKey"))
    })
    List<Item> selectByExample(ItemExample example);

    @Select({
        "select",
        "item_id, item_no, product_id, unit_price, introductions",
        "from item",
        "where item_id = #{itemId,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="item_id", property="itemId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="item_no", property="itemNo", jdbcType=JdbcType.VARCHAR),
        @Result(column="product_id", property="productId", jdbcType=JdbcType.INTEGER),
        @Result(column="unit_price", property="unitPrice", jdbcType=JdbcType.REAL),
        @Result(column="introductions", property="introductions", jdbcType=JdbcType.VARCHAR)
    })
    Item selectByPrimaryKey(Integer itemId);

    @UpdateProvider(type=ItemSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Item record, @Param("example") ItemExample example);

    @UpdateProvider(type=ItemSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Item record, @Param("example") ItemExample example);

    @UpdateProvider(type=ItemSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Item record);

    @Update({
        "update item",
        "set item_no = #{itemNo,jdbcType=VARCHAR},",
          "product_id = #{productId,jdbcType=INTEGER},",
          "unit_price = #{unitPrice,jdbcType=REAL},",
          "introductions = #{introductions,jdbcType=VARCHAR}",
        "where item_id = #{itemId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Item record);
}