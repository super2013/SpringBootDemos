package com.example.springsecuritydemo.service;

import com.example.springsecuritydemo.entity.SysUser;
import org.springframework.stereotype.Service;

/**
 * @ Author     ：lichao.
 * @ Date       ：Created in 10:09 2019/6/27
 * @ Description：
 * @ Modified By：
 * @Version: $
 */

public interface IUserService {
    SysUser findByName(String name);
}
