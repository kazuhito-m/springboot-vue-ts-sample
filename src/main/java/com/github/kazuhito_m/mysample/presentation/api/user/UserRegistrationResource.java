package com.github.kazuhito_m.mysample.presentation.api.user;

import com.github.kazuhito_m.mysample.domain.model.user.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

public class UserRegistrationResource {
    String userIdentifier;
    String name;
    LocalDate dateOfBirth;
    GenderType gender;
    String phoneNumber;

    public User toUser() {
        return new User(
                new UserIdentifier(userIdentifier),
                new Name(name),
                new DateOfBirth(dateOfBirth),
                new Gender(gender),
                new PhoneNumber(phoneNumber)
        );
    }
}
