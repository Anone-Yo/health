package com.anone.jobs;


import com.alibaba.dubbo.config.annotation.Reference;
import com.anone.dao.OrderSettingDao;
import com.anone.service.OrderSettingService;
import com.anone.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class ClearOrderSettingJob {

    @Autowired
    private OrderSettingDao orderSettingDao;

    public void clearOrderSetting() {
        //每月月底凌晨2点执行
        try {
            //获取当前日期
           /* String date = DateUtils.parseDate2String(DateUtils.getToday());*/
            orderSettingDao.clearOrderSetting("2019-08-11");
            System.out.println("执行了吗");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
