package com.example.FactoryApp.registration.token;

import com.twilio.rest.api.v2010.account.MessageCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

@Service
public class ValidationCodeServiceImpl implements ValidationCodeService {


    public static final int VALIDATION_DIGITS = 100_000;
    //@Value("@{userBucket.path}")
    private static final String ACCOUNT_SID = "ACbf686ea6b30a9e07f6378ee61f5140e3";

    //@Value("@{userBucket.path}")
    private static final String AUTH_TOKEN = "d692af04e58ff9647096070dd685b2da";

    private static final String MESSAGING_SERVICE_SUFFIX = "whatsapp:%s";

    private static final String MESSAGE_BODY = "Your SWARM Login code is %s";

    private static final String SENDER_NUMBER = "+14155238886";

    public String sendValidationCode(String receiverNumber) {

        String validationCode = String.valueOf( Math.round(( Math.random() * VALIDATION_DIGITS)) );

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message.creator(
                new com.twilio.type.PhoneNumber(String.format(MESSAGING_SERVICE_SUFFIX, receiverNumber)),
                new com.twilio.type.PhoneNumber(String.format(MESSAGING_SERVICE_SUFFIX, SENDER_NUMBER)),
                String.format(MESSAGE_BODY, validationCode)).create();

        return validationCode;

    }

}
