package com.hxl.service.impl;

import com.hxl.entity.Orders;
import com.hxl.mapper.OrderMapper;
import com.hxl.mapper.UserMapper;
import com.hxl.service.ReportService;
import com.hxl.service.WorkspaceService;
import com.hxl.utils.ReportUtil;
import com.hxl.vo.*;
import io.swagger.models.auth.In;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private WorkspaceService workspaceService;

    /**
     * 营业额统计
     */
    @Override
    public TurnoverReportVO turnoverStatistics(LocalDate begin, LocalDate end) {
        //统计区间内的时间 自定义工具类
        List<LocalDate> dateList = ReportUtil.statisticsTime(begin, end);

        List<BigDecimal> turnoverList = new ArrayList<>();
        //根据区间 分别查询对应的营业额
        for (LocalDate date : dateList) {
            //由于数据库里的date格式要求 我们需要LocalDateTime格式的时间
            LocalDateTime beginning = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime ending = LocalDateTime.of(date, LocalTime.MAX);

            //封装查询数据库所需要的参数
            Map map = new HashMap<>();
            map.put("begin", beginning);
            map.put("end", ending);
            map.put("status", Orders.COMPLETED);//已完成状态

            //去数据库查询这个时间区间的营业额 当天已完成的订单
            BigDecimal amount = orderMapper.turnoverStatistics(map);
            amount = amount == null ? BigDecimal.valueOf(0) : amount;//非空检查

            //存取
            turnoverList.add(amount);
        }

        //返回数据
        return new TurnoverReportVO(
                StringUtils.join(dateList, ','),
                StringUtils.join(turnoverList, ',')
        );
    }

    /**
     * 用户统计接口
     */
    @Override
    public UserReportVO userStatistics(LocalDate begin, LocalDate end) {
        //统计时间区间内的时间
        List<LocalDate> dateList = ReportUtil.statisticsTime(begin, end);

        //统计每个时间区间内新增的用户数量 和 截至当前时间用户总数
        List<Integer> newUserList = new ArrayList<>();
        List<Integer> totalUserList = new ArrayList<>();

        for (LocalDate date : dateList) {
            //时间格式转换
            LocalDateTime beginning = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime ending = LocalDateTime.of(date, LocalTime.MAX);

            //封装 新增用户数查询的条件数据
            Map map = new HashMap<>();
            map.put("begin", beginning);
            map.put("end", ending);

            Integer userNumber = userMapper.userStatistics(map);
            userNumber = userNumber == null ? 0 : userNumber;//非空判断
            newUserList.add(userNumber);

            //封装 截至当前时间的用户总数的条件数据
            map.put("begin", null);
            Integer userTotal = userMapper.userStatistics(map);
            userTotal = userTotal == null ? 0 : userTotal;
            totalUserList.add(userTotal);
        }

        //结果返回
        return new UserReportVO(
                StringUtils.join(dateList, ','),
                StringUtils.join(newUserList, ','),
                StringUtils.join(totalUserList, ',')
        );
    }

    /**
     * 订单统计接口
     */
    @Override
    public OrderReportVO ordersStatistics(LocalDate begin, LocalDate end) {
        //获取时间区间
        List<LocalDate> dateList = ReportUtil.statisticsTime(begin, end);

        //相关结果数据准备
        List<Integer> orderCountList = new ArrayList<>();//订单数列表
        List<Integer> validOrderCountList = new ArrayList<>();//有效订单数列表
        //数据查询
        for (LocalDate date : dateList) {
            //时间转换
            LocalDateTime beginning = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime ending = LocalDateTime.of(date, LocalTime.MAX);

            //查询结果封装
            Map map = new HashMap<>();
            map.put("begin", beginning);
            map.put("end", ending);

            //查询当天的订单总数
            Integer totalOrder = orderMapper.getOrderCount(map);
            orderCountList.add(totalOrder);

            //查询当天的有效订单数
            map.put("status", Orders.COMPLETED);//已完成的订单属于有效订单
            Integer validOrder = orderMapper.getOrderCount(map);
            validOrderCountList.add(validOrder);
        }

        //利用stream流 累加全部的订单数
        Integer totalOrderCount = orderCountList.stream().reduce(Integer::sum).get();//获取订单总数
        Integer validOrderCount = validOrderCountList.stream().reduce(Integer::sum).get();//有效订单数

        Double orderCompletionRate = 0.0;//订单完成率
        if (!totalOrderCount.equals(0)) {
            orderCompletionRate = validOrderCount * 1.0 / totalOrderCount;
        }

        return new OrderReportVO(
                StringUtils.join(dateList, ','),
                orderCompletionRate,
                StringUtils.join(orderCountList, ','),
                totalOrderCount,
                validOrderCount,
                StringUtils.join(validOrderCountList, ',')
        );
    }

    /**
     * 查询销量排名top10
     */
    @Override
    public SalesTop10ReportVO top10(LocalDate begin, LocalDate end) {
        //时间格式转换
        LocalDateTime beginning = LocalDateTime.of(begin, LocalTime.MIN);
        LocalDateTime ending = LocalDateTime.of(end, LocalTime.MAX);

        //封装数据
        Map map = new HashMap();
        map.put("begin", beginning);
        map.put("end", ending);
        map.put("status", Orders.COMPLETED);//已完成的商品

        //查询这个区间内的商品的name和数量 分组查询
        List<SalesTop10> salesTop10List = orderMapper.top10(map);

        //获取 商品名称列表
        List<String> names = salesTop10List.stream().map(SalesTop10::getName).collect(Collectors.toList());
        //销量列表
        List<Integer> numbers = salesTop10List.stream().map(SalesTop10::getNumber).collect(Collectors.toList());

        //将列表转换为字符串
        String nameList = StringUtils.join(names, ',');
        String numberList = StringUtils.join(numbers, ',');

        //结果返回
        return new SalesTop10ReportVO(nameList, numberList);
    }

    /**
     * 导出excel报表接口
     */
    @Override
    public void export(HttpServletResponse response) {
        //获取最近30天的营业数据
        LocalDate begin = LocalDate.now().minusDays(30);//减30天时间
        LocalDate end = LocalDate.now().minusDays(1);//减去1天时间

        LocalDateTime beginning = LocalDateTime.of(begin, LocalTime.MIN);
        LocalDateTime ending = LocalDateTime.of(end, LocalTime.MAX);

        //查询 数据统计的相关信息 调用 Service层的接口函数
        BusinessDataVO businessDataVO = workspaceService.businessData(beginning, ending);

        //将数据通过poi写入到Excel文件里
        //获取当前类路径下的Excel文件 的输入流
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("excel/运营数据报表模板.xlsx");


        try {
            //基于模板文件创建一个新的Excel文件
            XSSFWorkbook excel = new XSSFWorkbook(in);
            //获取表格文件的Sheet文件
            XSSFSheet sheet = excel.getSheet("Sheet1");
            //填充数据--时间
            sheet.getRow(1).getCell(1).setCellValue("时间：" + begin + "至" + end);
            //获得第4行
            XSSFRow row = sheet.getRow(3);
            row.getCell(2).setCellValue(businessDataVO.getTurnover().toString()); //第3个单元格
            row.getCell(4).setCellValue(businessDataVO.getOrderCompletionRate());
            row.getCell(6).setCellValue(businessDataVO.getNewUsers());
            //获得第5行
            row = sheet.getRow(4);
            row.getCell(2).setCellValue(businessDataVO.getValidOrderCount());
            row.getCell(4).setCellValue(businessDataVO.getUnitPrice());
            //填充明细数据
            for (int i = 0; i < 30; i++) {
                LocalDate date = begin.plusDays(i);
                //查询某一天的营业数据
                businessDataVO = workspaceService.businessData(LocalDateTime.of(date, LocalTime.MIN), LocalDateTime.of(date, LocalTime.MAX));
                //获得某一行
                row = sheet.getRow(7 + i);
                row.getCell(1).setCellValue(date.toString());
                row.getCell(2).setCellValue(businessDataVO.getTurnover().toString());
                row.getCell(3).setCellValue(businessDataVO.getValidOrderCount());
                row.getCell(4).setCellValue(businessDataVO.getOrderCompletionRate());
                row.getCell(5).setCellValue(businessDataVO.getUnitPrice());
                row.getCell(6).setCellValue(businessDataVO.getNewUsers());
            }
            //3.通过输出流将Excel文件下载到客户端浏览器
            ServletOutputStream out = response.getOutputStream();
            excel.write(out);
            //关闭资源
            out.close();
            excel.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
