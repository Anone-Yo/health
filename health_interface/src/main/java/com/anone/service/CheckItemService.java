package com.anone.service;

import com.anone.entity.PageResult;
import com.anone.entity.Result;
import com.anone.pojo.CheckItem;

import java.util.List;

/**
 * 这是检查项 service 接口
 */
public interface CheckItemService {
    /**
     * 新增检查项
     * @param checkItem
     */
    void add(CheckItem checkItem);

    /**
     * 分页查询
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    /**
     *  删除检查项
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 根据id查询检查项
     * @param id
     * @return
     */
    CheckItem findById(Integer id);

    /**
     * 修改检查项
     * @param checkItem
     */
    void edit(CheckItem checkItem);

    /**
     * 查询所有
     * @return
     */
    List<CheckItem> findAll();
}
