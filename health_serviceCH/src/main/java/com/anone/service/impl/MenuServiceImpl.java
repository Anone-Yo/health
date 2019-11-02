package com.anone.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.anone.dao.MenuDao;
import com.anone.entity.PageResult;
import com.anone.pojo.Menu;
import com.anone.service.MenuService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * 菜单 服务 实现
 */
@Transactional
@Service(interfaceClass = MenuService.class)
public class MenuServiceImpl implements MenuService {
   @Autowired
   private MenuDao menuDao;

    /**
     * 获取菜单
     * @return
     */
    @Override
    public List<Menu> getMenu() {
        //查询所有父菜单
    List<Menu> menus=menuDao.findAll();
        if (menus!=null&&menus.size()>0) {
            for (Menu menu : menus) {
                //根据父菜单id 进行自表查询 ，将子菜单查询出来
                Integer menuId = menu.getId();
             List<Menu> children= menuDao.findChildrenMenu(menuId);
                if (children != null && children.size() > 0) {
                    menu.setChildren(children);
                }
            }
        }
        return menus;
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
        Page<Menu> page= menuDao.findPage(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 新增菜单
     * @param menu
     */
    @Override
    public void add(Menu menu) {
        menuDao.add(menu);
    }

    /**
     * 删除菜单
     * @param id
     */
    @Override
    public void delete(Integer id) {
        //查看是否跟role有关联
      Integer count=  menuDao.findMenuAndRoleCount(id);
        if (count > 0) {
            throw new RuntimeException("菜单与role有关联，删除失败");
        } else {
            menuDao.delete(id);
        }
    }

    /**
     * 根据id查询菜单
     * @param id
     * @return
     */
    @Override
    public Menu findMenuById(Integer id) {
        return menuDao.findMenuById(id);
    }

    /**
     * 编辑菜单
     * @param menu
     */
    @Override
    public void edit(Menu menu) {
        menuDao.edit(menu);
    }
    //查询所有
    @Override
    public List<Menu> findAll() {
        return menuDao.findAlls();
    }
    //根据role id  查询菜单
    @Override
    public List<Integer> roleMenuById(Integer id) {
        return menuDao.roleMenuById(id);
    }
}
