package com.anone.service;

import com.anone.entity.PageResult;
import com.anone.pojo.Permission;

import java.util.List;

public interface PermissionService {
    //分页查询
    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);
    //新增权限
    void add(Permission permission);
    //删除权限
    void delete(Integer id);
    //根据id查询权限
    Permission findPermissionById(Integer id);
    //编辑权限
    void edit(Permission permission);
    //查询所有
    List<Permission> findAll();
    //根据role id 查询ids
    List<Integer> rolePermissionById(Integer id);
}
