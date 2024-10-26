package com.softeem.fresh.service.impl;

import com.softeem.fresh.entity.Goodstype;
import com.softeem.fresh.mapper.GoodstypeMapper;
import com.softeem.fresh.service.GoodstypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("goodstypeService")
public class GoodstypeServiceImpl implements GoodstypeService {
    @Resource
    GoodstypeMapper goodstypeMapper;

    @Override
    public List<Goodstype> findByPageType(Map<String, Object> map) {
        return goodstypeMapper.findByPageType(map);
    }

    @Override
    public int selectCountType() {
        return goodstypeMapper.selectCountType();
    }

    @Override
    public List<Goodstype> findByPageTypeSearch(Map<String, Object> map) {
        return goodstypeMapper.findByPageTypeSearch(map);
    }

    @Override
    public int selectSearchCounttype(String search) {
        return goodstypeMapper.selectSearchCounttype(search);
    }

    @Override
    public int addGoodsType(Map<String, Object> map) {
        return goodstypeMapper.addGoodsType(map);
    }

    @Override
    public List<Goodstype> selectGoodsType() {
        return goodstypeMapper.selectGoodsType();
    }
}
