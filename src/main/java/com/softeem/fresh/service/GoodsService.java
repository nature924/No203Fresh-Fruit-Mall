package com.softeem.fresh.service;

import com.softeem.fresh.entity.Goods;

import java.util.List;
import java.util.Map;

public interface GoodsService {
    List<Goods> findByPage(Map<String, Object> map);

    int selectCount();

    List<Goods> findByPageSearch(Map<String, Object> map);

    int selectSearchCount(String search1);

    int addGoods(Goods goods);

    List<Goods> findByPageDr(Map<String, Object> map);

    int selectCountDr(int typeDr);

    List<Goods> findByPageSearchDr(Map<String, Object> map);

    int selectSearchCountDr(Map<String, Object> map1);

    int goodsUp(String id);

    int goodsDown(String id);

    int goodsDelete(String id);

    int deleteAll(String[] ids);

    Goods goodsDetail(String photo);

    Goods loadGoods(String id);

    int goodsUpdate(Map<String, Object> map1);

    List<Goods> indexList();

    List<Goods> readMore(String groupid);

    Goods shopMessage(String id);

    List<Goods> goodsSearch(String search);

    int updateGoodsCount(Map<String, Object> map);
}
