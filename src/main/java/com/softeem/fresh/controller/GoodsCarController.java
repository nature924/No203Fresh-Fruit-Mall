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
public class GoodsCarController {
    @Resource(name = "goodsCarService")
    GoodsCarService goodsCarService;
    @Resource(name = "userService")
    UserService userService;
    @Resource(name = "orderService")
    OrderService orderService;

    /**
     * 加入购物车
     */
    @RequestMapping("/addGoodsCar")
    @ResponseBody
    public Map<String, Object> addGoodsCar(String goodsid, String shuliang, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        User u = userService.findUserByUsername(username);
        String userid = u.getId();
        List<Goodscar> goodscars = goodsCarService.findGoodscarByUserId(userid);
        for (Goodscar gs : goodscars) {
            if (gs.getGoodsid().equals(goodsid)){
                double count = Double.parseDouble(shuliang);
                count += gs.getCount();
                Map<String, Object> map1 = new HashMap<String, Object>();
                int reserved1 = (int) count;
                map1.put("count",count);
                map1.put("goodsid",goodsid);
                map1.put("reserved1",String.valueOf(reserved1));
                int result = goodsCarService.updateGoodsCarByCount(map1);
                if (result == 1){
                    List<Goodscar> gds = goodsCarService.findGoodscarByUserId(userid);
                    App.goodscarList = gds;
                    session.setAttribute("goodscarList", App.goodscarList);
                    session.setAttribute("count", App.goodscarList.size());
                    map.put("status", 0);
                    map.put("message", "加入购物车成功");
                }else {
                    map.put("status", 1);
                    map.put("message", "加入购物车失败");
                }
                return map;
            }
        }
        String id = App.getUid();
        int dr = 1;
        double count = Double.parseDouble(shuliang);
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("id",id);
        map1.put("goodsid",goodsid);
        map1.put("userid",userid);
        map1.put("dr",dr);
        map1.put("count",count);
        map1.put("reserved1",shuliang);
        int result = goodsCarService.addGoodsCar(map1);
        if (result == 1) {
            List<Goodscar> gods = goodsCarService.findGoodscarByUserId(userid);
            App.goodscarList = gods;
            session.setAttribute("goodscarList", App.goodscarList);
            session.setAttribute("count", App.goodscarList.size());
            map.put("status", 0);
            map.put("message", "加入购物车成功");
        } else {
            map.put("status", 1);
            map.put("message", "加入购物车失败");
        }
        return map;
    }

    /**
     * 购物车的商品单个逻辑删除
     */
    @RequestMapping("/goodscarDelete")
    @ResponseBody
    public Map<String, Object> goodscarDelete(String id, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        int result = goodsCarService.goodsDelete(id);
        if (result == 1) {
            HttpSession session = request.getSession();
            String username = (String) session.getAttribute("username");
            User u = userService.findUserByUsername(username);
            List<Goodscar> goodscars = goodsCarService.findGoodscarByUserId(u.getId());
            App.goodscarList = goodscars;
            session.setAttribute("goodscarList", App.goodscarList);
            session.setAttribute("count", App.goodscarList.size());
            map.put("status", 0);
            map.put("message", "删除成功");
        } else {
            map.put("status", 1);
            map.put("message", "删除失败");
        }
        return map;
    }

    /**
     * 结算商品
     */
    @RequestMapping("/totalAllGoods")
    @ResponseBody
    public Map<String, Object> totalAllGoods(String ids,String reserved1s, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        HttpSession session = request.getSession();
        String[] gcids = ids.split(",");
        String[] resves = reserved1s.split(",");
        Map<String, Object> map3 = new HashMap<String, Object>();
        for (int i=0;i<gcids.length;i++){
            int reserved1 = Integer.parseInt(resves[i]);
            map3.put("count",reserved1);
            map3.put("id",gcids[i]);
            map3.put("reserved1",String.valueOf(reserved1));
            goodsCarService.updateGoodsCarCount(map3);
        }
        int result = goodsCarService.totalAllGoods(gcids);
        if (result <= 0) {
            map.put("status", 1);
            map.put("message", "交易失败");
        } else {
            String uname = (String) session.getAttribute("username");
            User u = userService.findUserByUsername(uname);
            List<Goodscar> goodscars = goodsCarService.findGoodscarDrById(gcids);
            List<Ordergoods> orders = new ArrayList<Ordergoods>();
            Map<String, Object> map1 = new HashMap<String, Object>();
            for (Goodscar gc : goodscars) {
                String id = App.getUid();
                String uid = gc.getUserid();
                String gid = gc.getGoodsid();
                String gcid = gc.getId();
                int dr = gc.getDr();
                map1.put("id",id);
                map1.put("userid",uid);
                map1.put("goodsid",gid);
                map1.put("goodscarid",gcid);
                map1.put("dr",dr);
                orderService.addOrdergoods(map1);
            }
            List<Ordergoods> gcs = orderService.selectOrderByuserId(u.getId());
            for (Ordergoods gs : gcs) {
                orders.add(gs);
            }
            if (orders.size() <= 0) {
                map.put("status", 1);
                map.put("message", "交易失败");
            } else {
                session.setAttribute("orders", orders);
                String userid = u.getId();
                List<Goodscar> gc = goodsCarService.findGoodscarByUserId(userid);
                App.goodscarList = gc;
                session.setAttribute("goodscarList", App.goodscarList);
                session.setAttribute("count", App.goodscarList.size());
                map.put("status", 0);
                map.put("message", "交易成功");
            }
        }
        return map;
    }

    /**
     * 立即购买
     */
    @RequestMapping("/buyGoodsImmediately")
    @ResponseBody
    public Map<String, Object> buyGoodsImmediately(String goodsid, String shuliang,HttpServletRequest request){
        Map<String, Object> map = new HashMap<String, Object>();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        User u = userService.findUserByUsername(username);
        String userid = u.getId();
        String id = App.getUid();
        int dr = 0;
        double count = Double.parseDouble(shuliang);
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("id",id);
        map1.put("goodsid",goodsid);
        map1.put("userid",userid);
        map1.put("dr",dr);
        map1.put("count",count);
        map1.put("reserved1",shuliang);
        int result = goodsCarService.addGoodsCar(map1);
        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("id",App.getUid());
        map2.put("userid",userid);
        map2.put("goodsid",goodsid);
        map2.put("goodscarid",id);
        map2.put("dr",dr);
        int result2 = orderService.addOrdergoods(map2);
        if (result == 1 && result2 == 1){
            List<Ordergoods> orders = new ArrayList<Ordergoods>();
            List<Ordergoods> gcs = orderService.selectOrderByuserId(u.getId());
            for (Ordergoods gs : gcs) {
                orders.add(gs);
            }
            session.setAttribute("orders", orders);
            map.put("status", 0);
            map.put("message", "购买成功");
            map.put("result",id);
        }else {
            map.put("status", 1);
            map.put("message", "购买失败");
            map.put("result",null);
        }
        return map;
    }
}
