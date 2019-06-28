package com.example.springsecuritydemo.repo;

import com.example.springsecuritydemo.entity.SysResource;
import com.example.springsecuritydemo.entity.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @ Author     ：lichao.
 * @ Date       ：Created in 11:43 2019/6/27
 * @ Description：
 * @ Modified By：
 * @Version: $
 */
public interface ResourceRepo extends JpaRepository<SysResource,Integer>{
        @Query(value = "SELECT * FROM s_resource  WHERE id IN ( SELECT resourceid FROM s_resource_role  WHERE roleid = ( SELECT  id  FROM s_role  WHERE name = ?1))",nativeQuery = true)
        List<SysResource> findByRoleName(String roleName);
}
