package com.example.FactoryApp.AppUser;

import com.example.FactoryApp.ExceptionHandler.FactoryAppException;
import com.example.FactoryApp.ExceptionHandler.FactoryAppExceptionEnum;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final static String USER_NOT_FOUND_MSG =
            "user with email %s not found";

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        return getAppUserByPhoneNumber(phoneNumber);
    }

    public AppUser getAppUserByPhoneNumber(String phoneNumber) {
        return appUserRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, phoneNumber)));
    }

    public AppUser signUpUser(AppUser appUser) {

        AppUser thisUser = appUserRepository
                .findByPhoneNumber(appUser.getPhoneNumber()).orElseGet(() -> appUserRepository.
                        save(appUser.
                                withEncodedPassword(bCryptPasswordEncoder)));

        if(thisUser.getEnabled()) {
            throw new FactoryAppException(FactoryAppExceptionEnum.NUMBER_IN_USE);
        }

        return thisUser;

    }


    public String enableAppUser(String email) {
        AppUser user = this.getAppUserByPhoneNumber(email);
        user.setEnabled(Boolean.TRUE);
        appUserRepository.save(user);
        return HttpStatus.ACCEPTED.
                getReasonPhrase();
    }
}
