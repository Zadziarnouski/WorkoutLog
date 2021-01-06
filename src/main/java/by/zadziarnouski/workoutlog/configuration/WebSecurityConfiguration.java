package by.zadziarnouski.workoutlog.configuration;//package by.zadziarnouski.workoutlog.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Basic;


@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final MyUserDetailsService myUserDetailsService;

    @Autowired
    public WebSecurityConfiguration(MyUserDetailsService myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        auth
                .userDetailsService(myUserDetailsService).passwordEncoder(encoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/user-log/**").hasRole("ADMIN")
                .antMatchers("/login*", "/registration*").permitAll()
                .anyRequest().authenticated()

                .and()

                .formLogin().loginPage("/login").loginProcessingUrl("/perform_login")
                .defaultSuccessUrl("/menu", true)
                .failureUrl("/login?error=true")
//                .failureHandler(authenticationFailureHandler())

                .and()

                .logout().logoutUrl("/perform_logout").deleteCookies("JSESSIONID")
//                .logoutSuccessHandler(logoutSuccessHandler())

                .and()

//                .cors().disable()
//                .csrf().disable()
                .httpBasic();
    }


}

