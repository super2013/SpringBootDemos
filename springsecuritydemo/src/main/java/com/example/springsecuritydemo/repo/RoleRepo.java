package com.example.springsecuritydemo.repo;

import com.example.springsecuritydemo.entity.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ Author     ：lichao.
 * @ Date       ：Created in 10:41 2019/6/27
 * @ Description：
 * @ Modified By：
 * @Version: $
 */
@Repository
public interface RoleRepo extends JpaRepository<SysRole,Integer> {

}
