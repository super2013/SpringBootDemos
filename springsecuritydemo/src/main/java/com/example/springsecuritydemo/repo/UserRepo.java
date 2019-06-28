package com.example.springsecuritydemo.repo;

import com.example.springsecuritydemo.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @ Author     ：lichao.
 * @ Date       ：Created in 10:04 2019/6/27
 * @ Description：
 * @ Modified By：
 * @Version: $
 */
@Repository
public interface UserRepo extends JpaRepository<SysUser,Integer>{
    SysUser findByName(String name);
}
