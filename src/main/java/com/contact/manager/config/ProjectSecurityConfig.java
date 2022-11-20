package com.contact.manager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig {


    /**
     * From Spring Security 5.7, the WebSecurityConfigurerAdapter is deprecated to encourage users
     * to move towards a component-based security configuration. It is recommended to create a bean
     * of type SecurityFilterChain for security related configurations.
     *
     * @param http
     * @return SecurityFilterChain
     * @throws Exception
     */

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .cors().disable()
                .authorizeHttpRequests(auth ->
                                auth.antMatchers("/home", "/", "/alumni/**").permitAll()
//                        .antMatchers("/md/**").hasAnyRole("MD")
//                        .antMatchers("/authority/**").hasAnyRole("MD", "DA", "IM", "AC", "DM")
//                        .antMatchers("/home/**").permitAll()
                )
                .formLogin()
                .loginPage("/auth/login")
                .defaultSuccessUrl("/")
//                .loginProcessingUrl("authority/all")
//                .defaultSuccessUrl("")
        ;
        return http.build();

    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
