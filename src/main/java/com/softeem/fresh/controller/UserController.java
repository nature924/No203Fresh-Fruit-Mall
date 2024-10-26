package com.softeem.fresh.controller;

import com.softeem.fresh.entity.Goodscar;
import com.softeem.fresh.entity.Ordergoods;
import com.softeem.fresh.entity.User;
import com.softeem.fresh.service.GoodsCarService;
import com.softeem.fresh.service.OrderService;
import com.softeem.fresh.service.UserService;
import com.softeem.fresh.util.App;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.List;

/**
 * 用户和管理员控制层
 */
@Controller
public class UserController {
    @Resource(name = "userService")
    UserService userService;
    @Resource(name = "goodsCarService")
    GoodsCarService goodsCarService;
//    @Resource(name = "goodsService")
//    GoodsService goodsService;
    @Resource(name = "orderService")
    OrderService orderService;

    /**
     * 用户注册
     */
    @RequestMapping("/userRegister")
    @ResponseBody
    public Map<String, Object> doReg(String username, String password, String email, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        User u = userService.findUserByUsername(username);
        if (u != null) {
            map.put("status", 1);
            map.put("message", "注册失败,该账户名已存在");
        } else {
            String id = App.getUid();
            int dr = 0;
            User user = new User(id, username, password, dr, email);
            int result = userService.doReg(user);
            if (result == 1) {
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                session.setAttribute("email", email);
                map.put("status", 0);
                map.put("message", "注册成功");
            }
        }
        return map;
    }

    /**
     * 用户登录
     * checked 为1 代表记住用户名，为0 代表忘记用户名
     */
    @RequestMapping("/userLogin")
    @ResponseBody
    public Map<String, Object> doLogin(String username, String pwd, String checkcode, int checked, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE");
        session.removeAttribute("CHECKCODE");//为了保证验证码只能使用一次
        User u = userService.findUserByUsername(username);
        if (u == null || u.getDr() == 1) {
            map.put("status", 1);
            map.put("message", "登录失败,请检查信息是否正确");
        } else {
            if (u.getPassword().equals(pwd) && checkcode.equalsIgnoreCase(checkcode_server)) {
                String userid = u.getId();
                List<Goodscar> goodscars = goodsCarService.findGoodscarByUserId(userid);
                App.goodscarList = goodscars;
                session.setAttribute("goodscarList", App.goodscarList);
                session.setAttribute("count", App.goodscarList.size());
                session.setAttribute("username", username);
                session.setAttribute("email", u.getEmail());
                List<Ordergoods> orders = new ArrayList<Ordergoods>();
                List<Ordergoods> ordersEndList = orderService.ordersEndListById(userid);
                if (ordersEndList.size()>0){
                    session.setAttribute("ordersEndListById",ordersEndList);
                }
                List<Ordergoods> gcs = orderService.selectOrderByuserId(u.getId());
                for (Ordergoods gs : gcs) {
                    orders.add(gs);
                }
                if (orders.size() > 0) {
                    session.setAttribute("orders", orders);
                }
                if (checked == 1) {
                    App.USERNAME = username;
                } else if (checked == 0) {
                    App.USERNAME = null;
                }
                map.put("status", 0);
                map.put("message", "登录成功");
            } else {
                map.put("status", 1);
                map.put("message", "登录失败,请检查信息是否正确");
            }
        }
        return map;
    }

    /**
     * 管理员登录
     * checked 为 1 代表记住用户名和密码，为0 反之
     */
    @RequestMapping("/userAdminLogin")
    @ResponseBody
    public Map<String, Object> adminLogin(String username, String password, int checked, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Map<String, Object> map = new HashMap<String, Object>();
        User u = userService.findUserByUsername(username);
        if (u == null || u.getDr() == 0) {
            map.put("status", 1);
            map.put("message", "登录失败,请检查信息是否正确");
        } else {
            if (u.getPassword().equals(password)) {
                session.setAttribute("adminUsername", username);
                session.setAttribute("adminEmail", u.getEmail());
                if (checked == 1) {
                    App.ADMIN_USERNAME = username;
                    App.ADMIN_PASSWORD = password;
                } else if (checked == 0) {
                    App.ADMIN_USERNAME = null;
                    App.ADMIN_PASSWORD = null;
                }
                map.put("status", 0);
                map.put("message", "登录成功");
            } else {
                map.put("status", 1);
                map.put("message", "登录失败,请检查信息是否正确");
            }
        }
        return map;
    }

