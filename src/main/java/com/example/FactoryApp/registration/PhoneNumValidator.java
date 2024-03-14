package com.example.FactoryApp.registration;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class PhoneNumValidator implements Predicate<String> {
    @Override
    public boolean test(String email) {
        return true;
    }
}
