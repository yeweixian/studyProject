package com.danger.study.tools.common;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * Created by danger on 2016/6/17.
 */
public class StringUtils {

    public static boolean isEmpty(Object str) {
        return (str == null || "".equals(str));
    }

    public static boolean isNotEmpty(Object str) {
        return ! isEmpty(str);
    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public static List<Long> getIdListByIdsStr(String ids) {
        List<Long> idList = new ArrayList<>();
        String[] idArray = ids.split(",");
        for (String idStr : idArray) {
            if (StringUtils.isNotEmpty(idStr) && StringUtils.isNumeric(idStr)) {
                idList.add(Long.valueOf(idStr));
            }
        }
        return idList;
    }
}
