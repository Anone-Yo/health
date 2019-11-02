package com.anone.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.anone.dao.CheckItemDao;
import com.anone.entity.PageResult;
import com.anone.entity.Result;
import com.anone.pojo.CheckItem;
import com.anone.service.CheckItemService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 这是检查项的 service 实现
 */
@Service(interfaceClass = CheckItemService.class)//指定代理实现类的包名
@Transactional
public class CheckItemServiceImpl implements CheckItemService{
    //注入dao
    @Autowired
    private CheckItemDao checkItemDao;

    /**
     * 新增检查项
     * @param checkItem
     */
    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
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
       //分页插件操作
        PageHelper.startPage(currentPage, pageSize);
        //必须紧跟
         Page<CheckItem> page= checkItemDao.selectByQueryString(queryString);
        PageResult pageResult = new PageResult(page.getTotal(), page.getResult());

        return pageResult;
    }

    /**
     * 删除检查项
     * @param CheckId
     */
    @Override
    public void deleteById(Integer CheckId) {
        //首先查询是否存在关联表
       int count= checkItemDao.selectCountWithCheckgroup(CheckId);
       //判断count是否大于o
        if (count > 0) {
            //删除失败
            throw new RuntimeException("该检查项与检查组存在关联");
        } else {
            //删除成功
            checkItemDao.deleteById(CheckId);
        }
    }

    /**根据id查询检查项
     *
     * @param id
     * @return
     */
    @Override
    public CheckItem findById(Integer id) {
       CheckItem checkItem= checkItemDao.findById(id);
        return checkItem;
    }

    /**
     * 修改检查项
     * @param checkItem
     */
    @Override
    public void edit(CheckItem checkItem) {
        checkItemDao.edit(checkItem);
    }

    /**
     * 查询所有
     * @return
     */
    @Override
    public List<CheckItem> findAll() {
     List<CheckItem> checkItemList=   checkItemDao.findAll();
        return checkItemList;
    }
}
