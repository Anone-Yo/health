package com.anone.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.anone.pojo.Permission;
import com.anone.pojo.Role;
import com.anone.pojo.User;
import com.anone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class springSecurityUserService implements UserDetailsService {
    /**
     * 密码加锁
     */
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Reference
    private UserService UserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       //调用业务 根据用户名查询user
       User user =UserService.findUserByUsername(username);
       //判断user是否为空
        if (user == null) {
            return null;
        }
        // 授权
        List<GrantedAuthority> list=new ArrayList<>();
        Set<Role> roles = user.getRoles();
        //进行授权
        if (roles!=null && roles.size()>0) {
            for (Role role : roles) {
                list.add(new SimpleGrantedAuthority(role.getKeyword()));
                Set<Permission> permissions = role.getPermissions();
                if (permissions!=null &&permissions.size()>0) {
                    for (Permission permission : permissions) {
                        list.add(new SimpleGrantedAuthority(permission.getKeyword()));
                    }
                }
            }
        }

        org.springframework.security.core.userdetails.User detailUser=new org.springframework.security.core.userdetails.User(username,user.getPassword(),list);

        return detailUser;
    }
}
