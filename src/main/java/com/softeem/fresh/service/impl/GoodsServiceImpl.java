package com.softeem.fresh.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.softeem.fresh.entity.Goods;
import com.softeem.fresh.mapper.GoodsMapper;
import com.softeem.fresh.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.apache.commons.lang.StringUtils;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("goodsService")
public class GoodsServiceImpl implements GoodsService {
    @Resource
    GoodsMapper goodsMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public List<Goods> findByPage(Map<String, Object> map) {
        return goodsMapper.findByPage(map);
    }

    @Override
    public int selectCount() {
        return goodsMapper.selectCount();
    }

    @Override
    public List<Goods> findByPageSearch(Map<String, Object> map) {
        return goodsMapper.findByPageSearch(map);
    }

    @Override
    public int selectSearchCount(String search) {
        return goodsMapper.selectSearchCount(search);
    }

    @Override
    public int addGoods(Goods goods) {
        return goodsMapper.insert(goods);
    }

    @Override
    public List<Goods> findByPageDr(Map<String, Object> map) {
        return goodsMapper.findByPageDr(map);
    }

    @Override
    public int selectCountDr(int typeDr) {
        return goodsMapper.selectCountDr(typeDr);
    }

    @Override
    public List<Goods> findByPageSearchDr(Map<String, Object> map) {
        return goodsMapper.findByPageSearchDr(map);
    }

    @Override
    public int selectSearchCountDr(Map<String, Object> map) {
        return goodsMapper.selectSearchCountDr(map);
    }

    @Override
    public int goodsUp(String id) {
        int result = goodsMapper.goodsUp(id);
        if (result == 1){
            redisTemplate.delete("goodsJSON");
            List<Goods> goods = goodsMapper.indexList();
            String s = JSON.toJSONString(goods);
            redisTemplate.opsForValue().set("goodsJSON",s);
        }
        return result;
    }

    @Override
    public int goodsDown(String id) {
        int result = goodsMapper.goodsDown(id);
        if (result == 1){
            redisTemplate.delete("goodsJSON");
            List<Goods> goods = goodsMapper.indexList();
            String s = JSON.toJSONString(goods);
            redisTemplate.opsForValue().set("goodsJSON",s);
        }
        return result;
    }

    @Override
    public int goodsDelete(String id) {
        int result = goodsMapper.goodsDelete(id);
        if (result == 1){
            redisTemplate.delete("goodsJSON");
            List<Goods> goods = goodsMapper.indexList();
            String s = JSON.toJSONString(goods);
            redisTemplate.opsForValue().set("goodsJSON",s);
        }
        return result;
    }

    @Override
    public int deleteAll(String[] ids) {
        int result = goodsMapper.deleteAll(ids);
        if (result >= 1){
            redisTemplate.delete("goodsJSON");
            List<Goods> goods = goodsMapper.indexList();
            String s = JSON.toJSONString(goods);
            redisTemplate.opsForValue().set("goodsJSON",s);
        }
        return result;
    }

    @Override
    public Goods goodsDetail(String photo) {
        return goodsMapper.goodsDetail(photo);
    }

    @Override
    public Goods loadGoods(String id) {
        return goodsMapper.selectByPrimaryKey(id);
    }

    @Override
    public int goodsUpdate(Map<String, Object> map) {
        int result = goodsMapper.goodsUpdate(map);
        if (result == 1){
            redisTemplate.delete("goodsJSON");
            List<Goods> goods = goodsMapper.indexList();
            String s = JSON.toJSONString(goods);
            redisTemplate.opsForValue().set("goodsJSON",s);
        }
        return result;
    }

    @Override
    public List<Goods> indexList() {
        String goodsJSON = redisTemplate.opsForValue().get("goodsJSON");
        if (StringUtils.isEmpty(goodsJSON)){
            List<Goods> goods = goodsMapper.indexList();
            String s = JSON.toJSONString(goods);
            redisTemplate.opsForValue().set("goodsJSON",s);
            return goods;
        }
        List<Goods> goodsJSON1 = JSON.parseObject(goodsJSON, new TypeReference<List<Goods>>(){});
        return goodsJSON1;
    }

    @Override
    public List<Goods> readMore(String groupid) {
        return goodsMapper.readMore(groupid);
    }

    @Override
    public Goods shopMessage(String id) {
        return goodsMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Goods> goodsSearch(String search) {
        return goodsMapper.goodsSearch(search);
    }

    @Override
    public int updateGoodsCount(Map<String, Object> map) {
        return goodsMapper.updateGoodsCount(map);
    }
}
