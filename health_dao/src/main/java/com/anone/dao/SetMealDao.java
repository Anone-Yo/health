package com.anone.dao;

import com.anone.pojo.Setmeal;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 套餐 dao 接口
 */
public interface SetMealDao {
    /**
     * 新增套餐
     * @param setmeal
     */
    void add(Setmeal setmeal);

    /**
     * 关联套餐与检查组
     * @param
     */
    /*void setMealAndCheckGroup(Map<String, Integer> map);*/
    void setMealAndCheckGroup(@Param("setmeal_id") Integer id, @Param("checkgroup_id") Integer checkgroupId);

    /**
     * 分页查询
     * @param queryString
     * @return
     */
    Page<Setmeal> findpage(String queryString);

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
     * 更新套餐
     * @param setmeal
     */
    void edit(Setmeal setmeal);

    /**
     * 根据套餐id 删除套餐与检查组关联的中间表记录
     * @param id
     */
    void deleteSetMealAndCheckGroup(Integer id);

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
    List<Setmeal> findAll();
}
