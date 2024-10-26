package com.softeem.fresh.mapper;

import com.softeem.fresh.entity.Goods;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface GoodsMapper {
    int deleteByPrimaryKey(String id);

    int insert(Goods record);

    int insertSelective(Goods record);

    Goods selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKeyWithBLOBs(Goods record);

    int updateByPrimaryKey(Goods record);

    List<Goods> findByPage(Map<String, Object> map);

    int selectCount();

    List<Goods> findByPageSearch(Map<String, Object> map);

    int selectSearchCount(String search);

    List<Goods> findByPageDr(Map<String, Object> map);

    int selectCountDr(int typeDr);

    List<Goods> findByPageSearchDr(Map<String, Object> map);

    int selectSearchCountDr(Map<String, Object> map);

    int goodsUp(String id);

    int goodsDown(String id);

    int goodsDelete(String id);

    int deleteAll(String[] ids);

    Goods goodsDetail(String photo);

    int goodsUpdate(Map<String, Object> map);

    List<Goods> indexList();

    List<Goods> readMore(String groupid);

    List<Goods> goodsSearch(String search);

    int updateGoodsCount(Map<String, Object> map);
}