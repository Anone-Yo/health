package com.anone.controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.aliyuncs.exceptions.ClientException;
import com.anone.constant.MessageConstant;
import com.anone.constant.RedisConstant;
import com.anone.entity.Result;
import com.anone.pojo.Order;
import com.anone.service.OrderService;
import com.anone.utils.SMSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * 预约 控制层
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Reference
    private OrderService orderService;
    @Autowired
    private JedisPool jedisPool;

    /**
     * 预约提交
     * 目的：往order表中插入一条记录
     */
    @PostMapping("/submit")
    public Result submit(@RequestBody Map map) {
        //判断验证码是否一致
        String telephone = (String) map.get("telephone");
        String validateCode = (String) map.get("validateCode");
        String orderDate=(String)map.get("orderDate");
        String code = jedisPool.getResource().get(telephone + RedisConstant.SENDTYPE_ORDER);
        Result result=null;
        if (!StringUtils.isEmpty(code) && !StringUtils.isEmpty(validateCode) && validateCode.equals(code)) {
            //调用业务处理---插入order表中，并返回一个order对象
            //插入预约类型
            map.put("orderType",Order.ORDERTYPE_WEIXIN);
            try {
                 result=  orderService.submit(map);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return new Result(false,MessageConstant.VALIDATECODE_ERROR);
        }
        //预约成功需要发送短信通知---预约日期
        if (result.isFlag()) {
            try {
                SMSUtils.sendShortMessage(SMSUtils.ORDER_NOTICE,telephone,orderDate);
            } catch (ClientException e) {
                e.printStackTrace();
            }
            return result;
        }
        String message=MessageConstant.ORDER_FAIL;
        if (result.getMessage() != null) {
            message=result.getMessage();
        }
        return new Result(false,message);
    }

    /**
     * 根据预约id 查询orderinfo  体检人的预约信息
     */
    @PostMapping("/findById")
    public Result findById(Integer id) {
        try {
            Map<String,Object> map=  orderService.findById(id);
            return  new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false,MessageConstant.QUERY_ORDER_FAIL);
        }

    }
}
