package com.anone.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.anone.constant.MessageConstant;
import com.anone.entity.Result;
import com.anone.pojo.OrderSetting;
import com.anone.service.OrderSettingService;
import com.anone.utils.POIUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

    @Reference
    private OrderSettingService orderSettingService;

    /**
     * 上传文件 并保存到Db
     */
   @PostMapping("/upload")
   @PreAuthorize("hasAuthority('ORDERSETTING')")
    public Result upload(MultipartFile excelFile){
       try {
           //解析Excel文件
           List<String[]> strings = POIUtils.readExcel(excelFile);
           List<OrderSetting> list=new ArrayList<>();
           //遍历集合 将数组中的数据封装到ordersettinge
           for (String[] row : strings) {
               String date = row[0];
               String number = row[1];
               OrderSetting orderSetting = new OrderSetting();
               orderSetting.setOrderDate(new Date(date));
               orderSetting.setNumber(Integer.parseInt(number));
               //新增
             list.add(orderSetting);
           }
           orderSettingService.add(list);
       } catch (Exception e) {
           e.printStackTrace();
           return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
       }
       return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
   }

    /**
     * 展业页面
     */
    @GetMapping("/show")
    public Result show(String date) {
        try {
            List<Map> list=  orderSettingService.show(date);
            return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_ORDERSETTING_FAIL);

        }

    }

    /**
     * 设置单个预约
     */
    @PostMapping("/editNumberByDate")
    @PreAuthorize("hasAuthority('ORDERSETTING')")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting) {
        try {
            orderSettingService.editNumberByDate(orderSetting);
            return new Result(true,MessageConstant.IMPORT_ORDERSETTINGONE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.IMPORT_ORDERSETTINGONE_FAIL);
        }
    }
}
