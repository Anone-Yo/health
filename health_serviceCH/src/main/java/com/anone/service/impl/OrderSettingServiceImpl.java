package com.anone.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.anone.dao.OrderSettingDao;
import com.anone.pojo.OrderSetting;
import com.anone.service.OrderSettingService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 预约设置 服务 接口 实现
 */
@Service(interfaceClass = OrderSettingService.class)
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    /**
     * 新增预约设置
     */
    @Override
    @Transactional
    public void add(List<OrderSetting> list) {
    //判断list是否为空
        if (list !=null && list.size()>0) {
            for (OrderSetting orderSetting : list) {
                this.addOrEditOrderSetting(orderSetting);
            }
        }
    }

    /**
     *展示页面
     * @param date
     * @return
     */
    @Override
    @Transactional
    public List<Map> show(String date) {
        //定义起始日期 和末尾日期
        String startDate= date+"-01";
        String endDate=date+"-31";
        //查询当前页 当月的所有预约设置
      List<OrderSetting> list=orderSettingDao.show(startDate,endDate);
      //判断list 非空
        List<Map> ordList=new ArrayList<>();
        if (list !=null && list.size()>0) {
            for (OrderSetting orderSetting : list) {
                Map<String,Object> map=new HashMap<>();
                map.put("date",orderSetting.getOrderDate().getDate());
                map.put("number",orderSetting.getNumber());
                map.put("reservations",orderSetting.getReservations());
                ordList.add(map);
            }
        }
        return ordList;
    }

    /**
     * 根据预约日期修改可预约人数
     * @param orderSetting
     */
    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        this.addOrEditOrderSetting(orderSetting);
    }

    /**
     * 清空预约设置
     * @param date
     */
    @Override
    public void clearOrderSetting(String date) {
        orderSettingDao.clearOrderSetting(date);
    }

    /**
     * 抽取公共代码
     * 判断是否存在预约日期
     * 存在修改
     * 不存在新增
     */
    @Transactional
    public void addOrEditOrderSetting(OrderSetting orderSetting) {
        //先判断数据库中是否存在重复的预约设置-日期查询
        int count= orderSettingDao.checkOrderSettingByOrderDate(orderSetting.getOrderDate());
        //判断count是否大于0
        if (count > 0) {
            //存在 则修改预约设置
            orderSettingDao.editOrderSettingByOrderDate(orderSetting);
        } else {
            //不存在 则新增
            orderSettingDao.add(orderSetting);
        }
    }
}
