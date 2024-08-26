package obi.com.eazyconcept.Security;


import obi.com.eazyconcept.Service.AdminUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.AbstractConfiguredSecurityBuilder;
import org.springframework.security.config.annotation.AbstractSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity
public class AppConfig {

    @Autowired
    AdminUserDetailService service;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(
                requestMatcherRegistry ->
                        requestMatcherRegistry.requestMatchers("/home", "/projects/**","/css/**","/img/**",
                                        "/projects","/favicon.ico","/send-request","/contact-us").permitAll()
                                .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN"))
                        .formLogin(httpSecurityFormLoginConfigurer ->
                                httpSecurityFormLoginConfigurer.loginPage("/login").permitAll()
                                        .defaultSuccessUrl("/admin/adminPortal"))
                .csrf(AbstractHttpConfigurer::disable);
        return httpSecurity.build();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return new AdminUserDetailService();
    }
    @Bean
    PasswordEncoder encoder (){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(encoder());
        return authenticationProvider;
    }
}

