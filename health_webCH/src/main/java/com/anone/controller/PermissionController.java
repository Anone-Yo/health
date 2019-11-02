package com.anone.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.anone.constant.MessageConstant;
import com.anone.entity.PageResult;
import com.anone.entity.QueryPageBean;
import com.anone.entity.Result;
import com.anone.pojo.Menu;
import com.anone.pojo.Permission;
import com.anone.service.MenuService;
import com.anone.service.PermissionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *权限 Controller
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Reference
    private PermissionService permissionService;


    /**
     * 分页查询所有菜单
     */
    @PostMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
      PageResult pageResult= permissionService.findPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize(),queryPageBean.getQueryString());
      return pageResult;
    }

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Permission permission) {
        try {
            permissionService.add(permission);
            return new Result(true,MessageConstant.ADD_PERMISSION_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_PERMISSION_FAIL);
        }

    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public Result delete(Integer id) {
        try {
            permissionService.delete(id);
            return new Result(true,MessageConstant.DELETE_PERMISSION_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_PERMISSION_FAIL);
        }
    }

    /**
     * 回显
     */
    @PostMapping("/update")
    public Result update(Integer id) {
        try {
            Permission permission=  permissionService.findPermissionById(id);
            return new Result(true,MessageConstant.SHOW_PERMISSION_SUCCESS,permission);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.SHOW_PERMISSION_FAIL);
        }
    }

    /**
     * 编辑
     */
    @PostMapping("/edit")
    public Result edit(@RequestBody Permission permission) {
        try {
            permissionService.edit(permission);
            return new Result(true,MessageConstant.EDIT_PERMISSION_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_PERMISSION_FAIL);
        }
    }

    /**
     * 查询所有
     */
    @GetMapping("/findAll")
    public Result findAll() {
        try {
            List<Permission> permissionList=   permissionService.findAll();
            return new Result(true,MessageConstant.QUERY_PERMISSION_SUCCESS,permissionList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_PERMISSION_FAIL);
        }
    }

    //根据role id 获取permissionids
    @GetMapping("/rolePermissionById")
    public Result rolePermissionById(Integer id) {
        try {
            List<Integer>  list=  permissionService.rolePermissionById(id);
            return new Result(true,MessageConstant.QUERY_PERMISSION_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_PERMISSION_FAIL);
        }
    }
}
