package com.vionox.tools.tvscraper.accounting.config;

import com.vionox.tools.tvscraper.accounting.service.MyUserDetailsService;
import com.vionox.tools.tvscraper.dao.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@EnableWebSecurity
@RequiredArgsConstructor
@Order(2)
public class SecurityConfig
{

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private AuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private LogoutSuccessHandler myLogoutSuccessHandler;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private final AuthenticationConfiguration authenticationConfiguration;
    @Autowired
    private final PasswordEncoder encoder;
    @Autowired
    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    private final AuthenticationFailureHandler authenticationFailureHandler;

    @Bean
    SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    private LoginUrlAuthenticationEntryPoint getEntryPoint()
    {
        final LoginUrlAuthenticationEntryPoint entryPoint = new LoginUrlAuthenticationEntryPoint("/login");
        entryPoint.setForceHttps(false);
        return entryPoint;
    }

    @Bean
    MyUserDetailsService customUserDetailsService() {
        return new MyUserDetailsService();
    }

}
