package com.danger.study.tools.common;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by PC-361 on 2017/2/21.
 */
public class DateUtils {

    /**
     * 获取 传入日期(date) 前 最近的 周几 的日期对象
     * @param date (目标日期)
     * @param dayOfWeek 周几 (使用 calendar 类 的 常量 SUNDAY MONDAY TUESDAY WEDNESDAY THURSDAY FRIDAY SATURDAY)
     * @return 结果日期
     */
    public static Date getBeforeDayOfWeekByDate(Date date, int dayOfWeek) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayInWeek = c.get(Calendar.DAY_OF_WEEK);
        int beforeDay = (dayInWeek - dayOfWeek + 7) % 7;
        c.add(Calendar.DATE, - beforeDay);
        return c.getTime();
    }

    /**
     * 获取 传入日期(date) 后 最近的 周几 的 日期对象
     * @param date (目标日期)
     * @param dayOfWeek 周几 (使用 calendar 类 的 常量 SUNDAY MONDAY TUESDAY WEDNESDAY THURSDAY FRIDAY SATURDAY)
     * @return 结果日期
     */
    public static Date getAfterDayOfWeekByDate(Date date, int dayOfWeek) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayInWeek = c.get(Calendar.DAY_OF_WEEK);
        int afterDay = (dayOfWeek - dayInWeek + 7) % 7;
        c.add(Calendar.DATE, afterDay);
        return c.getTime();
    }

    /**
     * 以周一为一周开始
     * @return 这周一
     */
    public static Date getMondayInThisWeek() {
        return getBeforeDayOfWeekByDate(new Date(), Calendar.MONDAY);
    }

    /**
     * 以周日为一周结束
     * @return 这周日
     */
    public static Date getSundayInThisWeek() {
        return getAfterDayOfWeekByDate(new Date(), Calendar.SUNDAY);
    }

    public Map<String, Date> getThisWeek() {
        Map<String, Date> result = new HashMap<>();
        Date date = new Date();
        result.put("start", getBeforeDayOfWeekByDate(date, Calendar.MONDAY));
        result.put("end", getAfterDayOfWeekByDate(date, Calendar.SUNDAY));
        return result;
    }

}
