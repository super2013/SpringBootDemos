package com.example.springsecuritydemo.config;


import com.example.springsecuritydemo.support.CustomAccessDecisionManager;
import com.example.springsecuritydemo.support.CustomInvocationSecurityMetadataSourceService;
import com.example.springsecuritydemo.support.CustomUserDetailsService;
import com.example.springsecuritydemo.support.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private CustomUserDetailsService customUserDetailsService;

//    @Autowired
//    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class)//在正确的位置添加我们自定义的过滤器
                // 重新添加拥有自己属性的过滤器;
                /**
                 *  不使用自定义过滤器类的话，可以直接使用默认实现的类，并提供自定义的属性
                 */
                .addFilter(filterSecurityInterceptor())
                .authorizeRequests()
                //路径/home不需要验证
                .antMatchers("/home").permitAll()
                //任何请求都需要授权
                .anyRequest().authenticated()
                // .expressionHandler(new DefaultWebSecurityExpressionHandler())作用是什么?
                .and()
                .formLogin()
                .loginPage("/login")//之所以加true 是因为 th:if{param.error} 会去读取浏览器地址携带的参数，有了true之后，if就成立，所以后面的th:text就能执行。
                .permitAll()
                //登录成功处理
                .successHandler(loginSuccessHandler())
                .and()
                .logout()
                .permitAll()
                //注销后使session相关信息无效
                .invalidateHttpSession(true)
                .and()
                // 开启rememberme功能：验证，登录成功后，关闭页面，直接访问登陆后可以访问的页面
                .rememberMe()
                //持久化到数据库 如果不需要持久化到数据库，直接注释掉即可
                .rememberMeServices(new PersistentTokenBasedRememberMeServices("MySpringSecurityCookie",customUserDetailsService,persistentTokenRepository()))
                //设置有效时间
                .tokenValiditySeconds(7*24*60*60);
        //权限不足处理
        http.exceptionHandling().accessDeniedHandler(new AccessDeniedHandlerImpl()).accessDeniedPage("/deny");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    /**
     *   最新版本的 2.1.6.RELEASE使用configure  或者 configureGlobal 都可以
     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        //指定密码加密所使用的加密器为passwordEncoder()
//        //需要将密码加密后写入数据库
//        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
//        //不删除凭据，以便记住用户
//        auth.eraseCredentials(false);
//    }

    /**
     *  这种方式注入，会使用AuthenticationManagerBuilder新建一个AuthenticationManager对象，这就和下面
     *      @Autowired
    private AuthenticationManager authenticationManager;
    产生冲突导致报错，所以尽量使用上面的方式引入

     * @return
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //指定密码加密所使用的加密器为passwordEncoder()
        //需要将密码加密后写入数据库
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
        //不删除凭据，以便记住用户
        auth.eraseCredentials(false);

    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(4);
    }

    @Bean
    public LoginSuccessHandler loginSuccessHandler(){
        return new LoginSuccessHandler();
    }

    @Autowired
    private DataSource dataSource;

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        // 设置数据源 默认使用的Apache的连接池
        jdbcTokenRepository.setDataSource(dataSource);
        // 设置初始化存储Token的表  方便调试 由于源码没有对数据库中是否有表结构做出判断，正常使用的时候不建议开启，不然第二次启动会报错！
//        jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }

    @Autowired
    private CustomInvocationSecurityMetadataSourceService mySecurityMetadataSource;

    @Autowired
    private CustomAccessDecisionManager myAccessDecisionManager;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Bean
    public FilterSecurityInterceptor filterSecurityInterceptor(){
        FilterSecurityInterceptor filterSecurityInterceptor = new FilterSecurityInterceptor();
        filterSecurityInterceptor.setSecurityMetadataSource(mySecurityMetadataSource);
        filterSecurityInterceptor.setAccessDecisionManager(myAccessDecisionManager);
        filterSecurityInterceptor.setAuthenticationManager(authenticationManager);
        return filterSecurityInterceptor;
    }
}
