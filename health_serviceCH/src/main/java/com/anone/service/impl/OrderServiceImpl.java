package com.anone.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.anone.constant.MessageConstant;
import com.anone.dao.MemberDao;
import com.anone.dao.OrderDao;
import com.anone.dao.OrderSettingDao;
import com.anone.entity.Result;
import com.anone.pojo.Member;
import com.anone.pojo.Order;
import com.anone.pojo.OrderSetting;
import com.anone.service.OrderService;
import com.anone.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 预约 服务 接口 实现
 */
@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl  implements OrderService{
   @Autowired
   private OrderDao orderDao;
   @Autowired
   private OrderSettingDao orderSettingDao;
   @Autowired
   private MemberDao memberDao;




    /**
     * 根据预约表 id 查询 order info
     * 信息
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> findById(Integer id) {
      Map<String,Object> map=  orderDao.findById(id);
        return map;
    }

    /**
     * 提交预约--往预约表插入数据
     * @param map
     * @return
     */
    @Override
    public Result submit(Map map) throws Exception {
        String orderDate = (String) map.get("orderDate");//预约日期
        String telephone = (String) map.get("telephone");//电话
        Integer setmealId = Integer.parseInt((String)map.get("setmealId"));
        String idCard = (String) map.get("idCard");//身份证
        String sex = (String) map.get("sex");//性别
        String name = (String) map.get("name");
        String orderType = (String) map.get("orderType");
        //判断预约时间是否可以预约
        OrderSetting orderSetting = orderSettingDao.findOrderSettingByOrderDate(orderDate);
        if (orderSetting == null) {
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        } else {
            //可以预约--判断预约人数是否已满
            int number = orderSetting.getNumber();
            int reservations = orderSetting.getReservations();
            if (reservations>=number) {
                return new Result(false,MessageConstant.ORDER_FULL);
            }
            //由于order 表中需要memberid--需要判断是否为会员
            Member member = memberDao.findMemberByTelephone(telephone);
            if (member != null) {
                //是就获取id
                //再对当前用户进行判断是否重复预约
                //用户id、预约日期、套餐id
                Integer memberId = member.getId();
                Order order = new Order();
                order.setMemberId(memberId);
                order.setOrderDate(DateUtils.parseString2Date(orderDate));
                order.setSetmealId(setmealId);
                List<Order> orderList = orderDao.findCondition(order);
                if (orderList != null && orderList.size() > 0) {
                    return new Result(false,MessageConstant.HAS_ORDERED);
                }
            } else {
                //不是自动注册---新增一个用户
                member=new Member();
                member.setPhoneNumber(telephone);
                member.setRegTime(new Date());
                member.setIdCard(idCard);
                member.setSex(sex);
                member.setName(name);
                memberDao.add(member);
            }//往order表中插入记录
            Order order = new Order(member.getId(),DateUtils.parseString2Date(orderDate),orderType,Order.ORDERSTATUS_NO,setmealId);
            orderDao.add(order);
            //再修改预约设置中的预约人数+1
            orderSetting.setReservations(orderSetting.getReservations()+1);
            orderSettingDao.editOrderSettingByOrderDate(orderSetting);

            return new Result(true,MessageConstant.ORDER_SUCCESS,order);
        }

    }
}
