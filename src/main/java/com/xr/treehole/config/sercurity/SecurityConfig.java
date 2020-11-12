package com.xr.treehole.config.sercurity;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // ban the cross origin function
        http.csrf().disable()

                // form login config
                .formLogin()
                .loginPage("/user/login")   // default login page
                .loginProcessingUrl("/user/login")  // default login url
                .defaultSuccessUrl("/index")    //default success url

                .and()

                // filter authorize requests
                .authorizeRequests()
                .antMatchers("/user/login").permitAll()
                .antMatchers("/user/register").permitAll()
                .anyRequest().authenticated();

    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/**");
    }
}
