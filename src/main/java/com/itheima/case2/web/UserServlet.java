package com.itheima.case2.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
public class UserServlet extends HttpServlet {
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
        //4.根据请求路径中截取的方法名判断执行哪个方法
        if("find".equals(methodName)){
            //查询所有用户
            find(request,response);
        }else if("update".equals(methodName)){
            //更新用户
            update(request,response);
        }else if("add".equals(methodName)){
            //添加用户
            add(request,response);
        }else if("delete".equals(methodName)){
            //删除用户
            delete(request,response);
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
