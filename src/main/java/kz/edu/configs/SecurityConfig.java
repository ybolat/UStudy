package kz.edu.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    @Autowired
    @Qualifier("userDetailsService")
    UserDetailsService userDetailsService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception
    {
        security.authorizeRequests()
                .antMatchers("/", "/registration").permitAll()
                .antMatchers("/home").permitAll()
                .antMatchers("/myRequests").hasAnyRole("USER", "ADMIN")
                .antMatchers("/centers").hasRole("ADMIN")
                .antMatchers("/center").hasRole("ADMIN")
                .antMatchers("/createCenter").hasRole("ADMIN")
                .antMatchers("/createRequest").hasAnyRole("USER", "ADMIN")
                .antMatchers("/createRequestCenter").hasAnyRole("USER", "ADMIN")
                .antMatchers("/editRequestCenters").hasRole("ADMIN")
                .antMatchers("/editRequest").hasRole("ADMIN")
                .antMatchers("/requestsOfStatus").hasRole("ADMIN")
                .and()
                .formLogin().loginPage("/login").permitAll()
                .and()
                .logout().permitAll()
                .and()
                .csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }
}