package com.softeem.fresh.entity;

import java.util.List;

public class PageBean<T> {
    //当前页
    private int currentage;
    //每页显示的记录数
    private int pageSize;
    //总记录数
    private int totalCount;
    //总页数
    private int totalPage;
    //每页显示的数据
    private List<T> lists;

    public PageBean() {
    }

    public PageBean(int currentage, int pageSize, int totalCount, int totalPage, List<T> lists) {
        this.currentage = currentage;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.totalPage = totalPage;
        this.lists = lists;
    }

    public int getCurrentage() {
        return currentage;
    }

    public void setCurrentage(int currentage) {
        this.currentage = currentage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getLists() {
        return lists;
    }

    public void setLists(List<T> lists) {
        this.lists = lists;
    }
}

