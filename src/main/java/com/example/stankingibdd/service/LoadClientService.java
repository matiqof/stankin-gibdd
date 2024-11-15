package com.example.stankingibdd.service;

import com.example.stankingibdd.entity.Client;
import com.example.stankingibdd.exception.LoginException;
import com.example.stankingibdd.model.ClientRole;
import com.example.stankingibdd.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoadClientService implements UserDetailsService {

    private final ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        Client client = clientRepository.findByPhone(phone);
        if (Objects.isNull(client)) {
            final String errorMessage = "Не найден пользователь с телефоном " + phone;

            log.info(errorMessage);
            throw new UsernameNotFoundException(errorMessage);
        }

        if (!ClientRole.ROLE_ADMIN.equals(client.getRole())) {
            final String errorMessage = "Пользователь " + phone + " не является администратором";

            log.info(errorMessage);
            throw new LoginException(errorMessage);
        }

        return client;
    }
}