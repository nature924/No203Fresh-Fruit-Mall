package com.softeem.fresh.controller;

import com.softeem.fresh.entity.Goodscar;
import com.softeem.fresh.entity.Ordergoods;
import com.softeem.fresh.entity.User;
import com.softeem.fresh.service.GoodsCarService;
import com.softeem.fresh.service.GoodsService;
import com.softeem.fresh.service.OrderService;
import com.softeem.fresh.service.UserService;
import com.softeem.fresh.util.App;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class OrdergoodsController {
    @Resource(name = "orderService")
    OrderService orderService;
    @Resource(name = "userService")
    UserService userService;
    @Resource(name = "goodsCarService")
    GoodsCarService goodsCarService;
    @Resource(name = "goodsService")
    GoodsService goodsService;

    /**
     * 取消订单
     */
    @RequestMapping("/cancelOrder")
    @ResponseBody
    public synchronized Map<String, Object> cancelOrder(String id,String username, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        User us = userService.findUserByUsername(username);
        Ordergoods ordergoods = orderService.selectGoodsId(id);
        if (us.getDr() == 1){
            //管理员确定订单
            if (ordergoods != null && ordergoods.getDr() != 1){
                int result = orderService.orderEnsure(id);
                Ordergoods og = orderService.selectOrderById(id);
                Map<String, Object> map2 = new HashMap<String, Object>();
                double count = (og.getGoods().getCount()-og.getGoodscar().getCount());
                map2.put("id",og.getGoods().getId());
                map2.put("count",count);
                int result1 = goodsService.updateGoodsCount(map2);
                if (result==1 && result1==1){
                    HttpSession session = request.getSession();
                    List<Ordergoods> ordersWaitList = orderService.ordersWaitList();
                    session.setAttribute("ordersWaitList",ordersWaitList);
                    map.put("status", 0);
                    map.put("message", "发货成功");
                }else {
                    map.put("status", 1);
                    map.put("message", "发货失败");
                }
            }else {
                map.put("status", 1);
                map.put("message", "发货失败");
            }
        }else {
            //用户确定订单
            if (ordergoods != null && ordergoods.getDr() != 3){
                int result = orderService.cancelOrder(id);
                if (result == 1) {
                    Ordergoods or = orderService.selectGoodsId(id);
                    String goodscar_id = or.getGoodscarid();
                    int info = goodsCarService.updateGoodscarDr(goodscar_id);
                    if (info == 1) {
                        HttpSession session = request.getSession();
                        String uname = (String) session.getAttribute("username");
                        User u = userService.findUserByUsername(uname);
                        List<Ordergoods> orders = new ArrayList<Ordergoods>();
                        List<Ordergoods> gcs = orderService.selectOrderByuserId(u.getId());
                        for (Ordergoods gs : gcs) {
                            orders.add(gs);
                        }
                        session.setAttribute("orders", orders);
                        List<Goodscar> goodscars = goodsCarService.findGoodscarByUserId(u.getId());
                        App.goodscarList = goodscars;
                        session.setAttribute("goodscarList", App.goodscarList);
                        session.setAttribute("count", App.goodscarList.size());
                        map.put("status", 0);
                        map.put("message", "订单取消成功");
                        return map;
                    }
                }
            }
            map.put("status", 1);
            map.put("message", "订单取消失败");
        }
        return map;
    }

    /**
     * 添加地址
     */
    @RequestMapping("/addressOver")
    @ResponseBody
    public Map<String, Object> addressOver(String address, String ids, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        String[] gcids = ids.split(",");
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("address",address);
        map1.put("gcids",gcids);
        int result = orderService.addAddress(map1);
        if (result <= 0){
            map.put("status", 1);
            map.put("message", "添加地址失败");
        }else {
            App.ADDRESS = address;
            HttpSession session = request.getSession();
            session.setAttribute("address",App.ADDRESS);
            map.put("status", 0);
            map.put("message", "添加地址成功");
        }
        return map;
    }

    /**
     * 全部的订单列表
     */
    @RequestMapping("/ordersList")
    @ResponseBody
    public Map<String, Object> ordersList(HttpServletRequest request){
        Map<String, Object> map = new HashMap<String, Object>();
        HttpSession session = request.getSession();
        List<Ordergoods> ordersList = orderService.ordersList();
        if (ordersList.size()<=0){
            map.put("status", 1);
            map.put("message", "暂无订单");
        }else {
            session.setAttribute("ordersList",ordersList);
            map.put("status", 0);
            map.put("message", "订单查询成功");
        }
        return map;
    }

    /**
     * 待发货的订单
     */
    @RequestMapping("/ordersWaitList")
    @ResponseBody
    public Map<String, Object> ordersWaitList(HttpServletRequest request){
        Map<String, Object> map = new HashMap<String, Object>();
        HttpSession session = request.getSession();
        List<Ordergoods> ordersWaitList = orderService.ordersWaitList();
        if (ordersWaitList.size()<=0){
            session.setAttribute("ordersWaitList",ordersWaitList);
            map.put("status", 1);
            map.put("message", "暂无订单");
        }else {
            session.setAttribute("ordersWaitList",ordersWaitList);
            map.put("status", 0);
            map.put("message", "订单查询成功");
        }
        return map;
    }

    /**
     * 已发货的订单
     */
    @RequestMapping("/ordersEndList")
    @ResponseBody
    public Map<String, Object> ordersEndList(HttpServletRequest request){
        Map<String, Object> map = new HashMap<String, Object>();
        HttpSession session = request.getSession();
        List<Ordergoods> ordersEndList = orderService.ordersEndList();
        if (ordersEndList.size()<=0){
            map.put("status", 1);
            map.put("message", "暂无订单");
        }else {
            session.setAttribute("ordersEndList",ordersEndList);
            map.put("status", 0);
            map.put("message", "订单查询成功");
        }
        return map;
    }

//    /**
//     * 进行发货
//     */
//    @RequestMapping("/orderEnsure")
//    @ResponseBody
//    public Map<String, Object> orderEnsure(String id,String reserved1,HttpServletRequest request){
//        Map<String, Object> map = new HashMap<String, Object>();
//        HttpSession session = request.getSession();
//        int result = orderService.orderEnsure(id);
//        if (result == 1){
//            map.put("status", 0);
//            map.put("message", "发货成功");
//        }else {
//            map.put("status", 1);
//            map.put("message", "发货失败");
//        }
//        return map;
//    }
}
