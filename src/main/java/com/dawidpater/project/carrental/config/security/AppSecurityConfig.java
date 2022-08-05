package com.dawidpater.project.carrental.config.security;

import com.dawidpater.project.carrental.service.RentalUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.persistence.Basic;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AppSecurityConfig extends WebSecurityConfigurerAdapter{

    private final PasswordEncoder passwordEncoder;
    private final RentalUserService rentalUserService;

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

        http.
                authorizeRequests()
                .antMatchers(staticResources).permitAll()
                .antMatchers("/","/login","/cars","/api/cars","/h2-console")
                .permitAll()
                .antMatchers(HttpMethod.POST,"/logout").hasAnyRole("USER","ADMIN")
                .antMatchers("/register").hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .passwordParameter("password")
                    .usernameParameter("username")
                .defaultSuccessUrl("/",true)
                .and()
                .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID");
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
