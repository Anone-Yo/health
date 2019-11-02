package com.anone.service;

import com.anone.entity.PageResult;
import com.anone.pojo.Menu;

import java.util.List;
import java.util.Set;

/**
 * 菜单 服务 接口
 */
public interface MenuService {
    /**
     * 获取菜单
     * @return
     */
    List<Menu> getMenu();

    /**
     * 分页查询
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    /**
     * 新增菜单
     * @param menu
     */
    void add(Menu menu);

    /**
     * 删除菜单
     * @param id
     */
    void delete(Integer id);

    /**
     * 根据id查询菜单
     * @param id
     * @return
     */
    Menu findMenuById(Integer id);

    /**
     * 编辑
     * @param menu
     */
    void edit(Menu menu);
    //查询所有
    List<Menu> findAll();
    //根据 role id 查询菜单
    List<Integer> roleMenuById(Integer id);
}
