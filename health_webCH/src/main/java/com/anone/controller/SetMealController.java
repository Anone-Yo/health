package com.anone.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.anone.constant.MessageConstant;
import com.anone.constant.RedisConstant;
import com.anone.entity.PageResult;
import com.anone.entity.QueryPageBean;
import com.anone.entity.Result;
import com.anone.pojo.Setmeal;
import com.anone.service.SetMealService;
import com.anone.utils.QiniuUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * 套餐 控制层
 */
@RestController
@RequestMapping("/setmeal")
public class SetMealController {

    @Reference
    private SetMealService setMealService;
    @Autowired
    private JedisPool jedisPool;

    /**
     * 自动上传图片
     */
    @RequestMapping(value = "/upload")
    public Result upload(MultipartFile imgFile)  {
        try {
            String originalFilename = imgFile.getOriginalFilename();
            //获取后缀
            String ext = FilenameUtils.getExtension(originalFilename);
            //定义唯一的名字
            String uName = UUID.randomUUID().toString().replace("-", "");
            String newName=uName+"."+ext;
            //将图片上传
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),newName);
            //优化
            // 上传成功之后....往redis中存储...这是自动上传 存在垃圾图片
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,newName);
            //将图片名封装到result
            return new Result(true,MessageConstant.UPLOAD_SUCCESS,newName);

        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.PIC_UPLOAD_FAIL);
        }

    }


    /**
     * 新增套餐
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('SETMEAL_ADD')")
    public Result add(@RequestBody Setmeal setmeal, @RequestParam List<Integer> checkgroupIds) {
        try {
            setMealService.add(setmeal,checkgroupIds);
            //优化
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmeal.getImg());
            return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);

        }
    }

    /**
     * 分页查询
     */
    @RequestMapping(value = "/findpage", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('SETMEAL_QUERY')")
    public PageResult findpage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult=   setMealService.findpage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize(),queryPageBean.getQueryString());
        return pageResult;
    }

    /**
     * 根据id查询套餐
     */
    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    public Result findById(Integer id) {
        try {
            Setmeal setmeal=  setMealService.findById(id);
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_LOADING_FAIL);
        }
    }

    /**
     * 根据套餐id 查询检查项与其关联的ids
     */
    @RequestMapping(value = "/findCheckGroupIdsBySetMealId",method = RequestMethod.GET)
    public Result findCheckGroupIdsBySetMealId(Integer id) {
        try {
            List<Integer> list=   setMealService.findCheckGroupIdsBySetMealId(id);
            return new Result(true,MessageConstant.QUERY_LINKING_SUCCESS,list);

        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_LOADING_FAIL);

        }

    }

    /**
     * 修改套餐
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('SETMEAL_EDIT')")
    public Result edit(@RequestBody Setmeal setmeal, @RequestParam List<Integer> checkgroupIds) {
        try {
            setMealService.edit(setmeal,checkgroupIds);
            return new Result(true, MessageConstant.EDIT_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_SETMEAL_FAIL);

        }

    }

    /**
     * 删除套餐
     */
    @RequestMapping(value = "/deleteById", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('SETMEAL_DELETE')")
    public Result deleteById(Integer id) {
        try {
            setMealService.deleteById(id);
            return new Result(true,MessageConstant.DELETE_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_SETMEAL_FAIL);
        }
    }

    /**
     * 根据id删除图片
     */
    @RequestMapping(value = "/deletePicById", method = RequestMethod.GET)
    public Result deletePicById(Integer id) {
        try {
            setMealService.deletePicById(id);
           return new Result(true, MessageConstant.DELETE_PIC_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
          return new Result(false, MessageConstant.DELETE_PIC_FAIL);
        }
    }
}

