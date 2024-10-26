package com.softeem.fresh.mapper;

import com.softeem.fresh.entity.Goodscar;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface GoodscarMapper {
    int deleteByPrimaryKey(String id);

    int insert(Goodscar record);

    int insertSelective(Goodscar record);

    Goodscar selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Goodscar record);

    int updateByPrimaryKeyWithBLOBs(Goodscar record);

    int updateByPrimaryKey(Goodscar record);

    List<Goodscar> findGoodscarByUserId(String userid);

    int updateGoodsCarByCount(Map<String, Object> map);

    int addGoodsCar(Map<String, Object> map);

    int goodsDelete(String id);

    int totalAllGoods(String[] gcids);

    List<Goodscar> findGoodscarDrByuserId(String id);

    int updateGoodscarDr(String id);

    void updateGoodsCarCount(Map<String, Object> map);

    List<Goodscar> findGoodscarDrById(String[] gcids);
}