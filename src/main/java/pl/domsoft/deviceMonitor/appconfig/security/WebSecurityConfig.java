package pl.domsoft.deviceMonitor.appconfig.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.domsoft.deviceMonitor.infrastructure.user.UserRole;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;

    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
            http
                    .cors().disable()
                    .csrf().disable()
//                    .and()
                .authorizeRequests()
                    .antMatchers("/*").permitAll()
                    .antMatchers("/assets/**").permitAll()
                    .antMatchers("/src/**").permitAll()
                    .antMatchers( "/api/deviceState").permitAll()
                    .antMatchers( "/api/admin/accounts/roles").permitAll()
                    .antMatchers( "/api/notifications/types").permitAll()
                    .antMatchers( "/api/notifications/empty/**").permitAll()
                    .antMatchers( "/api/actions/types").hasRole(UserRole.SERVICEMAN.labelWithoutPrefix())
                    .antMatchers( "/api/actions").hasRole(UserRole.SERVICEMAN.labelWithoutPrefix())
                    .antMatchers( "/api/admin/dev").hasRole(UserRole.USER.labelWithoutPrefix())
                    .antMatchers( "/api/admin/**").hasRole(UserRole.ADMIN.labelWithoutPrefix())
                    .antMatchers( "/api/**").authenticated()
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginPage("/loginuser")
                    .failureUrl("/src/index.html#!/login?loginError=true")
                    .permitAll()
                    .defaultSuccessUrl("/src/index.html", true)
                    .and()
                .logout()
                    .logoutUrl("/logout")
                    .permitAll();
    }

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    /**
     * Bean bezpiecznie hashujący hasła
     * @return BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}