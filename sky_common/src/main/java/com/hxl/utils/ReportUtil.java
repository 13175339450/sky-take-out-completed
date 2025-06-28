package com.hxl.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReportUtil {

    /**
     * 统计时间区间
     */
    public static List<LocalDate> statisticsTime(LocalDate begin, LocalDate end){
        List<LocalDate> dateList = new ArrayList<>();

        dateList.add(begin);
        while (!begin.equals(end)) {
            begin = begin.plusDays(1);
            dateList.add(begin);
        }
        return dateList;
    }


}
