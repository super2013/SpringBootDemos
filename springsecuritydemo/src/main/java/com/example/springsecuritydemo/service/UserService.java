package com.example.springsecuritydemo.service;

import com.example.springsecuritydemo.entity.SysUser;
import com.example.springsecuritydemo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ Author     ：lichao.
 * @ Date       ：Created in 9:59 2019/6/27
 * @ Description：
 * @ Modified By：
 * @Version: $
 */
@Service
public class UserService implements  IUserService{

    @Autowired
    UserRepo userRepo;

    @Override
    public SysUser findByName(String name) {
        return userRepo.findByName(name);
    }
}
