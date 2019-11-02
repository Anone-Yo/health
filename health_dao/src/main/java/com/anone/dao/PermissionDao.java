package com.anone.dao;

import com.anone.pojo.Permission;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Set;

/**
 * 权限 dao  接口
 */
public interface PermissionDao {
    /**
     *
     * @param roleId
     * @return
     */
    Set<Permission> getPermissionByRoleId(Integer roleId);
    //分页查询
    Page<Permission> findPage(String queryString);
    //新增
    void add(Permission permission);
//查看与role是否关联
    Integer findPermissionAndRoleCount(Integer id);
    //删除
    void delete(Integer id);
    //根据id查询
    Permission findPermissionById(Integer id);
    //编辑
    void edit(Permission permission);
    //查询所有
    List<Permission> findAll();

    //根据role id获取权限ids
    List<Integer> rolePermissionById(Integer id);
}
