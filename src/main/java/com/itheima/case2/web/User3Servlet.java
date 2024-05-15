package com.itheima.case2.web;

import com.itheima.case2.pojo.vo.PageResult;
import com.itheima.case2.pojo.vo.QueryPageBean;
import com.itheima.case2.pojo.vo.Result;
import com.itheima.case2.pojo.vo.UpdateUser;
import com.itheima.case2.service.UserService;
import com.itheima.case2.utils.BaseController;
import com.itheima.case2.utils.BeansFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
    说明：
        1.定义User3Servlet类继承BaseServlet，而BaseServlet继承了HttpServlet，我们只需要在子类User3Servlet中书写操作用户模块的增删改查
        方法即可
        2.如果我们想操作角色 权限模块，那么只需要定义角色 权限的Servlet继承BaseServlet即可
 */
@WebServlet("/user/*")
public class User3Servlet extends BaseServlet{
    private void delete(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("删除用户");
    }

    private void add(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("添加用户");
    }
    //修改用户
    private void update(HttpServletRequest request, HttpServletResponse response) {
        try {
            //1.将请求参数封装到实体类中
            UpdateUser user = BaseController.parseJSON2Object(request, UpdateUser.class);
            //2.创建业务层对象
            UserService userService = BeansFactory.getBean("userService");
            //3.使用业务层对象调用方法
            userService.update(user);
            //4.创建Result对象
            Result result = new Result(true, "修改用户成功");
            //5.响应数据给前端
            BaseController.printResult(response,result);
        } catch (Exception e) {
            e.printStackTrace();
            Result result = new Result(false, "修改用户失败");
            try {
                BaseController.printResult(response,result);
            } catch (Exception ioException) {
                ioException.printStackTrace();
            }
        }
    }

    private void find(HttpServletRequest request, HttpServletResponse response) {
        try {
            //1.获取页面提交的数据封装到分页的实体类中
            /*
                 前端提交的数据：let params={"currentPage":this.pagination.pageNum,"pageSize":this.pagination.pageSize};
             */
            QueryPageBean pb = BaseController.parseJSON2Object(request, QueryPageBean.class);
            /*
                TODO:
                    1.之前在servlet中创建业务层对象： UserServiceImpl userService = new UserServiceImpl();
                    2.采用new 类名()创建对象方式弊端：
                        1）扩展性不强
                        2）耦合度太高
                    3.解决上述问题
                        如果我们想对业务层类进行扩展，例如需要创建UserServiceImpl2 UserServiceImpl3类...那么我们不用在修改web层
                        中Servlet的代码。我们可以通过反射技术+读取配置文件+面向接口编程 方式取代new创建对象的方式
                        1）面向接口编程   UserService userService = new UserServiceImpl();
                        2)反射技术+读取配置文件
             */
            //2.创建业务层对象
//            UserServiceImpl userService = new UserServiceImpl();
            //等号左边是UserService是接口，等号右边是实现类对象，这里是多态
//            UserService userService = new UserServiceImpl();
            //2.1关联配置文件beans.properties
            /*
                回顾：
                    ResourceBundle 类只能读取后缀名是.properties配置文件，ResourceBundle.getBundle("配置文件名")
             */
           /* ResourceBundle bundle = ResourceBundle.getBundle("beans");//只能书写配置文件名
            //2.2使用对象bundle调用方法根据key获取值
            //userService=com.itheima.case2.service.impl.UserServiceImpl
            //userServiceStr="com.itheima.case2.service.impl.UserServiceImpl"
            String userServiceStr = bundle.getString("userService");
            //2.3获取实现类UserServiceImpl的Class对象
            Class<?> clazz = Class.forName(userServiceStr);
            //2.4创建实现类对象
            //clazz.newInstance()：调用UserServiceImpl类的无参构造方法创建UserServiceImpl类的对象
            UserService userService = (UserService) clazz.newInstance();*/

            //使用工厂类调用方法根据指定key获取指定实现类UserServiceImpl对象
            UserService userService = BeansFactory.getBean("userService");
            //userService = com.itheima.case2.service.impl.UserServiceImpl@709dba22
            System.out.println("userService = " + userService);
            //userService2 = com.itheima.case2.service.impl.UserServiceImpl@709dba22
//            UserService userService2 = BeansFactory.getBean("userService");
//            System.out.println("userService2 = " + userService2);

            //3.使用业务层对象调用分页查询方法，将查询结果封装到分页结果PageResult实体类中
            PageResult pageResult = userService.findAllUsersByPage(pb);
            //4.将Result对象响应给浏览器
            //	public Result(boolean flag, String message, Object result)
            Result result = new Result(true, "分页查询用户成功", pageResult);
            BaseController.printResult(response,result);
        } catch (Exception e) {
            e.printStackTrace();
            //查询失败
            try {
                Result result = new Result(false, "分页查询用户失败");
                BaseController.printResult(response,result);
            } catch (Exception ioException) {
                ioException.printStackTrace();
            }

        }
    }
}
