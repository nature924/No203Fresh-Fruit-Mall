package com.softeem.fresh.controller;

import com.softeem.fresh.entity.Goodstype;
import com.softeem.fresh.entity.PageBean;
import com.softeem.fresh.service.GoodstypeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class GoodstypeController {
    @Resource(name = "goodstypeService")
    GoodstypeService goodstypeService;

    /**
     * 分页查询增加商品分类列表信息
     */
    @RequestMapping("/addGoodsTypeList")
    @ResponseBody
    public Map<String, Object> addGoodsTypeList(int currentPage, String search) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        PageBean<Goodstype> pageBean = new PageBean<Goodstype>();
        int pageSize = 5;
        int start = (currentPage - 1) * pageSize;
        int size = pageSize;
        if (search == "" || search == null) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("start",start);
            map.put("size",size);
            List<Goodstype> lists = goodstypeService.findByPageType(map);
//            List<Goodstype> list = goodstypeService.selectCountType();
            int count = goodstypeService.selectCountType();
            if (count <= 0) {
                resultMap.put("status", 1);
                resultMap.put("message", "没有数据");
                resultMap.put("result", null);
            } else {
                int totalCount = count;
                double tc = totalCount;
                int totalPage = (int) Math.ceil(tc / pageSize);
                pageBean.setCurrentage(currentPage);
                pageBean.setPageSize(pageSize);
                pageBean.setTotalCount(totalCount);
                pageBean.setTotalPage(totalPage);
                pageBean.setLists(lists);
                resultMap.put("status", 0);
                resultMap.put("message", "有数据");
                resultMap.put("result", pageBean);
            }
        } else {
//            String search1 = "";
//            search1 += ("%" + search + "%");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("start",start);
            map.put("size",size);
            map.put("search",search);
            List<Goodstype> lists = goodstypeService.findByPageTypeSearch(map);
//            List<Goodstype> list = goodstypeService.selectSearchCounttype(search1);
            int count = goodstypeService.selectSearchCounttype(search);
            if (count <= 0) {
                resultMap.put("status", 1);
                resultMap.put("message", "没有数据");
                resultMap.put("result", null);
            } else {
                int totalCount = count;
                double tc = totalCount;
                int totalPage = (int) Math.ceil(tc / pageSize);
                pageBean.setCurrentage(currentPage);
                pageBean.setPageSize(pageSize);
                pageBean.setTotalCount(totalCount);
                pageBean.setTotalPage(totalPage);
                pageBean.setLists(lists);
                resultMap.put("status", 0);
                resultMap.put("message", "有数据");
                resultMap.put("result", pageBean);
            }
        }
        return resultMap;
    }

    /**
     * 添加商品分类
     */
    @RequestMapping("/addGoodsType")
    @ResponseBody
    public Map<String, Object> addGoodsType(String name) {
        Map<String, Object> map = new HashMap<String, Object>();
        int dr = 0;
        String id = name;
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("id",id);
        map1.put("name",name);
        map1.put("dr",dr);
        int result = goodstypeService.addGoodsType(map1);
        if (result == 1) {
            map.put("status", 0);
            map.put("message", "添加成功");
        } else {
            map.put("status", 1);
            map.put("message", "添加失败");
        }
        return map;
    }

    /**
     * 查询商品分类
     */
    @RequestMapping("/selectGoodsType")
    @ResponseBody
    public Map<String, Object> selectGoodsType() {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Goodstype> goodstypes = goodstypeService.selectGoodsType();
        if (goodstypes != null) {
            map.put("status", 0);
            map.put("message", "查询成功");
            map.put("result", goodstypes);
        } else {
            map.put("status", 1);
            map.put("message", "查询失败");
            map.put("result", null);
        }
        return map;
    }
}
