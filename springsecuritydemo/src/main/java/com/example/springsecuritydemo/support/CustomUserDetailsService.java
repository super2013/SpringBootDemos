package com.example.springsecuritydemo.support;

/**
 * @ Author     ：lichao.
 * @ Date       ：Created in 9:53 2019/6/27
 * @ Description：
 * @ Modified By：
 * @Version: $
 */
import com.example.springsecuritydemo.entity.SysUser;
import com.example.springsecuritydemo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired  //业务服务类
    private IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        //SysUser对应数据库中的用户表，是最终存储用户和密码的表，可自定义
        //本例使用SysUser中的name作为用户名:
        SysUser user = userService.findByName(userName);
        if (user == null) {
            throw new UsernameNotFoundException("UserName " + userName + " not found");
        }
        // SecurityUser实现UserDetails并将SysUser的name映射为username
        SecurityUser seu = new SecurityUser(user);
        return  seu;
    }

}