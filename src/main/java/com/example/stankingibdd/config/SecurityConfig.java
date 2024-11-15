package com.example.stankingibdd.config;

import com.example.stankingibdd.model.ClientRole;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final DataSource dataSource;

    private static final String ROLE_ADMIN = ClientRole.ROLE_ADMIN.toString().replace("ROLE_", "");

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/css/**").permitAll()
                        .requestMatchers("/js/**").permitAll()
                        .requestMatchers("/images/**").permitAll()
                        .requestMatchers("/forgot-password").permitAll()
                        .requestMatchers("/forgot-password-success").permitAll()
                        .requestMatchers("/reset-password").permitAll()
                        .requestMatchers("/reset-password-success").permitAll()
                        .requestMatchers("/profile-forgot-password").hasRole(ROLE_ADMIN)
                        .requestMatchers("/profile-forgot-password-success").hasRole(ROLE_ADMIN)
                        .requestMatchers("/profile-reset-password").hasRole(ROLE_ADMIN)
                        .requestMatchers("/profile-reset-password-success").hasRole(ROLE_ADMIN)
                        .requestMatchers("/register").hasRole(ROLE_ADMIN)
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                        .defaultSuccessUrl("/")
                        .failureUrl("/login?error")
                )
                .rememberMe(rememberMe ->
                        rememberMe
                                .alwaysRemember(true)
                                .key("uniqueAndSecret")
                                .tokenRepository(persistentTokenRepository())
                                .tokenValiditySeconds(86400)
                )
                .logout(LogoutConfigurer::permitAll)
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }
}