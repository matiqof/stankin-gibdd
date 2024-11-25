package com.example.stankingibdd.service;

import com.example.stankingibdd.entity.Client;
import com.example.stankingibdd.entity.PasswordResetToken;
import com.example.stankingibdd.exception.EditTablesException;
import com.example.stankingibdd.exception.ForgotPasswordException;
import com.example.stankingibdd.exception.ResetPasswordException;
import com.example.stankingibdd.mapper.ClientMapper;
import com.example.stankingibdd.model.ClientDto;
import com.example.stankingibdd.model.ForgotPasswordRequest;
import com.example.stankingibdd.model.ResetPasswordRequest;
import com.example.stankingibdd.repository.ClientRepository;
import com.example.stankingibdd.repository.PasswordResetTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    public void forgotPassword(ForgotPasswordRequest request, boolean isFromProfile) {
        if (Objects.isNull(request)) {
            final String errorMessage = "Невозможно восстановить пароль так как запрос пустой";
            throw new ForgotPasswordException(errorMessage);
        }

        String currentClientPhone = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        String phone = request.getPhone();
        if (isFromProfile && !currentClientPhone.equals(phone)) {
            final String errorMessage = "Вы ввели телефон, не соответствующий вашему номеру телефона";
            throw new ForgotPasswordException(errorMessage);
        }

        Client client = clientRepository.findByPhone(phone);
        if (Objects.isNull(client)) {
            final String errorMessage = "Клиент с телефоном " + phone +  " не существует";
            throw new ForgotPasswordException(errorMessage);
        }

        List<PasswordResetToken> passwordResetTokens = passwordResetTokenRepository.findByPhone(phone);
        passwordResetTokenRepository.deleteAll(passwordResetTokens);

        UUID token = UUID.randomUUID();
        PasswordResetToken passwordResetToken = PasswordResetToken.builder()
                .token(token)
                .client(client)
                .expiryDate(calculateExpiryDate())
                .build();
        passwordResetTokenRepository.save(passwordResetToken);

        log.info("На этом этапе ссылка для восстановления должна быть отправлена на телефон, но это тестовый стенд, данная функция недоступна, сгенерированный токен " + token + " для продолжения перейдите по url " + (isFromProfile ? "http://127.0.0.1:8080/profile-reset-password?token=" : "http://127.0.0.1:8080/reset-password?token=") + token);
    }

    @Override
    public void resetPassword(ResetPasswordRequest request) {
        if (Objects.isNull(request)) {
            final String errorMessage = "Невозможно восстановить пароль, когда пришел пустой запрос";
            throw new ResetPasswordException(errorMessage);
        }

        UUID token = UUID.fromString(request.getToken());
        String newPassword = request.getNewPassword();
        String confirmPassword = request.getConfirmPassword();

        if (!newPassword.equals(confirmPassword)) {
            final String errorMessage = "Пароли не совпадают";
            throw new ResetPasswordException(errorMessage);
        }

        if (!validatePasswordResetToken(token)) {
            final String errorMessage = "Пришел некорректный токен";
            throw new ResetPasswordException(errorMessage);
        }

        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token);
        Client client = resetToken.getClient();
        if (Objects.isNull(client)) {
            final String errorMessage = "Нет клиента для сброса пароля";
            throw new ResetPasswordException(errorMessage);
        }

        client.setClientPassword(passwordEncoder.encode(newPassword));

        clientRepository.save(client);
        passwordResetTokenRepository.delete(resetToken);
    }

    @Override
    public void saveClient(ClientDto clientDto) {
        if (Objects.isNull(clientDto)) {
            final String errorMessage = "Невозможно сохранить изменения пустого клиента";
            throw new EditTablesException(errorMessage);
        }

        String phone = clientDto.getPhone();
        if (!StringUtils.hasLength(phone)) {
            final String errorMessage = "Невозможно сохранить изменения клиента с пустым телефоном";
            throw new EditTablesException(errorMessage);
        }

        Client clientFromDb = clientRepository.findByPhone(phone);
        String passport = clientDto.getPassport();
        if (!clientFromDb.getPassport().equals(passport)
                && clientRepository.existsClientByPassport(passport)) {
            final String errorMessage = "Клиент с паспортом " + passport +  " уже существует";
            throw new EditTablesException(errorMessage);
        }

        Client client = clientMapper.map(clientFromDb, clientMapper.map(clientDto));
        if (phone.equals(clientDto.getPhone())) {
            updateSecurityContextClient(client);
        }

        clientRepository.save(client);
    }

    private boolean validatePasswordResetToken(UUID token) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token);
        return Objects.nonNull(resetToken) && !resetToken.isExpired();
    }

    private Date calculateExpiryDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, EXPIRATION);
        return new Date(cal.getTime().getTime());
    }

    private void updateSecurityContextClient(Client client) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Client originalClient) {
            originalClient.setFullName(client.getFullName());
            originalClient.setDateOfBirth(client.getDateOfBirth());
            originalClient.setAddress(client.getAddress());
            originalClient.setPassport(client.getPassport());
            originalClient.setPassportIssueDate(client.getPassportIssueDate());
            originalClient.setPassportDepartmentCode(client.getPassportDepartmentCode());
        }
    }
}
