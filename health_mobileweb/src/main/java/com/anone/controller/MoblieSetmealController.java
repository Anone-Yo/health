package com.anone.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.anone.constant.MessageConstant;
import com.anone.constant.RedisConstant;
import com.anone.entity.Result;
import com.anone.pojo.Setmeal;
import com.anone.service.SetMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.List;

@RestController
@RequestMapping("/msetmeal")
public class MoblieSetmealController {

    @Reference
    private SetMealService setMealService;
    @Autowired
    private JedisPool jedisPool;

    /**
     * 查询所有套餐
     */
    @PostMapping("/getSetmeal")
    public Result getSetmeal() {
        try {
            String setmealListJson = jedisPool.getResource().hget(RedisConstant.SETMEAL_LIST,"setmealList");
            List<Setmeal> setmealList=null;
            if (StringUtils.isEmpty(setmealListJson)) {
                setmealList = setMealService.getSetmeal();
                setmealListJson = JSON.toJSON(setmealList).toString();
                jedisPool.getResource().hset(RedisConstant.SETMEAL_LIST,"setmealList", setmealListJson);
            } else {
                setmealList = JSON.parseArray(setmealListJson, Setmeal.class);
            }
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,setmealList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }

    /**
     * 根据id查询套餐+检查组+检查项
     */
    @PostMapping("/findById")
    public Result findById(Integer id) {
        try {
          String setmealJson=  jedisPool.getResource().hget(RedisConstant.SETMEAL,"setmeal");
            Setmeal setmeal=null;
            if (StringUtils.isEmpty(setmealJson)) {
                setmeal = setMealService.findById(id);
                setmealJson = JSON.toJSON(setmeal).toString();
                jedisPool.getResource().hset(RedisConstant.SETMEAL,"setmeal", setmealJson);
            } else {
                 setmeal = JSON.parseObject(setmealJson, Setmeal.class);
            }
            return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
}
