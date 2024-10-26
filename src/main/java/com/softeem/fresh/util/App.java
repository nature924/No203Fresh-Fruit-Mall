package com.softeem.fresh.util;

import com.softeem.fresh.entity.Goodscar;

import java.util.List;
import java.util.UUID;

public class App {
    public static String ADMIN_USERNAME = null;
    public static String ADMIN_PASSWORD = null;
    public static String USERNAME = null;
    public static String ADDRESS = null;
    public static List<Goodscar> goodscarList = null;
    public static String getUid() {
        return UUID.randomUUID().toString();
    }
}
