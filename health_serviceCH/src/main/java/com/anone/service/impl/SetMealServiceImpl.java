package com.anone.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.anone.constant.MessageConstant;
import com.anone.constant.RedisConstant;
import com.anone.dao.OrderDao;
import com.anone.dao.SetMealDao;
import com.anone.entity.PageResult;
import com.anone.pojo.Setmeal;
import com.anone.service.SetMealService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 这是 套餐 服务 的 实现
 */
@Service(interfaceClass = SetMealService.class)
public class SetMealServiceImpl implements SetMealService {
    //注入dao
    @Autowired
    private SetMealDao setMealDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private JedisPool jedisPool;

    /**
     * 新增套餐
     * @param setmeal
     * @param checkgroupIds
     */
    @Override
    @Transactional
    public void add(Setmeal setmeal, List<Integer> checkgroupIds) {
            //新增套餐返回自增主键
        setMealDao.add(setmeal);
        Integer id = setmeal.getId();
        //根据套餐id，往中间表新增与检查组的关联
        setMealAndCheckGroup(id,checkgroupIds);

    }

    /**
     * 分页查询
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    @Override
    public PageResult findpage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
            Page<Setmeal> page= setMealDao.findpage(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 根据id查询套餐
     * @param id
     * @return
     */
 /*   @Override
    public Setmeal findById(Integer id) {
        String setMealJson = jedisPool.getResource().get(RedisConstant.SETMEAL);
        Setmeal setmeal=null;
        if (setMealJson == null) {
            setmeal = setMealDao.findById(id);
            setMealJson = JSONObject.toJSON(setmeal).toString();

        } else {
            setmeal = JSONObject.parseObject(setMealJson, Setmeal.class);
        }
        return setmeal;
    } */
    @Override
    public Setmeal findById(Integer id) {

        Setmeal setmeal = setMealDao.findById(id);
        return setmeal;
    }
    /**
     * 根据套餐id 查询检查项与其关联的ids
     */
    @Override
    public List<Integer> findCheckGroupIdsBySetMealId(Integer id) {
        List<Integer> list=  setMealDao.findCheckGroupIdsBySetMealId(id);
        return list;
    }

    /**
     * 编辑套餐
     * @param setmeal
     * @param checkgroupIds
     */
    @Override
    public void edit(Setmeal setmeal, List<Integer> checkgroupIds) {
            //套餐的id
        Integer id = setmeal.getId();
        //修改套餐
            setMealDao.edit(setmeal);
            //清空套餐与检查组的关联中间表记录
            setMealDao.deleteSetMealAndCheckGroup(id);
            //重新关联
            setMealAndCheckGroup(id,checkgroupIds);
    }

    /**
     * 删除套餐
     * @param id
     */
    @Override
    @Transactional
    public void deleteById(Integer id) {
        //先清除套餐与检查组相关联的ids
        setMealDao.deleteSetMealAndCheckGroup(id);
        setMealDao.deleteById(id);
    }

    /**
     * 根据id删除图片
     * @param id
     */
    @Override
    public void deletePicById(Integer id) {
        setMealDao.deletePicById(id);
    }

    /**
     * 查询所有套餐
     * @return
     */
   /* @Override
    public List<Setmeal> getSetmeal() {
        String setMealJson = jedisPool.getResource().get(RedisConstant.SETMEAL_LIST);
        List<Setmeal> setmeals=null;
        if (setMealJson == null) {
             setmeals = setMealDao.findAll();
            setMealJson =  JSONObject.toJSON(setmeals).toString();
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_LIST, setMealJson);
        } else {
            setmeals = JSONObject.parseArray(setMealJson, Setmeal.class);
        }
        return setmeals ;
    } */
    @Override
    public List<Setmeal> getSetmeal() {

        List<Setmeal> setmeals = setMealDao.findAll();

        return setmeals ;
    }

    /**
     * 获取套餐信息
     * setmealNames
     * setmealCount
     * @return
     */
    @Override
    public Map getSetmealReport() {
        List<Map<String,Object>>  orderCount = orderDao.countOrder();
        List<String> stealsName=new ArrayList<>();
        for (Map<String, Object> map : orderCount) {
            String name = (String) map.get("name");
            stealsName.add(name);
        }
        Map map=new HashMap<>();
        map.put("setmealNames",stealsName);
        map.put("setmealCount",orderCount);


        return map;
    }

    /**
     * 套餐与检查组的关联
     */
    public void setMealAndCheckGroup(Integer id,List<Integer> checkgroupIds) {
        //先判断ids 是否为空
        if (checkgroupIds !=null && checkgroupIds.size()>0) {
            for (Integer checkgroupId : checkgroupIds) {
                /*//存入map中
                Map<String,Integer> map=new HashMap<>();
                map.put("setmeal_id",id);
                map.put("checkgroup_id",checkgroupId);*/
                setMealDao.setMealAndCheckGroup(id,checkgroupId);
            }
        }

    }
}

