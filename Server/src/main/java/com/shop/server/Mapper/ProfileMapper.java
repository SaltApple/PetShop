package com.shop.server.Mapper;

import com.shop.server.Model.Profile;

import java.util.List;

import com.shop.server.Model.ProfileExample;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

public interface ProfileMapper {
    @SelectProvider(type=ProfileSqlProvider.class, method="countByExample")
    long countByExample(ProfileExample example);

    @DeleteProvider(type=ProfileSqlProvider.class, method="deleteByExample")
    int deleteByExample(ProfileExample example);

    @Delete({
        "delete from profile",
        "where user_id = #{userId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer userId);

    @Insert({
        "insert into profile (user_id, language, ",
        "pet_id)",
        "values (#{userId,jdbcType=INTEGER}, #{language,jdbcType=VARCHAR}, ",
        "#{petId,jdbcType=INTEGER})"
    })
    //********************************************************************************************
    @Options(useGeneratedKeys = true, keyProperty = "userId", keyColumn = "user_id")
        /*增加这个注解插入记录后会返回自增长的id*/
        //********************************************************************************************

    int insert(Profile record);

    @InsertProvider(type=ProfileSqlProvider.class, method="insertSelective")
    int insertSelective(Profile record);

    @SelectProvider(type=ProfileSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="user_id", property="userId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="language", property="language", jdbcType=JdbcType.VARCHAR),
        @Result(column="pet_id", property="petId", jdbcType=JdbcType.INTEGER)
    })
    List<Profile> selectByExample(ProfileExample example);

    @Select({
        "select",
        "user_id, language, pet_id",
        "from profile",
        "where user_id = #{userId,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="user_id", property="userId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="language", property="language", jdbcType=JdbcType.VARCHAR),
        @Result(column="pet_id", property="petId", jdbcType=JdbcType.INTEGER)
    })
    Profile selectByPrimaryKey(Integer userId);

    @UpdateProvider(type=ProfileSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Profile record, @Param("example") ProfileExample example);

    @UpdateProvider(type=ProfileSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Profile record, @Param("example") ProfileExample example);

    @UpdateProvider(type=ProfileSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Profile record);

    @Update({
        "update profile",
        "set language = #{language,jdbcType=VARCHAR},",
          "pet_id = #{petId,jdbcType=INTEGER}",
        "where user_id = #{userId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Profile record);
}