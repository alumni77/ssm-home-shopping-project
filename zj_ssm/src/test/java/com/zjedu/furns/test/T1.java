package com.zjedu.furns.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class T1 {
    @Test
    public void t1() {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");
        System.out.println(ioc.getBean("pooledDataSource"));
    }
}
