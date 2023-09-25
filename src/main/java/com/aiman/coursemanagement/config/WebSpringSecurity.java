package com.aiman.coursemanagement.config;

import com.aiman.coursemanagement.entity.User;
import com.aiman.coursemanagement.model.Role;
import com.aiman.coursemanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSpringSecurity {
    private final UserDetailsService userDetailsService;

    private final UserService userService;

    @Autowired
    public WebSpringSecurity(UserService userService, UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        userService.configuraAdminUserIfNeeded();
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize ->
                        authorize.requestMatchers(
                                        new AntPathRequestMatcher("/lecturer/**")
                                ).hasAuthority(Role.Lecturer.name())
                                .requestMatchers(
                                        new AntPathRequestMatcher("/courses/**"),
                                        new AntPathRequestMatcher("/curriculums/**"),
                                        new AntPathRequestMatcher("/semesters/**"),
                                        new AntPathRequestMatcher("/lecturers/**"),
                                        new AntPathRequestMatcher("/admin"),
                                        new AntPathRequestMatcher("/admin/**")
                                )
                                .hasAuthority(Role.Admin.name())
                                .anyRequest().authenticated()
                )
                .formLogin(form -> form.loginPage("/login")
                        .loginProcessingUrl("/login")
                        .successHandler(new CustomAuthenticationSuccessHandler())
                        .permitAll())
                .logout(logout ->
                        logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                );
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(username -> {
            final User user = userService.getById(username);
            return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), true, true, true, true, user.getRoles());
        }).passwordEncoder(passwordEncoder());
    }
}
