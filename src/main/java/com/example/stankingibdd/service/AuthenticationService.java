package com.example.stankingibdd.service;

import com.example.stankingibdd.entity.Client;
import com.example.stankingibdd.repository.ClientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService implements AuthenticationProvider {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String phone = authentication.getName();
        String password = authentication.getCredentials().toString();

        Client client = clientRepository.findByPhone(phone);

        if (Objects.isNull(client)) {
            final String errorMessage = "Не найден пользователь с телефоном " + phone;

            log.info(errorMessage);
            throw new BadCredentialsException(errorMessage);
        }

        if (!passwordEncoder.matches(password, client.getPassword())) {
            final String errorMessage = "Неверный пароль у пользователя " + phone;

            log.info(errorMessage);
            throw new BadCredentialsException(errorMessage);
        }

        return new UsernamePasswordAuthenticationToken(
                client, password, client.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
