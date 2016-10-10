package com.danger.study.tools.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by PC-361 on 2016/8/25.
 */
public class Pager<D> implements Serializable {

    private List<D> list;
    private long total;
    private int page;
    private int pageSize;

    public Pager() {
    }

    public Pager(int page, int pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }

    public int getSize() {
        if (list == null) return 0;
        return list.size();
    }

    public List<D> getList() {
        return list;
    }

    public void setList(List<D> list) {
        this.list = list;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPages() {
        if (pageSize == 0) return 1;
        return (int) ((total + pageSize - 1) / pageSize);
    }
}
