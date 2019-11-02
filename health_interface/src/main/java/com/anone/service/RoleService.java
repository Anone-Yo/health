package com.anone.service;

import com.anone.entity.PageResult;
import com.anone.pojo.Role;

import java.util.List;
import java.util.Map;

public interface RoleService {
    //分页查询
    PageResult findpage(Integer currentPage, Integer pageSize, String queryString);
    //新增
    void add(Map map);
    //删除
    void delete(Integer id);
    //回显role
    Role update(Integer id);
    //编辑
    void edit(Map map);
    //查询所有
    List<Role> findAll();
}
