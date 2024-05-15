package com.itheima.case2.web;

import com.itheima.case2.pojo.po.Role;
import com.itheima.case2.pojo.vo.Result;
import com.itheima.case2.service.RoleService;
import com.itheima.case2.utils.BaseController;
import com.itheima.case2.utils.BeansFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet("/role/*")
public class RoleServlet extends BaseServlet{

    //查询所有角色
    //注意：这里的方法名需要和前端请求路径一致：let url="/role/findAllRoles"
    private void findAllRoles(HttpServletRequest request, HttpServletResponse response) {
        try {
            //1.创建业务层对象

            RoleService roleService = BeansFactory.getBean("roleService");
            //2.使用业务层对象调用查询所有角色方法
            List<Role> roleList = roleService.findAllRoles();
            //3.将roleList响应给浏览器
            Result result = new Result(true, "查询所有角色成功", roleList);
            BaseController.printResult(response,result);
        } catch (Exception e) {
            e.printStackTrace();
            Result result = new Result(false, "查询所有角色失败");
            try {
                BaseController.printResult(response,result);
            } catch (Exception ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
