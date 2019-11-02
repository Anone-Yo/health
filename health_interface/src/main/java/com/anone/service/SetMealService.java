package com.anone.service;

import com.anone.entity.PageResult;
import com.anone.pojo.Setmeal;

import java.util.List;
import java.util.Map;

/**
 * 套餐 服务接口
 */
public interface SetMealService {
    void add(Setmeal setmeal, List<Integer> checkgroupIds);

    /**
     * 分页查询
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    PageResult findpage(Integer currentPage, Integer pageSize, String queryString);

    /**
     * 根据id查询套餐
     * @param id
     * @return
     */
    Setmeal findById(Integer id);
    /**
     * 根据套餐id 查询检查项与其关联的ids
     */
    List<Integer> findCheckGroupIdsBySetMealId(Integer id);

    /**
     * 编辑套餐
     * @param setmeal
     * @param checkgroupIds
     */
    void edit(Setmeal setmeal, List<Integer> checkgroupIds);

    /**
     * 删除套餐
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 根据id删除图片
     * @param id
     */
    void deletePicById(Integer id);

    /**
     * 查询所有套餐
     * @return
     */
    List<Setmeal> getSetmeal();

    /**
     * 获取套餐信息
     * setmealNames
     * setmealCount
     * @return
     */
    Map getSetmealReport();

}
