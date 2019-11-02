package com.anone.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.anone.constant.MessageConstant;
import com.anone.entity.PageResult;
import com.anone.entity.QueryPageBean;
import com.anone.entity.Result;
import com.anone.pojo.CheckGroup;
import com.anone.service.CheckGroupService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/checkgroup",method = RequestMethod.GET)
public class CheckGroupController {

    @Reference
    private CheckGroupService checkGroupService;

    /**
     * 新增检查组
     * @param checkGroup
     * @param checkitemIds
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('CHECKGROUP_ADD')")
    public Result add(@RequestBody CheckGroup checkGroup, @RequestParam List<Integer> checkitemIds) {
        try {
            checkGroupService.add(checkGroup,checkitemIds);
            return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }

    }

    /**
     * 分页查询
     */
    @RequestMapping(value = "/findpage",method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('CHECKGROUP_QUERY')")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
     PageResult pageResult=   checkGroupService.findPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize(),queryPageBean.getQueryString());

     return pageResult;
    }

    /**
     * 根据id查询检查组
     */
    @RequestMapping(value = "/findCheckGroupById",method = RequestMethod.GET)
    public Result findCheckGroupById(Integer id) {
        try {
            CheckGroup checkGroup=  checkGroupService.findCheckGroupById(id);
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    /**
     * 查询选中的ids
     */
    @RequestMapping(value = "/findIdsById",method = RequestMethod.GET)
    public Result findIdsById(Integer id) {
        try {
           List<Integer> list=  checkGroupService.findIdsById(id);
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }
    /**
     * 修改检查组
     */
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('CHECKGROUP_EDIT')")
    public Result edit(@RequestBody CheckGroup checkGroup, @RequestParam List<Integer> checkitemIds) {
        try {
            checkGroupService.edit(checkGroup,checkitemIds);
            return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_CHECKGROUP_FAIL);
        }

    }
    /**
     * 删除检查组
     */
    @RequestMapping(value = "/deleteById",method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('CHECKGROUP_DELETE')")
    public Result deleteById(Integer id) {
        try {
            checkGroupService.deleteById(id);
            return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
    }

    /**
     * 查询所有检查组
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public Result findAll() {
        try {
            List<CheckGroup> list=  checkGroupService.findAll();
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_LOADING_FAIL);

        }
    }
}
