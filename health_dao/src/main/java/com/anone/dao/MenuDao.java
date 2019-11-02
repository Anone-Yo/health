package com.anone.dao;

import com.anone.pojo.Menu;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Set;

/**
 * 菜单 dao 接口
 */
public interface MenuDao {
    /**
     * 查询所有父菜单
     * @return
     */
    List<Menu> findAll();

    /**
     * 查询子菜单
     * @param menuId
     * @return
     */
    List<Menu> findChildrenMenu(Integer menuId);

    /**
     * 分页查询
     * @param queryString
     * @return
     */
    Page<Menu> findPage(String queryString);

    /**
     * 新增菜单
     * @param menu
     */
    void add(Menu menu);

    /**
     * 查看菜单与role是否有关联
     * @param id
     * @return
     */
    Integer findMenuAndRoleCount(Integer id);

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
     * 编辑菜单
     * @param menu
     */
    void edit(Menu menu);
    //查询所有
    List<Menu> findAlls();
    //根据role id 查询菜单
    List<Integer> roleMenuById(Integer id);
}
