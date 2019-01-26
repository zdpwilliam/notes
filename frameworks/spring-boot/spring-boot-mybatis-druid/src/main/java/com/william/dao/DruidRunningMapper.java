package com.william.dao;

import com.william.model.ADruidTmpModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by william on 17-7-24.
 */
@Mapper
public interface DruidRunningMapper {

    @Select("select id, name, role, created_at from a_druid_tmp where id = #{id}")
    public ADruidTmpModel selectById(@Param("id") Integer id);

    public Integer insertADruidTmpModel(ADruidTmpModel druidTmpModel);

}
