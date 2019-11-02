package com.anone.service;

import com.anone.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * 预约设置 服务 接口
 */
public interface OrderSettingService {

    /**
     * 新增预约设置
     */
    void add(List<OrderSetting> list);

    /**
     * 展示页面
     * @param date
     * @return
     */
    List<Map> show(String date);

    /**
     * 根据日期修改可预约人数
     * @param orderSetting
     */
    void editNumberByDate(OrderSetting orderSetting);

    /**
     * 清除预约设置
     * @param date
     */
    void clearOrderSetting(String date);
}
