package com.anone.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.anone.dao.CheckGroupDao;
import com.anone.entity.PageResult;
import com.anone.pojo.CheckGroup;
import com.anone.service.CheckGroupService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 这是 检查组 服务 实现
 */
@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServiceImpl implements CheckGroupService {
    //注入dao
    @Autowired
    private CheckGroupDao checkGroupDao;

    /**
     *新增检查组
     * @param checkGroup
     * @param checkitemIds
     */
    @Override
    @Transactional
    public void add(CheckGroup checkGroup, List<Integer> checkitemIds) {
        //新增checkGroup
        checkGroupDao.add(checkGroup);
        //返回主键id
        Integer id = checkGroup.getId();
        //在中间表新增管理
        setCheckItemAndCheckGroup(id,checkitemIds);
    }

    /**
     * 分页查询
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
       Page<CheckGroup> checkGroupPage= checkGroupDao.findPage(queryString);
        return new PageResult(checkGroupPage.getTotal(),checkGroupPage.getResult());
    }

    /**
     * 根据id查询检查组
     * @param id
     * @return
     */
    @Override
    public CheckGroup findCheckGroupById(Integer id) {
        CheckGroup checkGroup= checkGroupDao.findCheckGroupById(id);
        return checkGroup;
    }

    /**
     * 查询ids
     * @param id
     * @return
     */
    @Override
    public List<Integer> findIdsById(Integer id) {
        List<Integer> list=   checkGroupDao.findIdsById(id);
        return list;
    }

    /**
     * 编辑检查组
     * @param checkGroup
     * @param checkitemIds
     */
    @Transactional
    @Override
    public void edit(CheckGroup checkGroup, List<Integer> checkitemIds) {
        Integer id = checkGroup.getId();
        //更新检查组
        checkGroupDao.edit(checkGroup);
        //根据检查组id删除检查项
        checkGroupDao.deleteCheckItemIdsById(id);
        //重新关联中间表
        setCheckItemAndCheckGroup(id,checkitemIds);
    }

    /**
     * 删除检查组
     * @param id
     */
    @Override
    @Transactional
    public void deleteById(Integer id) {
        //先清空关联
        checkGroupDao.deleteCheckItemIdsById(id);
        //再删除检查组
        checkGroupDao.deleteById(id);
    }

    /**
     * 查询所有
     * @return
     */
    @Override
    public List<CheckGroup> findAll() {
        List<CheckGroup> list=   checkGroupDao.findAll();
        return list;
    }

    /**
     * 定义一个管理检查组与检查项的关系方法
     */
    public void setCheckItemAndCheckGroup(Integer id,List<Integer> checkitemIds) {
        //首先判断list是否为空
        if (checkitemIds !=null && checkitemIds.size()>0) {
            //循环遍历
            for (Integer checkitemId : checkitemIds) {
                Map<String,Integer> map=new HashMap<>();
                //这个封装检查组的id
                map.put("checkgroup_id",id);
                //这个封装遍历的检查项id
                map.put("checkitem_id",checkitemId);
                checkGroupDao.setCheckItemAndCheckGroup(map);
            }
        }

    }
}
