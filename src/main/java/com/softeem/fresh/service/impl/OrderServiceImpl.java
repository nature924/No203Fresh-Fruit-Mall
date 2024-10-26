package com.softeem.fresh.service.impl;

import com.softeem.fresh.entity.Ordergoods;
import com.softeem.fresh.mapper.OrdergoodsMapper;
import com.softeem.fresh.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("orderService")
public class OrderServiceImpl implements OrderService {
    @Resource
    OrdergoodsMapper ordergoodsMapper;

    @Override
    public int addOrdergoods(Map<String, Object> map) {
        return ordergoodsMapper.addOrdergoods(map);
    }

    @Override
    public List<Ordergoods> selectOrderByuserId(String uid) {
        return ordergoodsMapper.selectOrderByuserId(uid);
    }

    @Override
    public int cancelOrder(String id) {
        return ordergoodsMapper.cancelOrder(id);
    }

    @Override
    public Ordergoods selectGoodsId(String id) {
        return ordergoodsMapper.selectByPrimaryKey(id);
    }

    @Override
    public int addAddress(Map<String, Object> map) {
        return ordergoodsMapper.addAddress(map);
    }

    @Override
    public List<Ordergoods> ordersList() {
        return ordergoodsMapper.ordersList();
    }

    @Override
    public List<Ordergoods> ordersWaitList() {
        return ordergoodsMapper.ordersWaitList();
    }

    @Override
    public List<Ordergoods> ordersEndList() {
        return ordergoodsMapper.ordersEndList();
    }

    @Override
    public int orderEnsure(String id) {
        return ordergoodsMapper.orderEnsure(id);
    }

    @Override
    public Ordergoods selectOrderById(String id) {
        return ordergoodsMapper.selectOrderById(id);
    }

    @Override
    public List<Ordergoods> ordersEndListById(String userid) {
        return ordergoodsMapper.ordersEndListById(userid);
    }
}
