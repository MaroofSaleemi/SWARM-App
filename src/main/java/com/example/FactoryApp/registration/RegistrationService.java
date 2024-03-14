package com.example.FactoryApp.registration;

import com.example.FactoryApp.AppUser.AppUser;
import org.springframework.http.ResponseEntity;

public interface RegistrationService {
    AppUser register(RegistrationRequest request);

    String confirmToken(String token);

    String requestToken(RegistrationRequest request);
}
