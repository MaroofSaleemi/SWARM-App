package com.example.FactoryApp.registration;

import com.example.FactoryApp.AppUser.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationServiceImpl registrationService;

    @PostMapping
    public AppUser register(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }

    @GetMapping(path = "resend")
    public String resend(@RequestBody RegistrationRequest request) {
        return registrationService.requestToken(request);
    }
}
