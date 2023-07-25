package com.zjedu.furns.service.impl;

import com.zjedu.furns.bean.Furn;
import com.zjedu.furns.bean.FurnExample;
import com.zjedu.furns.dao.FurnMapper;
import com.zjedu.furns.service.FurnService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FurnServiceImpl implements FurnService {

    @Resource
    private FurnMapper furnMapper;

    @Override
    public void save(Furn furn) {
        furnMapper.insertSelective(furn);
    }

    @Override
    public List<Furn> findAll() {
        return furnMapper.selectByExample(null);
    }

    @Override
    public void update(Furn furn) {
        furnMapper.updateByPrimaryKeySelective(furn);
    }

    @Override
    public void del(Integer id) {
        furnMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Furn findById(Integer id) {
        return furnMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Furn> findByCondition(String name) {
        FurnExample furnExample = new FurnExample();
        FurnExample.Criteria criteria = furnExample.createCriteria();
        if (StringUtils.hasText(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        return furnMapper.selectByExample(furnExample);
    }
}
