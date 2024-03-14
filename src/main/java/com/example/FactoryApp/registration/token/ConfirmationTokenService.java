package com.example.FactoryApp.registration.token;

import com.example.FactoryApp.AppUser.AppUser;
import com.example.FactoryApp.ExceptionHandler.FactoryAppException;
import com.example.FactoryApp.ExceptionHandler.FactoryAppExceptionEnum;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }

    public Optional<ConfirmationToken> getTokenByCode(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    public void setConfirmedAt(String token) {
        ConfirmationToken thisToken = confirmationTokenRepository.findByToken(token)
                .orElseThrow(() -> new FactoryAppException(FactoryAppExceptionEnum.TOKEN_NOT_FOUND));

        thisToken.setConfirmedAt(LocalDateTime.now());

        confirmationTokenRepository.save(thisToken);

    }

    public ConfirmationToken getTokenByUser(AppUser user) {
        ConfirmationToken token = confirmationTokenRepository.findByAppUser(user)
                .orElseThrow(() -> new FactoryAppException(FactoryAppExceptionEnum.TOKEN_NOT_FOUND));

        if (!Objects.isNull(token.getConfirmedAt())) {
            throw new FactoryAppException(FactoryAppExceptionEnum.ACCOUNT_ALREADY_CONFIRMED);
        }
        return token;
    }
}