    /**
     * 管理员修改密码
     */
    @RequestMapping("/userAdminForgetPwd")
    @ResponseBody
    public Map<String, Object> adminForgetPwd(String username, String password, String email, String checkcode, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE");
        session.removeAttribute("CHECKCODE");//为了保证验证码只能使用一次
        User u = userService.findUserByUsername(username);
        if (u != null && checkcode.equalsIgnoreCase(checkcode_server) && email.equals(u.getEmail()) && u.getDr() == 1) {
            User user = new User(username, password);
            int result = userService.updatePassword(user);
            if (result == 1) {
                map.put("status", 0);
                map.put("message", "修改成功");
            } else {
                map.put("status", 1);
                map.put("message", "修改失败,请检查信息是否正确");
            }
        } else {
            map.put("status", 1);
            map.put("message", "修改失败,请检查信息是否正确");
        }
        return map;
    }

    /**
     * 用户修改密码
     */
    @RequestMapping("/userForgetPwd")
    @ResponseBody
    public Map<String, Object> forgetPwd(String username, String password, String email, String checkcode, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE");
        session.removeAttribute("CHECKCODE");//为了保证验证码只能使用一次
        User u = userService.findUserByUsername(username);
        if (u != null && checkcode.equalsIgnoreCase(checkcode_server) && email.equals(u.getEmail()) && u.getDr() == 0) {
            User user = new User(username, password);
            int result = userService.updatePassword(user);
            if (result == 1) {
                map.put("status", 0);
                map.put("message", "修改成功");
            } else {
                map.put("status", 1);
                map.put("message", "修改失败,请检查信息是否正确");
            }
        } else {
            map.put("status", 1);
            map.put("message", "修改失败,请检查信息是否正确");
        }
        return map;
    }

    /**
     * 用户退出
     */
    @RequestMapping("/userQuit")
    public String userQuit(HttpServletRequest request){
        HttpSession session = request.getSession();
        String auname = (String) session.getAttribute("adminUsername");
        if (auname == null){
            session.invalidate();
            App.goodscarList = null;
            App.ADDRESS = null;
            HttpSession session1 = request.getSession();
            session1.setAttribute("saveUsername", App.USERNAME);
        }else {
            session.removeAttribute("username");
            session.removeAttribute("saveUsername");
            session.removeAttribute("email");
            session.removeAttribute("goodsBrowse");
            session.removeAttribute("goodscarList");
            session.removeAttribute("count");
            session.removeAttribute("goodscarCount");
            session.removeAttribute("orders");
            session.removeAttribute("ordersEndListById");
            App.goodscarList = null;
            App.ADDRESS = null;
            session.setAttribute("saveUsername", App.USERNAME);
        }
        return "redirect:toIndex";
    }

    /**
     * 管理员退出
     */
    @RequestMapping("/userAdminQuit")
    public String userAdminQuit(HttpServletRequest request){
        HttpSession session = request.getSession();
//        session.invalidate();
        session.removeAttribute("adminUsername");
        session.removeAttribute("adminEmail");
        session.removeAttribute("saveadminUsername");
        session.removeAttribute("saveadminPassword");
        session.removeAttribute("ordersList");
        session.removeAttribute("ordersWaitList");
        session.removeAttribute("ordersEndList");
        session.setAttribute("saveadminUsername", App.ADMIN_USERNAME);
        session.setAttribute("saveadminPassword", App.ADMIN_PASSWORD);
        return "redirect:toAdminLogin";
    }
}
