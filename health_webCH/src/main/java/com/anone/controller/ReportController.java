package com.anone.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.anone.constant.MessageConstant;
import com.anone.entity.Result;
import com.anone.service.MemberService;
import com.anone.service.SetMealService;
import com.anone.utils.DateUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 这是报表 Controller
 */
@RestController
@RequestMapping("/report")
public class ReportController {

    @Reference
    private MemberService memberService;
    @Reference
    private SetMealService setMealService;

    /**
     * 统计会员数量
     *12个月的周期
     * @return
     */
    @GetMapping("/getMemberReport")
    @PreAuthorize("hasAuthority('REPORT_VIEW')")
    public Result getMemberReport() {
        //获取当前日期的前12个月
        Calendar calendar = Calendar.getInstance();
        //这里是月份往前减12个月
        calendar.add(Calendar.MONTH,-12);
        //存放月份的集合
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            String date = null;
            try {
                date = DateUtils.parseDate2String(calendar.getTime(),"yyyy-MM");
                list.add(date);
                //这是月份往前+1
                calendar.add(Calendar.MONTH,+1);
            } catch (Exception e) {
                e.printStackTrace();
                return new Result(false, MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL);
            }
        }
        Map<String,Object> map=new HashMap<>();
        //将月份封装到map中
        map.put("months",list);
        //获取每个月的会员数量
        try {
            List<Integer>  memberCountList =  memberService.getMemberNumberByMonth(list);
            map.put("memberCount",memberCountList);
            return new Result(true,MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL);
        }
    }

    /**
     * 统计各套餐预约人数占比
     * setmealNames
     * setmealCount
     */
    @GetMapping("/getSetmealReport")
    @PreAuthorize("hasAuthority('REPORT_VIEW')")
    public Result getSetmealReport() {
        try {
           Map map=  setMealService.getSetmealReport();
            return new Result(true,MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_SETMEAL_COUNT_REPORT_FAIL);
        }
    }

    /**
     * 获取会员统计表格
     */
    @GetMapping("/getBusinessReportData")
    @PreAuthorize("hasAuthority('REPORT_VIEW')")
    public Result getBusinessReportData() {
        //查询页面的对象 跟页面的属性一致
        try {
            Map map=memberService.getBusinessReportData();
            return new Result(true,MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL);
        }

    }

    /**
     * 导出表格
     */
    @GetMapping("/exportBusinessReport")
    public Result exportBusinessReport(HttpServletResponse response, HttpServletRequest request) {
     //获取表中的数据
        Map result = memberService.getBusinessReportData();
      /*  System.out.println(result);*/
        String reportDate = (String) result.get("reportDate");
        Integer todayNewMember = (Integer) result.get("todayNewMember");
        Integer totalMember = (Integer) result.get("totalMember");
        Integer thisWeekNewMember = (Integer) result.get("thisWeekNewMember");
        Integer thisMonthNewMember = (Integer) result.get("thisMonthNewMember");
        Integer todayOrderNumber = (Integer) result.get("todayOrderNumber");
        Integer thisWeekOrderNumber = (Integer) result.get("thisWeekOrderNumber");
        Integer thisMonthOrderNumber = (Integer) result.get("thisMonthOrderNumber");
        Integer todayVisitsNumber = (Integer) result.get("todayVisitsNumber");
        Integer thisWeekVisitsNumber = (Integer) result.get("thisWeekVisitsNumber");
        Integer thisMonthVisitsNumber = (Integer) result.get("thisMonthVisitsNumber");
        List<Map> hotSetmeal = (List<Map>) result.get("hotSetmeal");
        //获取模板文件的路径
       String template = request.getSession().getServletContext().getRealPath("template") + File.separator + "report_template.xlsx";
       // String template="../template/report_template.xlsx";
        //加载文件
        XSSFRow row=null;
        try {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new File(template));
            //记住行的索引从0开始 列是从0开
            //获取第一种sheet表
            XSSFSheet sheetAt = xssfWorkbook.getSheetAt(0);
            //第3行
            row = sheetAt.getRow(2);
            row.getCell(5).setCellValue(reportDate);
            //第五行
            row = sheetAt.getRow(4);
            row.getCell(5).setCellValue(todayNewMember);
            row.getCell(7).setCellValue(totalMember);
            //第6行
            row = sheetAt.getRow(5);
            row.getCell(5).setCellValue(thisWeekNewMember);
            row.getCell(7).setCellValue(thisMonthNewMember);
            //第8行
            row = sheetAt.getRow(7);
            row.getCell(5).setCellValue(todayOrderNumber);
            row.getCell(7).setCellValue(thisWeekOrderNumber);
            //第9行
            row = sheetAt.getRow(8);
            row.getCell(5).setCellValue(thisMonthOrderNumber);
            row.getCell(7).setCellValue(todayVisitsNumber);
            //第10行
            row = sheetAt.getRow(9);
            row.getCell(5).setCellValue(thisWeekVisitsNumber);
            row.getCell(7).setCellValue(thisMonthVisitsNumber);
            //第12行之后
            //定义行数
            Integer rowNumber=12;
            if (hotSetmeal!=null &&hotSetmeal.size()>0) {
                    for (Map map : hotSetmeal) {
                        String name = (String) map.get("name");
                        String setmeal_count = (String) map.get("setmeal_count").toString();
                        String proportion = (String) map.get("proportion").toString();
                        String remark = (String) map.get("remark");
                        row= sheetAt.getRow(rowNumber++);
                        row.getCell(4).setCellValue(name);
                        row.getCell(5).setCellValue(setmeal_count);
                        row.getCell(6).setCellValue(proportion);
                        row.getCell(7).setCellValue(remark);
                    }
                //输出
                ServletOutputStream outputStream = response.getOutputStream();
                //下载文件设置响应头信息
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("content-Disposition", "attachment;filename=report.xlsx");
                xssfWorkbook.write(outputStream);
                //刷新流
                outputStream.flush();
                //先关闭流
                outputStream.close();
                //再关闭xssfworkbook对象 excel对象
                xssfWorkbook.close();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取会员男女占比
     */
    @GetMapping("/getMemberGenderReport")
    public Result getMemberGenderReport() {
        try {
            Map map= memberService.getMemberGenderReport();
            return new Result(true,MessageConstant.GENDER_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.GENDER_FAIL);
        }
    }

    /**
     * 获取会员年龄段占比
     */
    @GetMapping("/getMemberAgeReport")
    public Result getMemberAgeReport() {
        try {
            Map  map= memberService.getMemberAgeReport();
            return new Result(true,MessageConstant.AGE_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.AGE_FAIL);
        }

    }

    /**
     * 展示时间段 会员数量的折现图
     */
    @GetMapping("/showByDate")
    public Result showByDate(String beginDate, String endDate) {
        try {
            Map rsMap=  memberService.showByDate(beginDate,endDate);
            return new Result(true,MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,rsMap);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL);
        }
    }
}
