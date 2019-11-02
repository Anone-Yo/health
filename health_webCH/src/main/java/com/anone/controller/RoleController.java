package com.anone.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.anone.constant.MessageConstant;
import com.anone.constant.RedisConstant;
import com.anone.entity.PageResult;
import com.anone.entity.QueryPageBean;
import com.anone.entity.Result;
import com.anone.pojo.Role;
import com.anone.pojo.Setmeal;
import com.anone.service.RoleService;
import com.anone.service.SetMealService;
import com.anone.utils.QiniuUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 套餐 控制层
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Reference
    private RoleService roleService;

    /**
     * 新增
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result add(@RequestBody Map map) {
        try {
            roleService.add(map);

            return new Result(true, MessageConstant.ADD_ROLE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_ROLE_FAIL);

        }
    }

    /**
     * 分页查询
     */
    @RequestMapping(value = "/findpage", method = RequestMethod.POST)
    public PageResult findpage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult=  roleService.findpage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize(),queryPageBean.getQueryString());
        return pageResult;
    }

    /**
     * 回显
     */
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public Result findById(Integer id) {
        try {
            Role role =  roleService.update(id);
            return new Result(true,MessageConstant.QUERY_ROLE_SUCCESS,role);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ROLE_FAIL);
        }
    }




    /**
     * 修改
     */

    @PutMapping("/edit")
    public Result edit(@RequestBody Map map) {
        try {
            roleService.edit(map);
            return new Result(true, MessageConstant.EDIT_ROLE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_ROLE_FAIL);

        }

    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public Result deleteById(Integer id) {
        try {
            roleService.delete(id);
            return new Result(true,MessageConstant.DELETE_ROLE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_ROLE_FAIL);
        }
    }

    /**
     * 查询所有
     */
    @GetMapping("/findAll")
    public Result findAll() {

        try {
            List<Role> list= roleService.findAll();
            return new Result(true,MessageConstant.QUERY_ROLE_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ROLE_FAIL);
        }

    }

}

