package com.zjedu.furns.test;

import com.zjedu.furns.bean.Furn;
import com.zjedu.furns.dao.FurnMapper;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;

public class FurnMapperTest {

    @Test
    public void insertSelective() {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");
        FurnMapper furnMapper = ioc.getBean(FurnMapper.class);
//        System.out.println("furnMapper--"+furnMapper.getClass());
        Furn furn = new Furn(null, "北欧风格沙发~", "晚安家居~!", new BigDecimal(180), 666,
                70, "assets/images/product-image/1.jpg");
        int affected = furnMapper.insertSelective(furn);
        System.out.println("affected= " + affected);
        System.out.println("操作成功");
    }

    @Test
    public void deleteByPrimaryKey() {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");
        FurnMapper furnMapper = ioc.getBean(FurnMapper.class);
        int affected = furnMapper.deleteByPrimaryKey(6);
        System.out.println("affected= " + affected);
        System.out.println("操作成功");
    }

    @Test
    public void updateByPrimaryKey() {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");
        FurnMapper furnMapper = ioc.getBean(FurnMapper.class);
        Furn furn = new Furn();
        furn.setId(5);
        furn.setName("晚安风格的家居-小沙发");
//        int affected = furnMapper.updateByPrimaryKey(furn);
        int affected = furnMapper.updateByPrimaryKeySelective(furn);
        System.out.println("affected= " + affected);
        System.out.println("操作成功");
    }

}
