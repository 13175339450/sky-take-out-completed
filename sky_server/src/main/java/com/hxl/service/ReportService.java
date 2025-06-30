package com.hxl.service;

import com.hxl.vo.OrderReportVO;
import com.hxl.vo.SalesTop10ReportVO;
import com.hxl.vo.TurnoverReportVO;
import com.hxl.vo.UserReportVO;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

public interface ReportService {
    /**
     * 营业额统计接口
     * @param begin 开始日期
     * @param end 结束日期
     * @return 返回该日期区间的日期字符串和营业额字符串
     */
    TurnoverReportVO turnoverStatistics(LocalDate begin, LocalDate end);

    /**
     * 用户统计接口
     * @param begin 开始时间
     * @param end 结束时间
     * @return 返回用户统计的相关数据
     */
    UserReportVO userStatistics(LocalDate begin, LocalDate end);

    /**
     * 订单统计接口
     * @param begin 开始时间
     * @param end 结束时间
     * @return 返回时间区间内的相关统计数据
     */
    OrderReportVO ordersStatistics(LocalDate begin, LocalDate end);

    /**
     * 查询销量排名top10
     * @param begin 开始时间
     * @param end 结束时间
     * @return 返回排名top10的数据
     */
    SalesTop10ReportVO top10(LocalDate begin, LocalDate end);

    /**
     * Excel报表接口
     */
    void export(HttpServletResponse response);
}
