package ru.lilitweb.books.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.lilitweb.books.repostory.UserRepository;
import ru.lilitweb.books.service.UserDetailsServiceImpl;
import ru.lilitweb.books.service.UserService;
import ru.lilitweb.books.service.UserServiceImpl;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/book/create").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/book").hasRole("ADMIN")
                .regexMatchers(HttpMethod.PUT, "/book/[A-Za-z0-9]+").hasRole("ADMIN")
                .regexMatchers(HttpMethod.DELETE, "/book/[A-Za-z0-9]+").hasRole("ADMIN")
                .regexMatchers("/book/create", "/book/[A-Za-z0-9]+/edit").hasRole("ADMIN")
                .regexMatchers(HttpMethod.GET, "/book/[A-Za-z0-9]+").authenticated()
                .antMatchers("/**").permitAll()
                .and()
                .formLogin()
                .loginPage("/signin")
                .failureUrl("/signin-error")
                .and()
                .rememberMe().key("springmvcsecutiry");
    }

    @Bean
    public UserService userService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        UserService userService = new UserServiceImpl(userRepository, passwordEncoder);
//        User user = userService.registerNewUserAccount(new UserDto(
//                "admin",
//                "admin",
//                "admin",
//                "admin4@gmail.com"));
//        userService.addRoleAdmin(user);
        return userService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService mongoUserDetails(UserRepository userRepository) {
        return new UserDetailsServiceImpl(userRepository);
    }

    @Autowired
    public void configAuthBuilder(AuthenticationManagerBuilder builder,
                                  @Qualifier("mongoUserDetails") UserDetailsService userDetailsService) throws Exception {
        builder.userDetailsService(userDetailsService);
    }
}
