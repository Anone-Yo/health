package com.anone.service;

import com.anone.entity.PageResult;
import com.anone.pojo.User;

import java.util.List;

/**
 * 这是用户 服务 接口
 */
public interface UserService {
    /**
     * 根据用户名查询用户对象
     * @param username
     * @return
     */
    User findUserByUsername(String username);
    //分页查询
    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);
    //新增用户
    void add(User user, List<Integer> roleIds);
    //删除
    void delete(Integer id);
    //回显
    User update(Integer id);
    //获取关联
    List<Integer> getRoleIdsByUserId(Integer id);
    //编辑
    void edit(User user, List<Integer> roleIds);
}
