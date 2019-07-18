package com.shop.server.Mapper;

import com.shop.server.Model.Products;
import com.shop.server.Model.ProductsExample;
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

public interface ProductsMapper {
    @SelectProvider(type=ProductsSqlProvider.class, method="countByExample")
    long countByExample(ProductsExample example);

    @DeleteProvider(type=ProductsSqlProvider.class, method="deleteByExample")
    int deleteByExample(ProductsExample example);

    @Delete({
        "delete from products",
        "where product_id = #{productId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer productId);

    @Insert({
        "insert into products (product_id, product_no, ",
        "pet_id, product_name, ",
        "description, picture)",
        "values (#{productId,jdbcType=INTEGER}, #{productNo,jdbcType=VARCHAR}, ",
        "#{petId,jdbcType=INTEGER}, #{productName,jdbcType=VARCHAR}, ",
        "#{description,jdbcType=VARCHAR}, #{picture,jdbcType=VARCHAR})"
    })
    int insert(Products record);

    @InsertProvider(type=ProductsSqlProvider.class, method="insertSelective")
    int insertSelective(Products record);

    @SelectProvider(type=ProductsSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="product_id", property="productId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="product_no", property="productNo", jdbcType=JdbcType.VARCHAR),
        @Result(column="pet_id", property="petId", jdbcType=JdbcType.INTEGER),
        @Result(column="product_name", property="productName", jdbcType=JdbcType.VARCHAR),
        @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
        @Result(column="picture", property="picture", jdbcType=JdbcType.VARCHAR)
    })
    List<Products> selectByExample(ProductsExample example);

    @Select({
        "select",
        "product_id, product_no, pet_id, product_name, description, picture",
        "from products",
        "where product_id = #{productId,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="product_id", property="productId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="product_no", property="productNo", jdbcType=JdbcType.VARCHAR),
        @Result(column="pet_id", property="petId", jdbcType=JdbcType.INTEGER),
        @Result(column="product_name", property="productName", jdbcType=JdbcType.VARCHAR),
        @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
        @Result(column="picture", property="picture", jdbcType=JdbcType.VARCHAR)
    })
    Products selectByPrimaryKey(Integer productId);

    @UpdateProvider(type=ProductsSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Products record, @Param("example") ProductsExample example);

    @UpdateProvider(type=ProductsSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Products record, @Param("example") ProductsExample example);

    @UpdateProvider(type=ProductsSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Products record);

    @Update({
        "update products",
        "set product_no = #{productNo,jdbcType=VARCHAR},",
          "pet_id = #{petId,jdbcType=INTEGER},",
          "product_name = #{productName,jdbcType=VARCHAR},",
          "description = #{description,jdbcType=VARCHAR},",
          "picture = #{picture,jdbcType=VARCHAR}",
        "where product_id = #{productId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Products record);
}