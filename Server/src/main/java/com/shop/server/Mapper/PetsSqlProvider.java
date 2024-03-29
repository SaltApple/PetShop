package com.shop.server.Mapper;

import com.shop.server.Model.Pets;
import com.shop.server.Model.PetsExample.Criteria;
import com.shop.server.Model.PetsExample.Criterion;
import com.shop.server.Model.PetsExample;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;

public class PetsSqlProvider {

    public String countByExample(PetsExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("pets");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String deleteByExample(PetsExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("pets");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String insertSelective(Pets record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("pets");
        
        if (record.getPetId() != null) {
            sql.VALUES("pet_id", "#{petId,jdbcType=INTEGER}");
        }
        
        if (record.getPetNo() != null) {
            sql.VALUES("pet_no", "#{petNo,jdbcType=VARCHAR}");
        }
        
        if (record.getPetName() != null) {
            sql.VALUES("pet_name", "#{petName,jdbcType=VARCHAR}");
        }
        
        if (record.getPicture() != null) {
            sql.VALUES("picture", "#{picture,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }

    public String selectByExample(PetsExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("pet_id");
        } else {
            sql.SELECT("pet_id");
        }
        sql.SELECT("pet_no");
        sql.SELECT("pet_name");
        sql.SELECT("picture");
        sql.FROM("pets");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        Pets record = (Pets) parameter.get("record");
        PetsExample example = (PetsExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("pets");
        
        if (record.getPetId() != null) {
            sql.SET("pet_id = #{record.petId,jdbcType=INTEGER}");
        }
        
        if (record.getPetNo() != null) {
            sql.SET("pet_no = #{record.petNo,jdbcType=VARCHAR}");
        }
        
        if (record.getPetName() != null) {
            sql.SET("pet_name = #{record.petName,jdbcType=VARCHAR}");
        }
        
        if (record.getPicture() != null) {
            sql.SET("picture = #{record.picture,jdbcType=VARCHAR}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("pets");
        
        sql.SET("pet_id = #{record.petId,jdbcType=INTEGER}");
        sql.SET("pet_no = #{record.petNo,jdbcType=VARCHAR}");
        sql.SET("pet_name = #{record.petName,jdbcType=VARCHAR}");
        sql.SET("picture = #{record.picture,jdbcType=VARCHAR}");
        
        PetsExample example = (PetsExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(Pets record) {
        SQL sql = new SQL();
        sql.UPDATE("pets");
        
        if (record.getPetNo() != null) {
            sql.SET("pet_no = #{petNo,jdbcType=VARCHAR}");
        }
        
        if (record.getPetName() != null) {
            sql.SET("pet_name = #{petName,jdbcType=VARCHAR}");
        }
        
        if (record.getPicture() != null) {
            sql.SET("picture = #{picture,jdbcType=VARCHAR}");
        }
        
        sql.WHERE("pet_id = #{petId,jdbcType=INTEGER}");
        
        return sql.toString();
    }

    protected void applyWhere(SQL sql, PetsExample example, boolean includeExamplePhrase) {
        if (example == null) {
            return;
        }
        
        String parmPhrase1;
        String parmPhrase1_th;
        String parmPhrase2;
        String parmPhrase2_th;
        String parmPhrase3;
        String parmPhrase3_th;
        if (includeExamplePhrase) {
            parmPhrase1 = "%s #{example.oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{example.oredCriteria[%d].allCriteria[%d].value} and #{example.oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{example.oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{example.oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{example.oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        } else {
            parmPhrase1 = "%s #{oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{oredCriteria[%d].allCriteria[%d].value} and #{oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        }
        
        StringBuilder sb = new StringBuilder();
        List<Criteria> oredCriteria = example.getOredCriteria();
        boolean firstCriteria = true;
        for (int i = 0; i < oredCriteria.size(); i++) {
            Criteria criteria = oredCriteria.get(i);
            if (criteria.isValid()) {
                if (firstCriteria) {
                    firstCriteria = false;
                } else {
                    sb.append(" or ");
                }
                
                sb.append('(');
                List<Criterion> criterions = criteria.getAllCriteria();
                boolean firstCriterion = true;
                for (int j = 0; j < criterions.size(); j++) {
                    Criterion criterion = criterions.get(j);
                    if (firstCriterion) {
                        firstCriterion = false;
                    } else {
                        sb.append(" and ");
                    }
                    
                    if (criterion.isNoValue()) {
                        sb.append(criterion.getCondition());
                    } else if (criterion.isSingleValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase1, criterion.getCondition(), i, j));
                        } else {
                            sb.append(String.format(parmPhrase1_th, criterion.getCondition(), i, j,criterion.getTypeHandler()));
                        }
                    } else if (criterion.isBetweenValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase2, criterion.getCondition(), i, j, i, j));
                        } else {
                            sb.append(String.format(parmPhrase2_th, criterion.getCondition(), i, j, criterion.getTypeHandler(), i, j, criterion.getTypeHandler()));
                        }
                    } else if (criterion.isListValue()) {
                        sb.append(criterion.getCondition());
                        sb.append(" (");
                        List<?> listItems = (List<?>) criterion.getValue();
                        boolean comma = false;
                        for (int k = 0; k < listItems.size(); k++) {
                            if (comma) {
                                sb.append(", ");
                            } else {
                                comma = true;
                            }
                            if (criterion.getTypeHandler() == null) {
                                sb.append(String.format(parmPhrase3, i, j, k));
                            } else {
                                sb.append(String.format(parmPhrase3_th, i, j, k, criterion.getTypeHandler()));
                            }
                        }
                        sb.append(')');
                    }
                }
                sb.append(')');
            }
        }
        
        if (sb.length() > 0) {
            sql.WHERE(sb.toString());
        }
    }
}