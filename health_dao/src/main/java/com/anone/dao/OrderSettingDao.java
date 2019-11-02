package com.anone.dao;

import com.anone.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * dao 接口
 */
public interface OrderSettingDao {
    /**
     * 查询是否存在预约设置
     * @param orderDate
     * @return
     */
    int checkOrderSettingByOrderDate(Date orderDate);

    /**
     * 根据预约日期修改可预约人数
     * @param orderSetting
     */
    void editNumberByOrderDate(OrderSetting orderSetting);

    /**
     * 新增预约设置
     * @param orderSetting
     */
    void add(OrderSetting orderSetting);

    /**
     * 查询当年 当月 的预约设置
     * @param startDate
     * @param endDate
     * @return
     */
    List<OrderSetting> show(@Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 根据预约日期查询预约设置
     * @param orderDate
     * @return
     */
    OrderSetting findOrderSettingByOrderDate(String orderDate);

    /**
     * 根据预约日期修改预约人数
     * @param orderSetting
     */
    void editOrderSettingByOrderDate(OrderSetting orderSetting);

    /**
     * 清空预约设置
     * @param date
     */
    void clearOrderSetting(String date);
}
