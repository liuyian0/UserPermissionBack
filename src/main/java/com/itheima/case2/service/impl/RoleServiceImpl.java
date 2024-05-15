package com.itheima.case2.service.impl;

import com.itheima.case2.dao.RoleMapper;
import com.itheima.case2.pojo.po.Role;
import com.itheima.case2.service.RoleService;
import com.itheima.case2.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class RoleServiceImpl implements RoleService {
    //查询所有角色信息
    @Override
    public List<Role> findAllRoles() {
        //1.获取mybatis会话对象
        SqlSession sqlSession = SqlSessionUtil.getSession();
        //2.获取接口代理对象
        RoleMapper mapper = sqlSession.getMapper(RoleMapper.class);
        //3.使用接口代理对象调用接口中查询所有角色的方法
        List<Role> roleList = mapper.findAllRoles();
        //4.释放资源
        sqlSession.close();
        //5.返回roleList给web层
        return roleList;
    }
}
