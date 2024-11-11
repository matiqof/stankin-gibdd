package com.example.stankingibdd.service;

import com.example.stankingibdd.entity.Client;
import com.example.stankingibdd.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
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
            throw new UsernameNotFoundException("Не найден пользователь с телефоном " + phone);
        }

        return client;
    }
}