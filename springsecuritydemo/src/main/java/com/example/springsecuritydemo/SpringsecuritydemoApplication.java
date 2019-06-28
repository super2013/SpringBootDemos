package com.example.springsecuritydemo;

import com.example.springsecuritydemo.entity.SysRole;
import com.example.springsecuritydemo.entity.SysUser;
import com.example.springsecuritydemo.repo.RoleRepo;
import com.example.springsecuritydemo.repo.UserRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;

@SpringBootApplication
public class SpringsecuritydemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringsecuritydemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(UserRepo userRepo, RoleRepo roleRepo){
        return (args) -> {
            //初始化一个账号以及角色
           /* SysUser user = new SysUser();
            user.setName("lichao");
            BCryptPasswordEncoder bc=new BCryptPasswordEncoder(4);
            user.setPassword(bc.encode("lichao"));
            user.setDob(new Date());

            SysRole role = new SysRole();
            role.setName("ADMIN");
            role.setSUser(user);

            //user.getSysRoles().add(role);
            userRepo.save(user);
            roleRepo.save(role);*/
        };
    }

}
