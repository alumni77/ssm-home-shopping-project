package com.zjedu.furns.test.service;

import com.zjedu.furns.bean.Furn;
import com.zjedu.furns.service.FurnService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.List;

public class FurnServiceImplTest {
    private ApplicationContext ioc;
    private FurnService furnService;

    @Before
    public void init() {
        ioc = new ClassPathXmlApplicationContext("applicationContext.xml");
        furnService = ioc.getBean(FurnService.class);
        System.out.println("furnService= " + furnService.getClass());
    }

    @Test
    public void save() {
        Furn furn = new Furn(null, "小风扇~!!", "晚安家居~!", new BigDecimal(180), 10,
                70, "assets/images/product-image/1.jpg");
        furnService.save(furn);
        System.out.println("ok~");
    }

    @Test
    public void findAll() {
        List<Furn> furns = furnService.findAll();
        for (Furn furn : furns) {
            System.out.println("furn- " + furn);
        }
    }

    @Test
    public void update() {
        Furn furn = new Furn();
        furn.setId(12);
        furn.setName("5666");
        furn.setMaker("大象家居");
        furn.setImgPath(null);
        furnService.update(furn);
    }

    @Test
    public void del() {
        furnService.del(12);
    }

    @Test
    public void findByCondition() {
        List<Furn> furns = furnService.findByCondition("风格");
        for (Furn furn : furns) {
            System.out.println("furn--" + furn);
        }
    }

}
