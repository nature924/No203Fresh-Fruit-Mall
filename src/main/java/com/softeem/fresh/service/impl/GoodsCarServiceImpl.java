package com.softeem.fresh.service.impl;

import com.softeem.fresh.entity.Goodscar;
import com.softeem.fresh.mapper.GoodscarMapper;
import com.softeem.fresh.service.GoodsCarService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("goodsCarService")
public class GoodsCarServiceImpl implements GoodsCarService {
    @Resource
    GoodscarMapper goodscarMapper;

    @Override
    public List<Goodscar> findGoodscarByUserId(String userid) {
        return goodscarMapper.findGoodscarByUserId(userid);
    }

    @Override
    public int updateGoodsCarByCount(Map<String, Object> map) {
        return goodscarMapper.updateGoodsCarByCount(map);
    }

    @Override
    public int addGoodsCar(Map<String, Object> map) {
        return goodscarMapper.addGoodsCar(map);
    }

    @Override
    public int goodsDelete(String id) {
        return goodscarMapper.goodsDelete(id);
    }

    @Override
    public int totalAllGoods(String[] gcids) {
        return goodscarMapper.totalAllGoods(gcids);
    }

    @Override
    public List<Goodscar> findGoodscarDrByuserId(String id) {
        return goodscarMapper.findGoodscarDrByuserId(id);
    }

    @Override
    public int updateGoodscarDr(String goodscar_id) {
        return goodscarMapper.updateGoodscarDr(goodscar_id);
    }

    @Override
    public void updateGoodsCarCount(Map<String, Object> map) {
        goodscarMapper.updateGoodsCarCount(map);
    }

    @Override
    public List<Goodscar> findGoodscarDrById(String[] gcids) {
        return goodscarMapper.findGoodscarDrById(gcids);
    }
}
