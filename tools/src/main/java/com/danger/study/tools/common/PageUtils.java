package com.danger.study.tools.common;

import com.danger.study.tools.model.Pager;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页工具类
 * Created by PC-361 on 2016/7/23.
 */
public class PageUtils {

    public static <T> List<List<T>> splitList(List<T> list, int pageSize) {
        int total = list.size();
        int pages = (total + pageSize - 1) / pageSize;
        List<List<T>> pageList = new ArrayList<>();
        for (int i = 0; i < pages; i ++) {
            pageList.add(new ArrayList<>());
        }
        for (int i = 0; i < total; i ++) {
            int index = i / pageSize;
            List<T> subList = pageList.get(index);
            subList.add(list.get(i));
        }
        return pageList;
    }

    public static <T> List<T> getList(List<T> list, int page, int pageSize) {
        int total = list.size();
        int start = page * pageSize;
        int end = start + pageSize;
        end = Math.min(total, end);
        List<T> l = new ArrayList<>();
        for (int i = start; i < end; i ++) {
            l.add(list.get(i));
        }
        return l;
    }

    public static <T> Pager<T> getPage(List<T> list, int page, int pageSize) {
        Pager<T> p = new Pager<>(page, pageSize);
        p.setTotal(list.size());
        p.setList(getList(list, page, pageSize));
        return p;
    }
}
