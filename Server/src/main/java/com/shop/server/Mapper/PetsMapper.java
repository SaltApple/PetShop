package com.shop.server.Mapper;

import com.shop.server.Model.Pets;
import com.shop.server.Model.PetsExample;
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

public interface PetsMapper {
    @SelectProvider(type=PetsSqlProvider.class, method="countByExample")
    long countByExample(PetsExample example);

    @DeleteProvider(type=PetsSqlProvider.class, method="deleteByExample")
    int deleteByExample(PetsExample example);

    @Delete({
        "delete from pets",
        "where pet_id = #{petId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer petId);

    @Insert({
        "insert into pets (pet_id, pet_no, ",
        "pet_name, picture)",
        "values (#{petId,jdbcType=INTEGER}, #{petNo,jdbcType=VARCHAR}, ",
        "#{petName,jdbcType=VARCHAR}, #{picture,jdbcType=VARCHAR})"
    })
    int insert(Pets record);

    @InsertProvider(type=PetsSqlProvider.class, method="insertSelective")
    int insertSelective(Pets record);

    @SelectProvider(type=PetsSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="pet_id", property="petId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="pet_no", property="petNo", jdbcType=JdbcType.VARCHAR),
        @Result(column="pet_name", property="petName", jdbcType=JdbcType.VARCHAR),
        @Result(column="picture", property="picture", jdbcType=JdbcType.VARCHAR)
    })
    List<Pets> selectByExample(PetsExample example);

    @Select({
        "select",
        "pet_id, pet_no, pet_name, picture",
        "from pets",
        "where pet_id = #{petId,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="pet_id", property="petId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="pet_no", property="petNo", jdbcType=JdbcType.VARCHAR),
        @Result(column="pet_name", property="petName", jdbcType=JdbcType.VARCHAR),
        @Result(column="picture", property="picture", jdbcType=JdbcType.VARCHAR)
    })
    Pets selectByPrimaryKey(Integer petId);

    @UpdateProvider(type=PetsSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Pets record, @Param("example") PetsExample example);

    @UpdateProvider(type=PetsSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Pets record, @Param("example") PetsExample example);

    @UpdateProvider(type=PetsSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Pets record);

    @Update({
        "update pets",
        "set pet_no = #{petNo,jdbcType=VARCHAR},",
          "pet_name = #{petName,jdbcType=VARCHAR},",
          "picture = #{picture,jdbcType=VARCHAR}",
        "where pet_id = #{petId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Pets record);
}