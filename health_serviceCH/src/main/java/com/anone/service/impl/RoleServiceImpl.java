package com.anone.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.anone.dao.PermissionDao;
import com.anone.dao.RoleDao;
import com.anone.entity.PageResult;
import com.anone.pojo.Permission;
import com.anone.pojo.Role;
import com.anone.service.RoleService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service(interfaceClass = RoleService.class)
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;
   //分页查询
    @Override
    public PageResult findpage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        Page<Role> page= roleDao.findPage(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }
    //新增
    @Override
    public void add(Map map) {
        String name = (String) map.get("name");
        String keyword = (String) map.get("keyword");
        String description = (String) map.get("description");
        String permissionNums =  map.get("permissionNums").toString();
        String menuNums = map.get("menuNums").toString();
        List<Integer> permissionNumList = JSON.parseArray(permissionNums, Integer.class);
        List<Integer> menuNumsList = JSON.parseArray(menuNums, Integer.class);
        Role role = new Role();
        role.setKeyword(keyword);
        role.setName(name);
        role.setDescription(description);
        roleDao.add(role);
        Integer roleId = role.getId();

        if (permissionNumList!=null&&permissionNumList.size()>0) {
            for (int permissionId : permissionNumList) {
                roleDao.setRoleAndPermission(roleId,permissionId);
            }
        }
        if (menuNumsList!=null&&menuNumsList.size()>0) {
            for (int menuId : menuNumsList) {
                roleDao.setRoleAndMenu(roleId,menuId);
            }
        }
    }
    //删除角色
    @Override
    public void delete(Integer id) {
        roleDao.deleteRoleAndPermission(id);
        roleDao.deleteRoleAndMenu(id);
        roleDao.delete(id);
    }
    //回显role
    @Override
    public Role update(Integer id) {
     Role role =   roleDao.update(id);
        return role;
    }
    //编辑
    @Override
    public void edit(Map map) {
        Integer id = (Integer) map.get("id");
        String name = (String) map.get("name");
        String keyword = (String) map.get("keyword");
        String description = (String) map.get("description");
        String permissionNums =  map.get("permissionNums").toString();
        String menuNums = map.get("menuNums").toString();
        List<Integer> permissionNumList = JSON.parseArray(permissionNums, Integer.class);
        List<Integer> menuNumsList = JSON.parseArray(menuNums, Integer.class);
        Role role = new Role();
        role.setId(id);
        role.setKeyword(keyword);
        role.setName(name);
        role.setDescription(description);
        roleDao.edit(role);
        Integer roleId = role.getId();
        roleDao.deleteRoleAndMenu(roleId);
        roleDao.deleteRoleAndPermission(roleId);

        if (permissionNumList!=null&&permissionNumList.size()>0) {
            for (int permissionId : permissionNumList) {
                roleDao.setRoleAndPermission(roleId,permissionId);
            }
        }
        if (menuNumsList!=null&&menuNumsList.size()>0) {
            for (int menuId : menuNumsList) {
                roleDao.setRoleAndMenu(roleId,menuId);
            }
        }
    }
    //查询所有
    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }
}
