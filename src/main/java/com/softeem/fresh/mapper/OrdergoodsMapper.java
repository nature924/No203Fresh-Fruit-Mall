package com.softeem.fresh.mapper;

import com.softeem.fresh.entity.Ordergoods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrdergoodsMapper {
    int deleteByPrimaryKey(String id);

    int insert(Ordergoods record);

    int insertSelective(Ordergoods record);

    Ordergoods selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Ordergoods record);

    int updateByPrimaryKey(Ordergoods record);

    int addOrdergoods(Map<String, Object> map);

    List<Ordergoods> selectOrderByuserId(String userid);

    int cancelOrder(String id);

    int addAddress(Map<String, Object> map);

    List<Ordergoods> ordersList();

    List<Ordergoods> ordersWaitList();

    List<Ordergoods> ordersEndList();

    int orderEnsure(String id);

    Ordergoods selectOrderById(String id);

    List<Ordergoods> ordersEndListById(@Param("userid") String userid);
}