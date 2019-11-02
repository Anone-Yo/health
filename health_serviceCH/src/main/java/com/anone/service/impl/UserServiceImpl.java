package com.anone.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.anone.dao.PermissionDao;
import com.anone.dao.RoleDao;
import com.anone.dao.UserDao;
import com.anone.entity.PageResult;
import com.anone.pojo.Permission;
import com.anone.pojo.Role;
import com.anone.pojo.User;
import com.anone.service.UserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Transactional
@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private PermissionDao permissionDao;
    @Autowired
     private RoleDao roleDao;
    @Override
    public User findUserByUsername(String username) {
       User user= userDao.findUserByUsername(username);
       //判断user 是否为空
        if (user != null) {
         Set<Role> roles =   roleDao.getRoleByUserId(user.getId());
            if (roles!=null&&roles.size()>0) {
                //获取当前角色的权限
                for (Role role : roles) {
                    Integer roleId = role.getId();
                 Set<Permission> permissions =   permissionDao.getPermissionByRoleId(roleId);
                    if (permissions!=null && permissions.size()>0) {
                        role.setPermissions(permissions);
                    }
                }
                user.setRoles(roles);
            }
        }
        return user;
    }
    //分页查询
    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        Page<User> page= userDao.findPage(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }
    //新增用户
    @Override
    public void add(User user, List<Integer> roleIds) {
        userDao.add(user);
        Integer userId = user.getId();

        if (roleIds!=null&&roleIds.size()>0) {
            for (Integer roleId : roleIds) {
                userDao.addRoleAndUser(userId,roleId);
            }
        }
    }
    //删除
    @Override
    public void delete(Integer id) {
        userDao.deleteRoleAndUser(id);
        userDao.delete(id);
    }
    //回显
    @Override
    public User update(Integer id) {
        return userDao.update(id);
    }
    //获取关联
    @Override
    public List<Integer> getRoleIdsByUserId(Integer id) {
        return userDao.getRoleIdsByUserId(id);
    }
    //编辑
    @Override
    public void edit(User user, List<Integer> roleIds) {
        userDao.edit(user);
        Integer userId = user.getId();
        userDao.deleteRoleAndUser(userId);
        if (roleIds!=null&&roleIds.size()>0) {
            for (Integer roleId : roleIds) {
                userDao.addRoleAndUser(userId,roleId);
            }
        }
    }
}
