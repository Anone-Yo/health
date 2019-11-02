package com.anone.service;

import com.anone.entity.Result;

import java.util.Map;

/**
 *  预约 服务 接口
 */
public interface OrderService {


    /**
     * 根据预约表 id
     * 查询order info
     * @param id
     * @return
     */
    Map<String, Object> findById(Integer id);

    /**
     * 提交预约
     * 往预约表插入数据
     * @param map
     * @return
     */
    Result submit(Map map) throws Exception;
}
