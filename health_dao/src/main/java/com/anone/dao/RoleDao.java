package com.anone.dao;


import com.anone.pojo.Role;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 这是角色的dao  接口
 */
public interface RoleDao {
    /**
     * 根据user id 查询 角色
     *
     * @param id
     * @return
     */
    Set<Role> getRoleByUserId(Integer id);

    //分页查询
    Page<Role> findPage(String queryString);

    //新增
    void add(Role role);

    //设置角色与权限关联
    void setRoleAndPermission(@Param("roleId") Integer roleId, @Param("permissionId") int permissionId);

    //设置角色与菜单关联
    void setRoleAndMenu(@Param("roleId") Integer roleId, @Param("menuId") int menuId);

    //根据id删除role和权限关联
    void deleteRoleAndPermission(Integer id);

    //根据id删除role和菜单关联
    void deleteRoleAndMenu(Integer id);

    //删除角色
    void delete(Integer id);

    //回显
    Role update( Integer id);
    //编辑
    void edit(Role role);
    //查询所有
    List<Role> findAll();
}
