package com.softeem.fresh.service;

import com.softeem.fresh.entity.Ordergoods;

import java.util.List;
import java.util.Map;

public interface OrderService {
    int addOrdergoods(Map<String, Object> map1);

    List<Ordergoods> selectOrderByuserId(String uid);

    int cancelOrder(String id);

    Ordergoods selectGoodsId(String id);

    int addAddress(Map<String, Object> map);

    List<Ordergoods> ordersList();

    List<Ordergoods> ordersWaitList();

    List<Ordergoods> ordersEndList();

    int orderEnsure(String id);

    Ordergoods selectOrderById(String id);

    List<Ordergoods> ordersEndListById(String userid);
}
