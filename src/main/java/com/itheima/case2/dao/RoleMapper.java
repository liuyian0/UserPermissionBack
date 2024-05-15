package com.itheima.case2.dao;

import com.itheima.case2.pojo.po.Role;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleMapper {

    //查询所有角色信息
    @Select("select * from t_role")
    List<Role> findAllRoles();
}
