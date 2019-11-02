package com.anone.service;

import com.anone.entity.PageResult;
import com.anone.pojo.CheckGroup;

import java.util.List;

/**
 * 这是检查组 服务接口
 */
public interface CheckGroupService {
    /**
     * 新增检查组
     * @param checkGroup
     * @param checkitemIds
     */
    void add(CheckGroup checkGroup, List<Integer> checkitemIds);

    /**
     * 分页查询
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

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
     * 修改检查组
     * @param checkGroup
     * @param checkitemIds
     */
    void edit(CheckGroup checkGroup, List<Integer> checkitemIds);

    /**
     * 删除检查组
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 查询所有
     * @return
     */
    List<CheckGroup> findAll();

}
