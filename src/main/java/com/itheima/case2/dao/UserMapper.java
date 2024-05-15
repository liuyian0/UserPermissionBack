package com.itheima.case2.dao;


import com.itheima.case2.pojo.po.User;
import com.itheima.case2.pojo.vo.QueryPageBean;
import com.itheima.case2.pojo.vo.UpdateUser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserMapper {
    //1.在接口中定义分页查询方法
    //分页查询用户和角色，是多表查询，建议使用映射文件
    List<User> findAllUsersByPage(QueryPageBean pb);

    //2.在接口中定义查询所有行记录数方法
    //查询t_user表的总记录数，单表查询，建议使用注解
    @Select("select count(*) from t_user")
    Long findUsersCount();
    //更新用户
    @Update("update t_user set username=#{username},email=#{email},password=#{password} where id=#{id}")
    void updateUser(UpdateUser user);
    //到中间表根据用户id删除数据
    @Delete("delete from t_user_role where user_id=#{uid}")
    void deleteUserByUID(@Param("uid") Integer id);
    //到中间表添加，需要使用动态sql,因为这里roleIds是集合，需要遍历获取角色id值
    void addUserIDAndRoleID(@Param("uid") Integer id, @Param("rids") List<String> roleIds);
}
