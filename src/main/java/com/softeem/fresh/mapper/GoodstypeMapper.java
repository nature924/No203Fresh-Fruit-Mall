package com.softeem.fresh.mapper;

import com.softeem.fresh.entity.Goodstype;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface GoodstypeMapper {
    int deleteByPrimaryKey(String id);

    int insert(Goodstype record);

    int insertSelective(Goodstype record);

    Goodstype selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Goodstype record);

    int updateByPrimaryKey(Goodstype record);

    List<Goodstype> findByPageType(Map<String, Object> map);

    int selectCountType();

    List<Goodstype> findByPageTypeSearch(Map<String, Object> map);

    int selectSearchCounttype(String search);

    List<Goodstype> selectGoodsType();

    int addGoodsType(Map<String, Object> map);
}