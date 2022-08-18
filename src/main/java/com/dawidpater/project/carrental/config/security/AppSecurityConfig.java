package com.dawidpater.project.carrental.config.security;

import com.dawidpater.project.carrental.service.RentalUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AppSecurityConfig extends WebSecurityConfigurerAdapter{

    private final PasswordEncoder passwordEncoder;
    private final RentalUserService rentalUserService;

    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new RentalAccessDeniedHandler();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String[] staticResources  =  {
                "/css/**",
                "/images/**",
                "/fonts/**",
                "/js/**",
        };

        //TODO: rest post methods not working. Error page etc
        http
                .headers().frameOptions().disable().and().csrf().ignoringAntMatchers("/h2-console/**").and()
                .authorizeRequests().antMatchers("/h2-console**").permitAll()
                .antMatchers(staticResources).permitAll()
                .antMatchers("/","/login**","/cars**","/api/cars**","/register","/api/registration/**","/rent**").permitAll()
                .antMatchers(HttpMethod.POST,"/rent**").permitAll()
                .antMatchers(HttpMethod.POST,"/register").permitAll()
                .antMatchers(HttpMethod.POST,"/api/registration/**").permitAll()
                .antMatchers(HttpMethod.POST,"/api/cars/**").permitAll()
                .antMatchers(HttpMethod.POST,"/logout").hasAnyRole("USER","ADMIN","MANAGER")
                //.antMatchers("/rent**").hasAnyRole("USER","ADMIN","MANAGER")
                .antMatchers("/management/**").hasAnyRole("ADMIN","MANAGER")
                .antMatchers("/management/admin/**").hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .passwordParameter("password")
                    .usernameParameter("username")
                .defaultSuccessUrl("/",true)
                .failureUrl("/login-error")
                .and()
                .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                .and()
                .exceptionHandling() // 1
                //.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .accessDeniedHandler(accessDeniedHandler());;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(rentalUserService);
        return provider;
    }
}
