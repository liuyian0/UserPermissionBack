package com.itheima.case2.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/*
    1.浏览器客户端的请求服务器路径：
        http://localhost:8080/user/find
        http://localhost:8080/user/findById
        http://localhost:8080/user/update
        http://localhost:8080/user/add
        http://localhost:8080/user/delete
    2.这里设置的路径： /user/* ：表示浏览器客户端的访问路径只要以/user开始都可以访问当前Servlet
 */
//@WebServlet("/user/*")
public class User2Servlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //浏览器地址栏请求服务器路径： http://localhost:8080/user/find
        //1.获取请求路径
        String uri = request.getRequestURI();
        //uri="/user/find"
//        System.out.println("uri = " + uri);
        //2.获取最后一个/出现的索引
        // /user/find 这里index的值是 5
        int index = uri.lastIndexOf("/");
        //3.截取:截取的时候不需要/,需要的是/后一个的字符开始到uri字符串末尾的所有内容：find
        //因此这里是index+1
        String methodName = uri.substring(index + 1);
//        System.out.println("methodName = " + methodName);
        try {
            //4.根据请求路径中截取的方法名判断执行哪个方法
            //这里不采用过多的if语句判断，使用反射技术，根据浏览器客户端的请求方式来执行具体的方法
            //4.1创建Class对象
            //this表示User2Servlet类的对象
            Class<?> clazz = this.getClass();
            //4.2获取要执行的方法
        /*
            public Method getDeclaredMethod(String name, Class<?>... parameterTypes)
                    参数：
                        name:获取的方法名
                        parameterTypes:表示要获取方法的形参Class类型
         */
            Method m = clazz.getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);

            //4.3暴力反射
            m.setAccessible(true);

            //4.4 执行方法
        /*
             public Object invoke(Object obj, Object... args)
                            参数：
                                obj:表示方法依赖的对象
                                args:实参

         */
            // private void delete(HttpServletRequest request, HttpServletResponse response)
            m.invoke(this, request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void delete(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("删除用户");
    }

    private void add(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("添加用户");
    }

    private void update(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("更新用户");
    }

    private void find(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("查询用户");
    }
}
