package com.softeem.fresh.controller;

import com.softeem.fresh.entity.Goods;
import com.softeem.fresh.entity.PageBean;
import com.softeem.fresh.service.GoodsService;
import com.softeem.fresh.util.App;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class GoodsController {
    @Resource(name = "goodsService")
    GoodsService goodsService;

    List<Goods> gdsList = null;

    List<Goods> goodsBrowse;

    public GoodsController() {
        goodsBrowse = new ArrayList<Goods>();
    }

    /**
     * 分页查询商品列表信息
     */
    @RequestMapping("/goodsList")
    @ResponseBody
    public Map<String, Object> goodsList(int currentPage, String search) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        PageBean<Goods> pageBean = new PageBean<Goods>();
        int pageSize = 5;
        int start = (currentPage - 1) * pageSize;
        int size = pageSize;
        if (search == "" || search == null) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("start",start);
            map.put("size",size);
            List<Goods> lists = goodsService.findByPage(map);
//            List<Goods> list = goodsService.selectCount();
            int count = goodsService.selectCount();
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
//            System.out.println(search1);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("start",start);
            map.put("size",size);
            map.put("search",search);
            List<Goods> lists = goodsService.findByPageSearch(map);
//            List<Goods> list = goodsService.selectSearchCount(search1);
            int count = goodsService.selectSearchCount(search);
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
     * 添加商品
     */
    @RequestMapping("/addGoods")
    @ResponseBody
    public Map<String, Object> addGoods(MultipartFile file, Goods goods) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            //获取文件名
            String pname = file.getOriginalFilename();
            //获取文件格式
            String type = pname.substring(pname.lastIndexOf("."));
            //重命名
            String photo_name = App.getUid() + type;
            //将图片存到指定的位置
            //windows系统配置图片路径
            file.transferTo(new File("E:\\我的发货\\Springboot生鲜水果商城\\图片素材\\GoodsPhoto\\", photo_name));
            //mac系统配置图片路径
            //file.transferTo(new File("/Users/hanmeng/Desktop/java源码/Springboot生鲜水果商城/源码/GoodsPhoto", photo_name));
            //将图片信息存入商品对象
            goods.setPhoto(photo_name);
            //商品id
            goods.setId(App.getUid());
            //商品状态
            goods.setDr(1);
            int i = goodsService.addGoods(goods);
            if (i == 1) {
                map.put("status", 0);
                map.put("message", "添加成功");
            } else {
                map.put("status", 1);
                map.put("message", "添加失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 分页查询上架或下架商品列表信息
     */
    @RequestMapping("/goodsTypeList")
    @ResponseBody
    public Map<String, Object> goodsTypeList(int currentPage, String search, int typeDr) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        PageBean<Goods> pageBean = new PageBean<Goods>();
        int pageSize = 5;
        int start = (currentPage - 1) * pageSize;
        int size = pageSize;
        if (search == "" || search == null) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("start",start);
            map.put("size",size);
            map.put("typeDr",typeDr);
            List<Goods> lists = goodsService.findByPageDr(map);
//            List<Goods> list = goodsService.selectCountDr(typeDr);
            int count = goodsService.selectCountDr(typeDr);
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
            map.put("typeDr",typeDr);
            map.put("search",search);
            List<Goods> lists = goodsService.findByPageSearchDr(map);
            Map<String, Object> map1 = new HashMap<String, Object>();
            map1.put("typeDr",typeDr);
            map1.put("search",search);
//            List<Goods> list = goodsService.selectSearchCountDr(map1);
            int count = goodsService.selectSearchCountDr(map1);
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
     * 商品上架
     */
    @RequestMapping("/goodsUp")
    @ResponseBody
    public Map<String, Object> goodsUp(String id) {
        Map<String, Object> map = new HashMap<String, Object>();
        int result = goodsService.goodsUp(id);
        if (result == 1) {
            map.put("status", 0);
            map.put("message", "上架成功");
        } else {
            map.put("status", 1);
            map.put("message", "上架失败");
        }
        return map;
    }

    /**
     * 商品下架
     */
    @RequestMapping("/goodsDown")
    @ResponseBody
    public Map<String, Object> goodsDown(String id) {
        Map<String, Object> map = new HashMap<String, Object>();
        int result = goodsService.goodsDown(id);
        if (result == 1) {
            map.put("status", 0);
            map.put("message", "下架成功");
        } else {
            map.put("status", 1);
            map.put("message", "下架失败");
        }
        return map;
    }

    /**
     * 商品删除
     */
    @RequestMapping("/goodsDelete")
    @ResponseBody
    public Map<String, Object> goodsDelete(String id) {
        Map<String, Object> map = new HashMap<String, Object>();
        int result = goodsService.goodsDelete(id);
        if (result == 1) {
            map.put("status", 0);
            map.put("message", "删除成功");
        } else {
            map.put("status", 1);
            map.put("message", "删除失败");
        }
        return map;
    }

    /**
     * 商品批量删除
     */
    @RequestMapping("/deleteAll")
    @ResponseBody
    public Map<String, Object> deleteAll(String ids) {
        Map<String, Object> map = new HashMap<String, Object>();
        String[] id_s = ids.split(",");
        int result = goodsService.deleteAll(id_s);
        if (result >= 1) {
            map.put("status", 0);
            map.put("message", "删除成功");
        } else {
            map.put("status", 1);
            map.put("message", "删除失败");
        }
        return map;
    }

    /**
     * 管理员查看商品详情
     */
    @RequestMapping("/goodsDetail")
    @ResponseBody
    public Map<String, Object> goodsDetail(String photo) {
        Map<String, Object> map = new HashMap<String, Object>();
        Goods goods = goodsService.goodsDetail(photo);
        if (goods != null) {
            map.put("status", 0);
            map.put("message", "加载成功");
            map.put("result", goods);
        } else {
            map.put("status", 1);
            map.put("message", "加载失败");
            map.put("result", null);
        }
        return map;
    }

    /**
     * 加载商品信息
     */
    @RequestMapping("/loadGoods")
    @ResponseBody
    public Map<String, Object> loadGoods(String id) {
        Map<String, Object> map = new HashMap<String, Object>();
        Goods goods = goodsService.loadGoods(id);
        if (goods != null) {
            map.put("status", 0);
            map.put("message", "加载编辑成功");
            map.put("result", goods);
        } else {
            map.put("status", 1);
            map.put("message", "加载编辑失败");
            map.put("result", null);
        }
        return map;
    }

    /**
     * 商品编辑
     */
    @RequestMapping("/goodsUpdate")
    @ResponseBody
    public Map<String, Object> goodsUpdate(String id, String price, String count) {
        Map<String, Object> map = new HashMap<String, Object>();
        double price_double = Double.parseDouble(price);
        double count_double = Double.parseDouble(count);
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("id",id);
        map1.put("price",price_double);
        map1.put("count",count_double);
        int result = goodsService.goodsUpdate(map1);
        if (result == 1) {
            map.put("status", 0);
            map.put("message", "编辑成功");
        } else {
            map.put("status", 1);
            map.put("message", "编辑失败");
        }
        return map;
    }

    /**
     * 商品主页加载
     */
    @RequestMapping("/indexList")
    @ResponseBody
    public Map<String, Object> indexList() {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Goods> goodslist = goodsService.indexList();
        if (goodslist.size() <= 0) {
            map.put("status", 1);
            map.put("message", "暂无上架商品");
            map.put("result", null);
        } else {
            map.put("status", 0);
            map.put("message", "正在加载，请稍后");
            map.put("result", goodslist);
        }
        return map;
    }

    /**
     * 查看更多
     */
//    @RequestMapping("/readMore")
//    public String readMore(String groupid, Model model) {
//        List<Goods> goodslist = goodsService.readMore(groupid);
//        if (goodslist.size() <= 0) {
//            return "redirect:toIndex";
//        } else {
//            List<Goods> goodsPart = new ArrayList<Goods>();
//            if (goodslist.size()>=2){
//                goodsPart.add(goodslist.get(goodslist.size() - 2));
//                goodsPart.add(goodslist.get(goodslist.size() - 1));
//            }else if((goodslist.size()==1)){
//                goodsPart.add(goodslist.get(goodslist.size() - 1));
//            }
//            model.addAttribute("groupid", groupid);
//            model.addAttribute("goodsPart", goodsPart);
//            model.addAttribute("goodslist", goodslist);
//        }
//        return "readmoreShop";
//    }
    @RequestMapping("/readMore")
    @ResponseBody
    public Map<String, Object> readMore(String groupid) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Goods> goodslist = goodsService.readMore(groupid);
        if (goodslist.size() <= 0) {
            map.put("status",1);
            map.put("message","抱歉没有相关的商品");
        } else {
            map.put("status",0);
            map.put("message","商品展现成功");
            gdsList = goodslist;
        }
        return map;
    }

    @GetMapping("/readMoreByMessage")
    public String readMoreByMessage(String groupid, Model model){
        List<Goods> goodslist = goodsService.readMore(groupid);
        List<Goods> goodsPart = new ArrayList<Goods>();
        if (goodslist.size()>=2){
            goodsPart.add(goodslist.get(goodslist.size() - 2));
            goodsPart.add(goodslist.get(goodslist.size() - 1));
        }else if((goodslist.size()==1)){
            goodsPart.add(goodslist.get(goodslist.size() - 1));
        }
        model.addAttribute("groupid", groupid);
        model.addAttribute("goodsPart", goodsPart);
        model.addAttribute("goodslist", goodslist);
        return "readmoreShop";
    }

    @RequestMapping("/readMoreSecond")
    public String readMoreSecond(String groupid, Model model){
        if (gdsList!=null){
            List<Goods> goodsPart = new ArrayList<Goods>();
            if (gdsList.size()>=2){
                goodsPart.add(gdsList.get(gdsList.size() - 2));
                goodsPart.add(gdsList.get(gdsList.size() - 1));
            }else if((gdsList.size()==1)){
                goodsPart.add(gdsList.get(gdsList.size() - 1));
            }
            model.addAttribute("groupid", groupid);
            model.addAttribute("goodsPart", goodsPart);
            model.addAttribute("goodslist", gdsList);
            return "readmoreShop";
        }
        return null;
    }

    /**
     * 用户查看商品详情
     */
    @RequestMapping("/shopMessage")
    public String shopMessage(String id, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Goods goods = goodsService.shopMessage(id);
        if (goods == null) {
            return "redirect:toIndex";
        } else {
            if (goodsBrowse.size() >= 1) {
                for (int i = 0; i < goodsBrowse.size(); i++) {
                    if (goods.getName().equals(goodsBrowse.get(i).getName())) {
                        goodsBrowse.remove(i);
                    }
                }
            }
            goodsBrowse.add(goods);
            if (goodsBrowse.size() > 4) {
                goodsBrowse.remove(0);
            }
            session.setAttribute("goodsBrowse", goodsBrowse);
            String groupid = goods.getGroupid();
            List<Goods> goodslist = goodsService.readMore(groupid);
            List<Goods> goodsNews = new ArrayList<Goods>();
            if (goodslist.size()>=2){
                goodsNews.add(goodslist.get(goodslist.size() - 2));
                goodsNews.add(goodslist.get(goodslist.size() - 1));
            }else if(goodslist.size()==1){
                goodsNews.add(goodslist.get(goodslist.size() - 1));
            }
            model.addAttribute("goodsNews", goodsNews);
            model.addAttribute("goods", goods);
        }
        return "shop_message";
    }

    /**
     * 商品主页搜索
     */
    @RequestMapping("/goodsSearch")
    public String goodsSearch(String search, Model model) {
        if (search == "" || search == null) {
            return "redirect:toNoPage";
        }
        List<Goods> goodslist = goodsService.goodsSearch(search);
        if (goodslist.size() <= 0) {
            return "redirect:toNoPage";
        } else {
            model.addAttribute("goodslist", goodslist);
        }
        return "searchmoreShop";
    }
}
