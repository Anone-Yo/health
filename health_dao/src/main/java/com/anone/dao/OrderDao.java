package com.anone.dao;

import com.anone.pojo.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 预约 dao 接口
 */
public interface OrderDao {
    /**
     * 查询预约表中信息
     * @param order
     * @return
     */
    List<Order> findCondition(Order order);

    /**
     * 多表查询 获取orderinfo 信息
     * @param id
     * @return
     */
    Map<String, Object> findById(Integer id);

    /**
     * 新增order
     * @param order
     */
    void add(Order order);

    /**
     * 查询套餐预约的人数
     * @return
     */
    List<Map<String,Object>> countOrder();

//今日预约数
    Integer findTodayOrderNumber(String reportDate);
//今日到诊数
    Integer findTodayVisitsNumber(@Param("orderstatusYes")String orderstatusYes,@Param("reportDate") String reportDate);
//本周预约数
    Integer findThisWeekOrderNumber(String thisWeekMonday);
//本周到诊数
    Integer findThisWeekVisitsNumber(@Param("thisWeekMonday") String thisWeekMonday,@Param("orderstatusYes") String orderstatusYes);
//本月预约数
    Integer findThisMonthOrderNumber(String thisMonthFirstDay);
//本月到诊数
    Integer findThisMonthVisitsNumber(@Param("thisMonthFirstDay")String thisMonthFirstDay, @Param("orderstatusYes")String orderstatusYes);
//热门套餐
    List<Map> findHotSetmeal();
}
