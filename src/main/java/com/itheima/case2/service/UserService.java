package com.itheima.case2.service;

import com.itheima.case2.pojo.vo.PageResult;
import com.itheima.case2.pojo.vo.QueryPageBean;
import com.itheima.case2.pojo.vo.UpdateUser;

/**
 * 接口
 */
public interface UserService {
    //分页查询用户
    public PageResult findAllUsersByPage(QueryPageBean pb);
    //修改用户
    void update(UpdateUser user);
}
