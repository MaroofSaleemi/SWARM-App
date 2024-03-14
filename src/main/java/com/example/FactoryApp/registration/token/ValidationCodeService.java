package com.example.FactoryApp.registration.token;


import com.twilio.rest.api.v2010.account.MessageCreator;

public interface ValidationCodeService {
    String sendValidationCode(String receiverNumber);

}
