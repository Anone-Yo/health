package com.anone.dao;

import com.anone.pojo.CheckItem;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * 这是检查项 的dao 接口
 */
public interface CheckItemDao {
    /**
     * 新增检查项
     * @param checkItem
     */
    void add(CheckItem checkItem);

    /**
     * 分页查询
     * @param queryString
     * @return
     */
    Page<CheckItem> selectByQueryString(String queryString);

    /**
     * 检查项与检查组关联表的个数
     * @param checkId
     * @return
     */
    int selectCountWithCheckgroup(Integer checkId);

    /**
     * 删除检查项
     * @param checkId
     */
    void deleteById(Integer checkId);

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

    /**
     * 根据检查组id查询检查项
     */
    CheckItem findCheckItemBycheckGroupId();
}
