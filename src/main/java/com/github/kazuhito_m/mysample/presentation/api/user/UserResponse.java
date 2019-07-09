package com.github.kazuhito_m.mysample.presentation.api.user;

import com.github.kazuhito_m.mysample.domain.model.user.UserSummary;

public class UserResponse {
    String userIdentifier;
    String name;
    int age;

    public UserResponse(UserSummary userSummary) {
        this.userIdentifier = userSummary.identifier().value();
        this.name = userSummary.name().toString();
        this.age = userSummary.age().value();
    }
}
