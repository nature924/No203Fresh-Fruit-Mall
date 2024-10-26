package com.softeem.fresh.service;

import com.softeem.fresh.entity.Goodstype;

import java.util.List;
import java.util.Map;

public interface GoodstypeService {
    List<Goodstype> findByPageType(Map<String, Object> map);

    int selectCountType();

    List<Goodstype> findByPageTypeSearch(Map<String, Object> map);

    int selectSearchCounttype(String search1);

    int addGoodsType(Map<String, Object> map);

    List<Goodstype> selectGoodsType();
}
