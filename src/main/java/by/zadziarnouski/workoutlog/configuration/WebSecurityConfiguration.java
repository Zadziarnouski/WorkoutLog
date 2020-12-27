package by.zadziarnouski.workoutlog.configuration;//package by.zadziarnouski.workoutlog.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final MyUserDetailsService myUserDetailsService;

    @Autowired
    public WebSecurityConfiguration(MyUserDetailsService myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder encoder = new CustomPasswordEncoder();
        auth
                .userDetailsService(myUserDetailsService).passwordEncoder(encoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests().
                antMatchers("/", "/registration").permitAll()
                .anyRequest().hasAnyAuthority("ROLE_USER" , "ROLE_ADMIN").
                and()
                .formLogin().permitAll().successForwardUrl("/")
                .and()
                .logout().permitAll().logoutSuccessUrl("/")
                .and()
                .cors().disable()
                .csrf().disable()
                .httpBasic();
    }


}

