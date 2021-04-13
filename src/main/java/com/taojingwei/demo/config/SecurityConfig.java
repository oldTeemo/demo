package com.taojingwei.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    }

    @Override
    protected UserDetailsService userDetailsService() {
        return super.userDetailsService();
    }

    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return super.userDetailsServiceBean();
    }
    

}
