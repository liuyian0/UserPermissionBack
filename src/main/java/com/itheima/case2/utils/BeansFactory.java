package com.itheima.case2.utils;

import java.util.HashMap;
import java.util.ResourceBundle;

/**
 *  TODO:该类专门用来创建类对象，我们可以将该类理解为一个工厂类，该工厂使用反射+读取配置文件来生产对象。
 *  注意：生产对象有两种：
 *      1.单例对象
 *          1）饿汉式模式：工厂类一加载就创建指定类(UserServiceImpl)的对象
 *          2）懒汉式模式：什么时候需要指定类(UserServiceImpl)的对象的对象，那么在工厂类中创建指定类(UserServiceImpl)
 *      2.多例对象
 */
public class BeansFactory {
    /*
        需求：定义静态方法获取指定类(UserServiceImpl)的单个对象
        实现步骤：
            1.定义map集合 new HashMap<String,Object>
                 key :需要调用者传递的字符串即根据key就知道创建哪个类的对象，key可以是：userService 、 roleService
                 value：存放的是创建指定类(UserServiceImpl，RoleServiceImpl)的对象
            2.定义静态方法接收调用者传递的key
            3.在静态方法体中使用反射技术+读取配置文件方式创建指定key对应类的对象
     */
    /*
         1.定义map集合 new HashMap<String,Object>
                 key :需要调用者传递的字符串即根据key就知道创建哪个类的对象，key可以是：userService 、 roleService
                 value：存放的是创建指定类(UserServiceImpl，RoleServiceImpl)的对象
     */
    private static HashMap<String,Object> beansMap = new HashMap<>();
    // 2.定义静态方法接收调用者传递的key
    /*
        问题：如果是多线程环境下，那么这里会发生多线程安全问题：
            t1:cpu先执行t1线程，第一次 Object obj = beansMap.get(key);，obj是null,然后进入if语句，还没执行完if,cpu切换到t2线程
            t2：由于此时beansMap集合还是空，所以这里获取的obj也是null,t2也进入到if语句，此时就会有多线程安全问题
         解决多线程安全问题：加同步锁 synchronized
     */
    public static synchronized  <T> T getBean(String key)throws Exception{//String key=userService 、 roleService
        //3.在静态方法体中使用反射技术+读取配置文件方式创建指定key对应类的对象
        //3.1根据接收的key结合beansMap集合的方法获取值
        Object obj = beansMap.get(key);
        //3.2判断obj是否等于null
        if(obj == null){
            //3.3说明是第一次操作，之前map集合中没有该类的对象，我们需要创建对象
            ResourceBundle bundle = ResourceBundle.getBundle("beans");//只能书写配置文件名
            //使用对象bundle调用方法根据key获取值
            //userServiceStr="com.itheima.case2.service.impl.UserServiceImpl"
            String userServiceStr = bundle.getString(key);//t1 t2
            //获取实现类UserServiceImpl的Class对象
            Class<?> clazz = Class.forName(userServiceStr);
            //clazz.newInstance()：调用UserServiceImpl类的无参构造方法创建UserServiceImpl类的对象
            obj =  clazz.newInstance();
            //3.4将创建的对象放到map集合中
            //存放到map中是保证下次在获取指定类(UserServiceImpl，RoleServiceImpl)的对象的时候不用在执行if，直接返回obj即可
            beansMap.put(key, obj);//t1->obj=0x001 t2->obj=0x002
        }
        //3.5返回对象obj
        return (T)obj;//t1->obj=0x001 t2->obj=0x002
    }
}
