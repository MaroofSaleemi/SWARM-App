package com.example.FactoryApp.AppUser;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface AppUserService extends UserDetailsService {
     AppUser signUpUser(AppUser appUser);

     String enableAppUser(String email);

     AppUser getAppUserByPhoneNumber(String email);


}
