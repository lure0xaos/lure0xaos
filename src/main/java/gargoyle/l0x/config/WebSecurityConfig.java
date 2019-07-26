package gargoyle.l0x.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

import static gargoyle.l0x.data.Roles.ADMIN;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    public static final String PATH_ADMIN = "/admin";
    public static final String PATH_LOGIN = "/login";
    public static final String PATH_LOGOUT = "/logout";
    private final DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder passwordEncoder = passwordEncoder();
        auth
                .jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return super.userDetailsServiceBean();
    }

    //    @Override
//    public void configure(WebSecurity web) throws Exception {
//        super.configure(web);
//        web.debug(true);
//    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .headers()
                .frameOptions().sameOrigin()
                .and()
                .csrf()
                .requireCsrfProtectionMatcher(new AntPathRequestMatcher(PATH_ADMIN + "/**", HttpMethod.POST.name()))
                .and()
                .authorizeRequests()
                .antMatchers(PATH_ADMIN, PATH_ADMIN + "/**").hasRole(ADMIN)
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage(PATH_LOGIN)
                .successHandler(new SavedRequestAwareAuthenticationSuccessHandler())
                .permitAll()
                .and()
                .logout()
                .logoutUrl(PATH_LOGOUT)
                .logoutSuccessUrl("/")
                .permitAll()
                .and()
                .rememberMe()
                .alwaysRemember(true);
    }
}
