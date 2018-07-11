package com.github.kazuhito_m.mysample.domain.model.user;

public class UserSummary {
    final UserIdentifier identifier;
    final Name name;
    final DateOfBirth dateOfBirth;

    public UserSummary(UserIdentifier identifier, Name name, DateOfBirth dateOfBirth) {
        this.identifier = identifier;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
    }

    public UserIdentifier identifier() {
        return identifier;
    }

    public Name name() {
        return name;
    }

    public Age age() { return dateOfBirth.age(); }
}
