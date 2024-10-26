package com.softeem.fresh.service;

import com.softeem.fresh.entity.Goodscar;

import java.util.List;
import java.util.Map;

public interface GoodsCarService {
    List<Goodscar> findGoodscarByUserId(String userid);

    int updateGoodsCarByCount(Map<String, Object> map);

    int addGoodsCar(Map<String, Object> map1);

    int goodsDelete(String id);

    int totalAllGoods(String[] gcids);

    List<Goodscar> findGoodscarDrByuserId(String id);

    int updateGoodscarDr(String goodscar_id);

    void updateGoodsCarCount(Map<String, Object> map3);

    List<Goodscar> findGoodscarDrById(String[] gcids);
}
