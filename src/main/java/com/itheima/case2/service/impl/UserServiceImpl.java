package com.itheima.case2.service.impl;

import com.itheima.case2.dao.UserMapper;
import com.itheima.case2.pojo.po.User;
import com.itheima.case2.pojo.vo.PageResult;
import com.itheima.case2.pojo.vo.QueryPageBean;
import com.itheima.case2.pojo.vo.UpdateUser;
import com.itheima.case2.service.UserService;
import com.itheima.case2.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class UserServiceImpl implements UserService {
    //1.定义方法接收web层传递的数据
    @Override
    public PageResult findAllUsersByPage(QueryPageBean pb) {
        //2.获取mybatis的会话对象
        SqlSession session = SqlSessionUtil.getSession();
        //3.使用会话对象调用方法获取接口代理对象
        UserMapper mapper = session.getMapper(UserMapper.class);
        //4.使用接口代理对象调用接口中的分页查询方法
        //说明：每页显示的是多个用户，所以这里使用单列集合保存
        List<User> list = mapper.findAllUsersByPage(pb);
        //5.使用接口代理对象调用方法查询用户表的所有行记录数
        Long count = mapper.findUsersCount();
        //6.将行记录数和用户数据封装到PageResult对象中
        PageResult pageResult = new PageResult(count,list);
        //7.关闭会话对象
        session.close();
        //8.返回PageResult对象
        return pageResult;
    }
    //修改用户
    @Override
    public void update(UpdateUser user) {
        //1.获取mybatis的会话对象
        SqlSession session = SqlSessionUtil.getSession();
        //2.使用会话对象调用方法获取接口代理对象
        UserMapper mapper = session.getMapper(UserMapper.class);
        //3.使用接口代理对象调用方法修改用户信息
        mapper.updateUser(user);
        //4.先到中间表根据当前用户id删除当前用户的信息
        mapper.deleteUserByUID(user.getId());
        //5.到中间表根中将当前用户id和对应的角色id插入到中间表中
        //一个用户有多个角色：用户id:2  角色id: 2 3
        //user.getId() 表示用户id
        //user.getRoleIds() 用户对应的角色id
        mapper.addUserIDAndRoleID(user.getId(),user.getRoleIds());
        //6.释放资源
        session.close();
    }
}
