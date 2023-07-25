package com.zjedu.furns.service;

import com.zjedu.furns.bean.Furn;

import java.util.List;

public interface FurnService {
    public void save(Furn furn);

    public List<Furn> findAll();

    public void update(Furn furn);

    public void del(Integer id);

    public Furn findById(Integer id);

    public List<Furn> findByCondition(String name);

}
