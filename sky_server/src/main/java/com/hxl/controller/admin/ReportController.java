package com.hxl.controller.admin;

import com.hxl.result.Result;
import com.hxl.service.ReportService;
import com.hxl.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

@RestController("adminReportController")
@RequestMapping("/admin/report")
@Slf4j
@Api(tags = "数据统计相关接口")
public class ReportController {

    @Autowired
    private ReportService reportService;


    /**
     * 营业额统计
     *
     * @param begin 前端传来的格式为 2022-05-01 格式
     * @param end   前端传来的格式为 2022-05-31 格式
     * @return 返回日期列表和营业额列表
     */
    @GetMapping("/turnoverStatistics")
    @ApiOperation("营业额统计接口")
    public Result<TurnoverReportVO> report(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end
    ) {
        log.info("营业额统计: {}, {}", begin, end);

        TurnoverReportVO vo = reportService.turnoverStatistics(begin, end);

        return Result.success(vo);
    }

    /**
     * 用户统计接口
     */
    @GetMapping("/userStatistics")
    @ApiOperation("用户统计接口")
    public Result<UserReportVO> userStatistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end
    ) {
        log.info("用户统计接口: {}, {}", begin, end);

        UserReportVO vo = reportService.userStatistics(begin, end);

        return Result.success(vo);
    }

    /**
     * 订单统计接口
     */
    @GetMapping("/ordersStatistics")
    @ApiOperation("订单统计接口")
    public Result<OrderReportVO> ordersStatistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end
    ) {
        log.info("订单统计接口: {}, {}", begin, end);

        OrderReportVO vo = reportService.ordersStatistics(begin, end);

        return Result.success(vo);
    }

    /**
     * 查询销量排名top10
     */
    @GetMapping("/top10")
    @ApiOperation("查询销量排名top10")
    public Result<SalesTop10ReportVO> top10(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end
    ) {
        log.info("查询销量排名top10: {}, {}", begin, end);

        SalesTop10ReportVO vo = reportService.top10(begin, end);

        return Result.success(vo);
    }

    /**
     * 导出Excel报表
     */
    @GetMapping("/export")
    @ApiOperation("导出Excel报表接口")
    public void export(HttpServletResponse response){
        log.info("导出Excel报表");

        reportService.export(response);
    }
}
