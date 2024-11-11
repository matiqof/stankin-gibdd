package com.example.stankingibdd.service;

import com.example.stankingibdd.entity.Client;
import com.example.stankingibdd.entity.PasswordResetToken;
import com.example.stankingibdd.exception.ForgotPasswordException;
import com.example.stankingibdd.exception.RegistrationException;
import com.example.stankingibdd.exception.ResetPasswordException;
import com.example.stankingibdd.mapper.ClientMapper;
import com.example.stankingibdd.model.ClientDto;
import com.example.stankingibdd.model.ForgotPasswordRequest;
import com.example.stankingibdd.model.ResetPasswordRequest;
import com.example.stankingibdd.repository.ClientRepository;
import com.example.stankingibdd.repository.PasswordResetTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final ClientMapper clientMapper;
    private final PasswordEncoder passwordEncoder;

    private static final int EXPIRATION = 60 * 24;

    @Override
    public void registerClient(ClientDto clientDto) {
        if (Objects.isNull(clientDto)) {
            final String errorMessage = "Невозможно зарегистрировать пустого клиента";

            log.info(errorMessage);
            throw new RegistrationException(errorMessage);
        }

        String phone = clientDto.getPhone();
        if (clientRepository.existsClientByPhone(phone)) {
            final String errorMessage = "Клиент с телефоном " + phone +  " уже зарегистрирован";

            log.info(errorMessage);
            throw new RegistrationException(errorMessage);
        }

        String passport = clientDto.getPassport();
        if (clientRepository.existsClientByPassport(passport)) {
            final String errorMessage = "Клиент с паспортом " + passport +  " уже зарегистрирован";

            log.info(errorMessage);
            throw new RegistrationException(errorMessage);
        }

        Client client = clientMapper.map(clientDto);
        client.setClientPassword(passwordEncoder.encode(clientDto.getClientPassword()));

        clientRepository.save(client);
    }

    @Override
    public void forgotPassword(ForgotPasswordRequest request) {
        if (Objects.isNull(request)) {
            final String errorMessage = "Невозможно восстановить пароль так как запрос пустой";

            log.info(errorMessage);
            throw new ForgotPasswordException(errorMessage);
        }

        String phone = request.getPhone();
        Client client = clientRepository.findByPhone(phone);
        if (Objects.isNull(client)) {
            final String errorMessage = "Клиент с телефоном " + phone +  " не существует";

            log.info(errorMessage);
            throw new ForgotPasswordException(errorMessage);
        }

        List<PasswordResetToken> passwordResetTokens = passwordResetTokenRepository.findByPhone(phone);
        passwordResetTokenRepository.deleteAll(passwordResetTokens);

        String token = UUID.randomUUID().toString();
        PasswordResetToken passwordResetToken = PasswordResetToken.builder()
                .token(token)
                .client(client)
                .expiryDate(calculateExpiryDate())
                .build();
        passwordResetTokenRepository.save(passwordResetToken);

        log.info("На этом этапе ссылка для восстановления должна быть отправлена на телефон, но это тестовый стенд, данная фнукция недоступна, сгенерированный токен " + token + " для продолжения перейдите по url " + "http://127.0.0.1:8080/reset-password?token=" + token);
    }

    @Override
    public void resetPassword(ResetPasswordRequest request) {
        if (Objects.isNull(request)) {
            final String errorMessage = "Невозможно восстановить пароль, когда пришел пустой запрос";

            log.info(errorMessage);
            throw new ResetPasswordException(errorMessage);
        }

        String token = request.getToken();
        String newPassword = request.getNewPassword();
        String confirmPassword = request.getConfirmPassword();

        if (!newPassword.equals(confirmPassword)) {
            final String errorMessage = "Пароли не совпадают";

            log.info(errorMessage);
            throw new ResetPasswordException(errorMessage);
        }

        if (!validatePasswordResetToken(token)) {
            final String errorMessage = "Пришел некорректный токен";

            log.info(errorMessage);
            throw new ResetPasswordException(errorMessage);
        }

        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token);
        Client client = resetToken.getClient();
        if (Objects.isNull(client)) {
            final String errorMessage = "Нет клиента для сброса пароля";

            log.info(errorMessage);
            throw new ResetPasswordException(errorMessage);
        }

        client.setClientPassword(passwordEncoder.encode(newPassword));

        clientRepository.save(client);
        passwordResetTokenRepository.delete(resetToken);
    }

    private boolean validatePasswordResetToken(String token) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token);
        return Objects.nonNull(resetToken) && !resetToken.isExpired();
    }

    private Date calculateExpiryDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, EXPIRATION);
        return new Date(cal.getTime().getTime());
    }
}
