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

    private static final String ROLE_ADMIN = replaceRolePrefix(ClientRole.ROLE_ADMIN);
    private static final String ROLE_OPERATOR = replaceRolePrefix(ClientRole.ROLE_OPERATOR);
    private static final String ROLE_INSPECTOR =  replaceRolePrefix(ClientRole.ROLE_INSPECTOR);
    private static final String ROLE_SYSTEM =  replaceRolePrefix(ClientRole.ROLE_SYSTEM);

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
                        .requestMatchers("/profile-forgot-password").hasAnyRole(ROLE_ADMIN, ROLE_OPERATOR, ROLE_INSPECTOR, ROLE_SYSTEM)
                        .requestMatchers("/profile-forgot-password-success").hasAnyRole(ROLE_ADMIN, ROLE_OPERATOR, ROLE_INSPECTOR, ROLE_SYSTEM)
                        .requestMatchers("/profile-reset-password").hasAnyRole(ROLE_ADMIN, ROLE_OPERATOR, ROLE_INSPECTOR, ROLE_SYSTEM)
                        .requestMatchers("/profile-reset-password-success").hasAnyRole(ROLE_ADMIN, ROLE_OPERATOR, ROLE_INSPECTOR, ROLE_SYSTEM)
                        .requestMatchers("/clients").hasRole(ROLE_ADMIN)
                        .requestMatchers("/driving-licenses").hasAnyRole(ROLE_ADMIN, ROLE_OPERATOR, ROLE_INSPECTOR)
                        .requestMatchers("/driving-license-categories").hasAnyRole(ROLE_ADMIN)
                        .requestMatchers("/vehicles").hasAnyRole(ROLE_ADMIN, ROLE_OPERATOR, ROLE_INSPECTOR, ROLE_SYSTEM)
                        .requestMatchers("/accidents").hasAnyRole(ROLE_ADMIN, ROLE_INSPECTOR)
                        .requestMatchers("/accident-compositions").hasAnyRole(ROLE_ADMIN, ROLE_INSPECTOR)
                        .requestMatchers("/fines").hasAnyRole(ROLE_ADMIN, ROLE_INSPECTOR, ROLE_SYSTEM)
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

    private static String replaceRolePrefix(ClientRole role) {
        return role.toString().replace("ROLE_", "");
    }
}