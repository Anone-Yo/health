package com.anone.dao;

import com.anone.pojo.User;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户的 dao 接口
 */
public interface UserDao {
    /**
     * 根据user 和role 通过username 查询 user对象 role 对象
     * @param username
     * @return
     */
    User findUserByUsername(String username);
    //分页查询
    Page<User> findPage(String queryString);
    //新增用户
    void add(User user);
    //新增用户与角色关联
    void addRoleAndUser(@Param("userId") Integer userId, @Param("roleId")Integer roleId);
    //清除关联
    void deleteRoleAndUser(Integer id);
    //删除
    void delete(Integer id);
    //回显
    User update(Integer id);
    //获取关联ids
    List<Integer> getRoleIdsByUserId(Integer id);
    //编辑
    void edit(User user);
}
