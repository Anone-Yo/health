package com.anone.dao;

import com.anone.pojo.CheckGroup;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * 这是检查组的 dao 接口
 */
public interface CheckGroupDao {
    /**
     * 新增检查组
     * @param checkGroup
     */
    void add(CheckGroup checkGroup);

    /**
     * 在中间表中添加关联
     * @param map
     */
    void setCheckItemAndCheckGroup(Map<String, Integer> map);

    /**
     * 分页查询
     * @param queryString
     * @return
     */
    Page<CheckGroup> findPage(String queryString);

    /**
     * 根据id查询检查组
     * @param id
     * @return
     */
    CheckGroup findCheckGroupById(Integer id);

    /**
     * 查询ids
     * @param id
     * @return
     */
    List<Integer> findIdsById(Integer id);

    /**
     * 更新检查组
     * @param checkGroup
     */
    void edit(CheckGroup checkGroup);

    /**
     * 根据检查组id 删除ids
     * @param id
     */
    void deleteCheckItemIdsById(Integer id);

    /**
     * 删除检查组
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 查询所有检查组
     * @return
     */
    List<CheckGroup> findAll();

    /**
     * 根据套餐id查询检查组
     */
    CheckGroup findCheckGroupBySetmealId();

}
