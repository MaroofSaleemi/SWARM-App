package com.example.FactoryApp.registration;

import com.example.FactoryApp.AppUser.AppUser;
import com.example.FactoryApp.AppUser.AppUserRole;
import com.example.FactoryApp.AppUser.AppUserService;
import com.example.FactoryApp.ExceptionHandler.FactoryAppException;
import com.example.FactoryApp.registration.token.ConfirmationToken;
import com.example.FactoryApp.registration.token.ConfirmationTokenService;
import com.example.FactoryApp.ExceptionHandler.FactoryAppExceptionEnum;
import com.example.FactoryApp.registration.token.ValidationCodeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
    private final AppUserService appUserService;
    private final PhoneNumValidator phoneNumValidator;
    private final ConfirmationTokenService confirmationTokenService;
    private final ValidationCodeService validationCodeService;

    public AppUser register(RegistrationRequest request) {
        boolean isEmailValid = phoneNumValidator.
                test(request.getPhoneNum());
        if (!isEmailValid) {
            throw new FactoryAppException(FactoryAppExceptionEnum.EMAIL_NOT_VALID);
        }
        AppUser createdUser = appUserService.signUpUser(
                    new AppUser(
                            request.getFirstName(),
                            request.getLastName(),
                            request.getPhoneNum(),
                            request.getPassword(),
                            AppUserRole.valueOf(request.getUserRole())
                    )
            );


        String validationCode = validationCodeService.sendValidationCode(createdUser.getPhoneNumber());

        confirmationTokenService.
                saveConfirmationToken(new ConfirmationToken(
                        validationCode,
                        LocalDateTime.now(),
                        LocalDateTime.now().plusMinutes(2),
                        createdUser
                ));

        return createdUser;
    }

    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getTokenByCode(token)
                .orElseThrow(() ->
                new FactoryAppException(FactoryAppExceptionEnum.TOKEN_EXPIRED));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new FactoryAppException(FactoryAppExceptionEnum.ACCOUNT_ALREADY_CONFIRMED);
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new FactoryAppException(FactoryAppExceptionEnum.TOKEN_EXPIRED);
        }

        confirmationTokenService.setConfirmedAt(token);

        return appUserService.enableAppUser(
                confirmationToken.getAppUser().getPhoneNumber()
        );
    }

    @Override
    public String requestToken(RegistrationRequest request) {
        AppUser user = appUserService.getAppUserByPhoneNumber(request.getPhoneNum());

        ConfirmationToken token = confirmationTokenService.getTokenByUser(user);

        String validationCode = validationCodeService.sendValidationCode(user.getPhoneNumber());

        confirmationTokenService.
                saveConfirmationToken(token.withToken(validationCode));

        return HttpStatus.ACCEPTED.getReasonPhrase();

    }
}
