package com.compete.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 封装日期工具类
 */
@Component
public class DateUtil {

    private static final Logger log = LoggerFactory.getLogger(DateUtil.class);

    /**
     * 获取当前日期时间
     *
     * @return java格式当前日期时间
     */
    public Date getNow() {
        return Calendar.getInstance().getTime();
    }

    /**
     * 获取当前日期时间
     *
     * @return 格式化(yyyy - MM - dd HH : mm : ss)后当前日期时间
     */
    public String getFormatNow() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DATE) + " "
                + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND);
    }

    /**
     * 返回本月的最后一天
     *
     * @return 本月最后一天的日期
     */
    public Date getMonthLastDay() {
        return getMonthLastDay(Calendar.getInstance().getTime());
    }

    /**
     * 返回给定日期中的月份中的最后一天
     *
     * @param date 基准日期
     * @return 该月最后一天的日期
     */
    public Date getMonthLastDay(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // 将日期设置为下一月第一天
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, 1);

        // 减去1天，得到的即本月的最后一天
        calendar.add(Calendar.DATE, -1);

        return calendar.getTime();
    }

    /**
     * 根据日期确定星期几:1-星期日，2-星期一.....s
     */
    public int getDayOfWeek(Date date) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        int mydate = cd.get(Calendar.DAY_OF_WEEK);
        return mydate;
    }

    /**
     * 获取系统当前日期，日期格式：yyyyMMdd
     */
    public String getCurrentDate() {
        return getCurrentDateFromPattern("yyyyMMdd");
    }

    /**
     * 获取系统当前日期时间，时间格式：yyyyMMdd HH:mm:ss
     */
    public String getCurrentTime() {
        return getCurrentDateFromPattern("yyyyMMdd HH:mm:ss");
    }

    /**
     * 获取系统当前日期时间，时间格式：yyyyMMdd HH:mm:ss
     */
    public String getCurrentTime(String format) {
        return getCurrentDateFromPattern(format);
    }

    /**
     * 获取系统当前时间，时间格式：HH:mm:ss
     */
    public String getOnlyCurrentTime() {
        return getCurrentDateFromPattern("HH:mm:ss");
    }

    /**
     * 功能描述:获取当前日期(根据pattern对应显示时间或日期等)
     */
    public String getCurrentDateFromPattern(String pattern) {
        Calendar cal = Calendar.getInstance();
        Date getdate = cal.getTime();
        SimpleDateFormat df = new SimpleDateFormat(pattern == null || "".equals(pattern) ? "yyyyMMdd" : pattern);
        return df.format(getdate);
    }

    /**
     * 获取两个字符串型pattern为yyyyMMdd的日期之间相隔的天数
     */
    public Integer getDaysDifferString(String dateStart, String dateEnd) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = sdf.parse(dateStart);
            date2 = sdf.parse(dateEnd);
        } catch (ParseException e) {
            log.error("Tools.getDaysOfString格式化日期异常", e);
            return null;
        }
        log.info(String.format("转化后日期：起始日期为%s,结束日期为%s", sdf.format(date1), sdf.format(date2)));
        return getDaysBetweenTime(date1, date2);
    }

    /**
     * 获取两个日期型日期之间相差的天数
     */
    public int getDaysBetweenTime(Date dateStart, Date dateEnd) {
        return (int) ((dateEnd.getTime() - dateStart.getTime()) / 1000 / 60 / 60 / 24);
    }

    /**
     * 获取两个日期型日期之间相隔的天数
     */
    public int getDaysDifferDate(Date dateStart, Date dateEnd) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return getDaysDifferString(sdf.format(dateStart), sdf.format(dateEnd));
    }

    /**
     * 得到当前年份
     */
    public int getCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);

    }

    /**
     * 得到当前月份
     */
    public int getCurrentMonth() {
        // 用get得到的月份数比实际的小1，需要加上
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    /**
     * 得到当前日
     */
    public int getCurrentDay() {
        return Calendar.getInstance().get(Calendar.DATE);
    }

    /**
     * 获取两个字符串型pattern为yyyyMMdd的日期之间相差的天数
     */
    public int getDaysOfString(String littleDate, String bigDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date1 = sdf.parse(littleDate);
        Date date2 = sdf.parse(bigDate);
        return getDaysOfDate(date1, date2);
    }

    /**
     * 获取两个日期型日期之间相差的天数
     */
    public int getDaysOfDate(Date littleDate, Date bigDate) {
        Calendar littleCal = Calendar.getInstance();
        littleCal.setTime(littleDate);

        Calendar bigCal = Calendar.getInstance();
        bigCal.setTime(bigDate);

        int littleDay = littleCal.get(Calendar.DAY_OF_YEAR);
        int bigDay = bigCal.get(Calendar.DAY_OF_YEAR);

        int littleYear = littleCal.get(Calendar.YEAR);
        int bigYear = bigCal.get(Calendar.YEAR);
        if (littleYear != bigYear) { // 不同年
            int timeDistance = 0;
            for (int i = littleYear; i < bigYear; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) { // 闰年
                    timeDistance += 366;
                } else { // 不是闰年
                    timeDistance += 365;
                }
            }
            int diffDay = timeDistance + (bigDay - littleDay);
            log.info(String.format("timeDistance(%s) + bigDay(%s) - littleDay(%s) : %s", timeDistance, bigDay,
                    littleDay, diffDay));
            return diffDay;
        } else { // 同年
            int diffDay = bigDay - littleDay;
            log.info(String.format("bigDay(%s) - littleDay(%s) : %s", bigDay, littleDay, diffDay));
            return diffDay;
        }
    }

    /**
     * 取得当前系统时间
     */
    public String getNowTime() {
        Calendar calendar = Calendar.getInstance();
        return String.valueOf(Time.valueOf(String.valueOf(calendar.get(11)) + ":" + String.valueOf(calendar.get(12))
                + ":" + String.valueOf(calendar.get(13))));
    }

    /**
     * 获取相应日期
     */
    public String getDayToToday(int inum) {
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_YEAR);
        cal.set(Calendar.DAY_OF_YEAR, day + inum);
        Date getdate = cal.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        return df.format(getdate);
    }

    /**
     * 获取格式化当前时间"yyyyMMdd HH:mm:ss"
     */
    public String getCurrentTimeByFormat(String format) {
        Date date = new Date();
        if ("".equals(format) || format == null) {
            format = "yyyyMMdd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 某月第一天
     */
    public String getFirstday(String month) throws ParseException {
        String firstday = "";
        SimpleDateFormat sourceDf = new SimpleDateFormat("yyyy-MM");
        Date date = sourceDf.parse(month);
        SimpleDateFormat targetDf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        firstday = targetDf.format(calendar.getTime());
        log.info("获取" + month + "月第一天结果为:" + firstday);
        return firstday;
    }

    /**
     * 获取参数日期前一个月返回六位
     */
    public String getPreMonth(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        // 将字符串的日期转为Date类型，ParsePosition(0)表示从第一个字符开始解析
        Date date = sdf.parse(dateStr, new ParsePosition(0));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        Date rtnDate = calendar.getTime();
        SimpleDateFormat rtnFormat = new SimpleDateFormat("yyyyMM");
        String rtnDateStr = rtnFormat.format(rtnDate);
        return rtnDateStr;
    }

    /**
     * 某月最后一天
     */
    public String getLastday(String month) throws ParseException {
        String firstday = "";
        SimpleDateFormat sourceDf = new SimpleDateFormat("yyyy-MM");
        Date date = sourceDf.parse(month);
        SimpleDateFormat targetDf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DATE, -1);
        firstday = targetDf.format(calendar.getTime());
        log.info("获取" + month + "月最后一天结果为:" + firstday);
        return firstday;
    }

    /**
     * 某月最后一天28、29、30、31
     */
    public String getLastdate(String month) throws ParseException {
        String handledDate = getLastday(month);
        String temp[] = handledDate.split("\\-");
        return temp[2];
    }

    public Object getRunTime(int mins) {
        String runTime = "0mins";
        if (mins > 0) {
            int days = mins / 1440;
            int hours = mins / 60 - days * 24;
            int realMins = mins - days * 1440 - hours * 60;
            if (days > 0) {
                runTime = String.format("%sdays %shours %smins", days, hours, realMins);
            } else if (hours > 0) {
                runTime = String.format("%shours %smins", hours, realMins);
            } else {
                runTime = mins + "mins";
            }
        }
        return runTime;
    }

    /**
     * 获取相应日期
     */
    public String getDayToStr(String str, int inum) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        // 将字符串的日期转为Date类型，ParsePosition(0)表示从第一个字符开始解析
        Date date = sdf.parse(str, new ParsePosition(0));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // add方法中的第二个参数n中，正数表示该日期后n天，负数表示该日期的前n天
        calendar.add(Calendar.DATE, inum);
        Date date1 = calendar.getTime();
        return sdf.format(date1);
    }

    /**
     * 获取相应日期
     */
    public String getDayToStr(String str, int inum, String dateType) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdfResult = new SimpleDateFormat(dateType);
        // 将字符串的日期转为Date类型，ParsePosition(0)表示从第一个字符开始解析
        Date date = sdf.parse(str, new ParsePosition(0));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // add方法中的第二个参数n中，正数表示该日期后n天，负数表示该日期的前n天
        calendar.add(Calendar.DATE, inum);
        Date date1 = calendar.getTime();
        return sdfResult.format(date1);
    }

    /**
     * 判断参数日期是否月末
     */
    public boolean isLstDayOfMonth(String judgeDay) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = sdf.parse(judgeDay);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, (calendar.get(Calendar.DATE) + 1));
        if (calendar.get(Calendar.DAY_OF_MONTH) == 1) {
            return true;
        }
        return false;
    }

    /**
     * 获取参数日期上月末日期
     */
    public String getPreMonthLastDay(String dateParm) throws ParseException {
        String firstDayParm = getPreMonth(dateParm);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        Date date = sdf.parse(firstDayParm);
        SimpleDateFormat rtnSdf = new SimpleDateFormat("yyyyMMdd");
        return rtnSdf.format(getMonthLastDay(date));
    }

    /**
     * 获取两个日期之间的毫秒差
     */
    public long getMills(String beginTimeStr, String endTimeStr) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        long diff = 0;
        try {
            long endTime = df.parse(endTimeStr).getTime();
            //从对象中拿到时间
            long beginTime = df.parse(beginTimeStr).getTime();
            diff = (endTime - beginTime) + 1;
        } catch (ParseException e) {
            log.error("获取两个日期之间的毫秒差执行异常", e);
        }
        return diff;
    }
}
