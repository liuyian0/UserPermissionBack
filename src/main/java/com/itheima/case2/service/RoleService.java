package com.itheima.case2.service;

import com.itheima.case2.pojo.po.Role;

import java.util.List;

public interface RoleService {
    //查询所有角色信息
    List<Role> findAllRoles();
}
