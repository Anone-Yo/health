package com.anone.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.anone.dao.PermissionDao;
import com.anone.entity.PageResult;
import com.anone.pojo.Permission;
import com.anone.service.PermissionService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = PermissionService.class)
@Transactional
public class PermissionServiceImpl implements PermissionService{
    @Autowired
    private PermissionDao permissionDao;
    //分页查询
    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        Page<Permission> page=permissionDao.findPage(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }
    //新增
    @Override
    public void add(Permission permission) {
        permissionDao.add(permission);
    }
    //删除
    @Override
    public void delete(Integer id) {
        //查看是否跟role有关联
        Integer count=  permissionDao.findPermissionAndRoleCount(id);
        if (count > 0) {
            throw new RuntimeException("该权限与role有关联，删除失败");
        } else {
            permissionDao.delete(id);
        }
    }
    //回显
    @Override
    public Permission findPermissionById(Integer id) {
        return permissionDao.findPermissionById(id);
    }
    //编辑
    @Override
    public void edit(Permission permission) {
        permissionDao.edit(permission);
    }
    //查询所有
    @Override
    public List<Permission> findAll() {
        return permissionDao.findAll();
    }
    //根据role id 查询ids
    @Override
    public List<Integer> rolePermissionById(Integer id) {
        return permissionDao.rolePermissionById(id);
    }
}
