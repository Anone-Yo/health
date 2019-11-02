package com.anone.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.anone.constant.MessageConstant;
import com.anone.entity.PageResult;
import com.anone.entity.QueryPageBean;
import com.anone.entity.Result;
import com.anone.pojo.Menu;
import com.anone.service.MenuService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 菜单 Controller
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Reference
    private MenuService menuService;

    /**
     * 获取菜单
     * @return
     */
    @GetMapping("/getMenu")
    public Result getMenu() {
        try {
            List<Menu> list= menuService.getMenu();
            System.out.println(list);
            return new Result(true, MessageConstant.GET_MENU_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_MENU_FAIL);
        }
    }

    /**
     * 分页查询所有菜单
     */
    @PostMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
      PageResult pageResult= menuService.findPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize(),queryPageBean.getQueryString());
      return pageResult;
    }

    /**
     * 新增菜单
     */
    @PostMapping("/add")
    public Result add(@RequestBody Menu menu) {
        try {
            menuService.add(menu);
            return new Result(true,MessageConstant.ADD_MENU_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_MENU_FAIL);
        }

    }

    /**
     * 删除菜单
     */
    @PostMapping("/delete")
    public Result delete(Integer id) {
        try {
            menuService.delete(id);
            return new Result(true,MessageConstant.DELETE_MENU_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_MENU_FAIL);
        }
    }

    /**
     * 回显
     */
    @PostMapping("/update")
    public Result update(Integer id) {
        try {
            Menu menu=  menuService.findMenuById(id);
            return new Result(true,MessageConstant.SHOW_MENU_SUCCESS,menu);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.SHOW_MENU_FAIL);
        }
    }

    /**
     * 编辑
     */
    @PostMapping("/edit")
    public Result edit(@RequestBody Menu menu) {
        try {
            menuService.edit(menu);
            return new Result(true,MessageConstant.EDIT_MENU_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_MENU_FAIL);
        }
    }

    /**
     * 查询所有
     */
    @GetMapping("/findAll")
    public Result findAll() {
        try {
            List<Menu> menuList=  menuService.findAll();
            return new Result(true,MessageConstant.QUERY_MENU_SUCCESS,menuList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_MENU_FAIL);
        }

    }

    /**
     * 根据 role id获取套餐集合ids
     */
    @GetMapping("/roleMenuById")
    public Result roleMenuById(Integer id) {
        try {
            List<Integer> menus= menuService.roleMenuById(id);
            return new Result(true,MessageConstant.QUERY_MENU_SUCCESS,menus);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_MENU_FAIL);
        }
    }
}
