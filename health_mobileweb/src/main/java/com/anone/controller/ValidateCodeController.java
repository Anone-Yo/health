package com.anone.controller;

import com.aliyuncs.exceptions.ClientException;
import com.anone.constant.MessageConstant;
import com.anone.constant.RedisConstant;
import com.anone.entity.Result;
import com.anone.utils.SMSUtils;
import com.anone.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

/**
 * 这是发送短信验证的Controller层
 */
@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {

    @Autowired
    private JedisPool jedisPool;
    /**
     * 发送验证码
     * 使用阿里云
     */
    @PostMapping("/send4Order")
    public Result send4Order(String telephone) {
       //生成验证码
        Integer code = ValidateCodeUtils.generateValidateCode(4);
        //发送短信验证码
        try {
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,code.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        System.out.println("验证码"+code);
        //将验证码存入redis中 5分钟 5*60
        jedisPool.getResource().setex(telephone+ RedisConstant.SENDTYPE_ORDER,5*60,code.toString());
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);

    }

    /**
     * 登录验证码
     */
    @PostMapping("/send4Login")
    public Result send4Login(String telephone) {
        Integer code = ValidateCodeUtils.generateValidateCode(4);
        try {
                SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,code.toString());
        } catch (ClientException e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        System.out.println(code);
        jedisPool.getResource().setex(telephone+RedisConstant.SENDTYPE_LOGIN,5*60,code.toString());
        return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);

    }
}
