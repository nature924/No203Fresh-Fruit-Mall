package com.softeem.fresh.controller;

import com.softeem.fresh.entity.Ordergoods;
import com.softeem.fresh.entity.User;
import com.softeem.fresh.service.OrderService;
import com.softeem.fresh.service.UserService;
import com.softeem.fresh.util.App;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 通用控制层
 */
@Controller
public class CommonController {
    @Resource(name = "orderService")
    OrderService orderService;
    @Resource(name = "userService")
    UserService userService;

    //用户登录
    @RequestMapping("/toUserLogin")
    public String userLogin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("saveUsername", App.USERNAME);
        return "login";
    }

    //用户注册
    @RequestMapping("/toUserRegister")
    public String userRegister() {
        return "register";
    }

    //用户修改密码
    @RequestMapping("/userToForgetPwd")
    public String toForgetPwd(){
        return "fogetPwd";
    }

    //管理员登录
    @RequestMapping("/toAdminLogin")
    public String adminLogin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("saveadminUsername", App.ADMIN_USERNAME);
        session.setAttribute("saveadminPassword", App.ADMIN_PASSWORD);
        return "admin_login";
    }

    //管理员修改密码
    @RequestMapping("/userToAdminForgetpwd")
    public String toAdminForgetpwd(){
        return "admin_forgetPwd";
    }

    //管理员信息页
    @RequestMapping("/toAdmin")
    public String admin() {
        return "admin";
    }

    //管理员主页
    @RequestMapping("/userAdminIndex")
    public String AdminIndex(){
        return "admin_index";
    }

    //用户主页
    @RequestMapping({"/","/toIndex"})
    public String index() {
        return "index";
    }

    //商品列表页面
    @RequestMapping("/toGoodList")
    public String goodsList() {
        return "goodslist";
    }

    //已上架商品列表页面
    @RequestMapping("/toGoodsUp")
    public String goodsUp() {
        return "goodsUp";
    }

    //已下架商品列表页面
    @RequestMapping("/toGoodsDown")
    public String goodsDown() {
        return "goodsDown";
    }

    //商品订单页面
    @RequestMapping("/toOrdersList")
    public String ordersList(HttpServletRequest request){
        HttpSession session = request.getSession();
        List<Ordergoods> ordersList = orderService.ordersList();
        if (ordersList.size()>0) {
            session.setAttribute("ordersList", ordersList);
        }
        return "ordersList";
    }

    //代发货订单页面
    @RequestMapping("/toOrdersWait")
    public String ordersWait(HttpServletRequest request){
        HttpSession session = request.getSession();
        List<Ordergoods> ordersWaitList = orderService.ordersWaitList();
//        if (ordersWaitList.size()>0){
//            session.setAttribute("ordersWaitList",ordersWaitList);
//        }
        session.setAttribute("ordersWaitList",ordersWaitList);
        return "ordersWait";
    }

    //已发货订单页面
    @RequestMapping("/toOrdersEnd")
    public String ordersEnd(HttpServletRequest request){
        HttpSession session = request.getSession();
        List<Ordergoods> ordersEndList = orderService.ordersEndList();
        if (ordersEndList.size()>0){
            session.setAttribute("ordersEndList",ordersEndList);
        }
        return "ordersEnd";
    }

    //商品类别列表页面
    @RequestMapping("/toGoodsTypeList")
    public String goodsTypeList() {
        return "goodsTypeList";
    }

    //增加商品类别列表页面
    @RequestMapping("/toAddGoodsType")
    public String addGoodsType() {
        return "addGoodsType";
    }

    //日历页面
    @RequestMapping("/toWelcome")
    public String welcome() {
        return "welcome";
    }

    //用户中心页面
    @RequestMapping("/toCustomer")
    public String customer() {
        return "customer";
    }

    //购物车页面
    @RequestMapping("/toShop")
    public String shop() {
        return "shop";
    }

    //订单页面
    @RequestMapping("/toOrder")
    public String order(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String uname = (String) session.getAttribute("username");
        if(uname!=null){
            User u = userService.findUserByUsername(uname);
            List<Ordergoods> orders = new ArrayList<Ordergoods>();
            List<Ordergoods> gcs = orderService.selectOrderByuserId(u.getId());
            for (Ordergoods gs : gcs) {
                orders.add(gs);
            }
            session.setAttribute("orders", orders);
            List<Ordergoods> ordersEndList = orderService.ordersEndListById(u.getId());
            if (ordersEndList.size()>0){
                session.setAttribute("ordersEndListById",ordersEndList);
            }
        }
        return "order";
    }

    //收获地址
    @RequestMapping("/toAddress")
    public ModelAndView address(String ids) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("address");
        mav.addObject("ids",ids);
        return mav;
    }

    //商品找不到页面
    @RequestMapping("/toNoPage")
    public String noPage(){
        return "404";
    }

    /**
     * 验证码的绘制
     */
    @RequestMapping("/checkCode")
    public void checkCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int width = 120;
        int height = 50;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, width, height);
        String checkCode = getCheckCode();
        request.getSession().setAttribute("CHECKCODE", checkCode);
        g.setColor(Color.green);
        g.setFont(new Font("黑体", Font.BOLD, 36));
        g.drawString(checkCode, 25, 35);
        ImageIO.write(image, "PNG", response.getOutputStream());
    }

    /**
     * 验证码的获取
     */
    private String getCheckCode() {
        String code = "0123456789ABCDEFGabcdefg";
        int size = code.length();
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 1; i <= 4; i++) {
            int index = r.nextInt(size);
            char c = code.charAt(index);
            sb.append(c);
        }
        return sb.toString();
    }
}
