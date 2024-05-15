package com.itheima.case2.test;

public class Person {
    /*
		this：谁调用就表示谁。子类对象调用就代表子类对象
	*/
    public void speak(){
        //this:com.itheima.case2.test.Student@610455d6
        //这里的this表示的是子类对象
        System.out.println("this:" + this);
    }
}
class Student extends Person{

}
