package com.itheima.case2.test;

public class Test01 {
    public static void main(String[] args) {
        //多态，s是子类对象
        Person s = new Student();
        //s = com.itheima.case2.test.Student@610455d6
        System.out.println("s = " + s);
        //使用子类对象调用父类Person中的方法
        s.speak();
    }
}
