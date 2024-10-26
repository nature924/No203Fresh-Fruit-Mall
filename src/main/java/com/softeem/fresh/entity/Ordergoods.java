package com.softeem.fresh.entity;

public class Ordergoods {
    private String id;

    private String userid;

    private String goodsid;

    private String goodscarid;

    private Integer dr;

    private String address;

    private Goods goods;

    private Goodscar goodscar;

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public Goodscar getGoodscar() {
        return goodscar;
    }

    public void setGoodscar(Goodscar goodscar) {
        this.goodscar = goodscar;
    }

    public String getGoodscarid() {
        return goodscarid;
    }

    public void setGoodscarid(String goodscarid) {
        this.goodscarid = goodscarid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid == null ? null : goodsid.trim();
    }

    public Integer getDr() {
        return dr;
    }

    public void setDr(Integer dr) {
        this.dr = dr;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }
}