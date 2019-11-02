package com.anone.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.anone.constant.MessageConstant;
import com.anone.entity.PageResult;
import com.anone.entity.QueryPageBean;
import com.anone.entity.Result;
import com.anone.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.GET;
import java.util.List;

/**
 * 用户控制层
 *
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Reference
    private UserService userService;

    /**
     * 获取用户名 从框架对象中获取
     */
    @GetMapping("/getUsername")
    public Result getUsername() {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,user.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.GET_USERNAME_FAIL);
        }

    }

    /**
     * 分页查询
     */
    @PostMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult=  userService.findPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize(),queryPageBean.getQueryString());
        return pageResult;
    }

    //新增用户
    @PostMapping("/add")
    public Result add(@RequestBody com.anone.pojo.User user, @RequestParam List<Integer> roleIds) {
        try {
            userService.add(user,roleIds);
            return new Result(true,MessageConstant.ADD_USER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_USER_FAIL);
        }
    }
    //删除用户
    @PostMapping("/delete")
    public Result delete(Integer id) {
        try {
            userService.delete(id);
            return new Result(true,MessageConstant.DELETE_USER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_USER_FAIL);
        }
    }

    //回显
    @PostMapping("/update")
    public Result update(Integer id) {
        try {
            com.anone.pojo.User user= userService.update(id);
            return new Result(true,MessageConstant.QUERY_USER_SUCCESS,user);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_USER_FAIL);
        }
    }

    //获取roleids  getRoleIdsByUserId
    @PostMapping("/getRoleIdsByUserId")
    public Result getRoleIdsByUserId(Integer id) {
        try {
            List<Integer> list=  userService.getRoleIdsByUserId(id);
            return new Result(true,MessageConstant.QUERY_USER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_USER_FAIL);
        }
    }

    //编辑
    @PostMapping("/edit")
    public Result edit(@RequestBody com.anone.pojo.User user, @RequestParam List<Integer> roleIds) {
        try {
            userService.edit(user,roleIds);
            return new Result(true,MessageConstant.EDIT_USER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_USER_FAIL);
        }
    }
}
