package com.github.kazuhito_m.mysample.domain.model.user;

import javax.validation.Valid;

public class User {
    @Valid
    UserIdentifier identifier;

    @Valid
    Name name;

    @Valid
    DateOfBirth dateOfBirth;

    @Valid
    Gender gender;

    @Valid
    PhoneNumber phoneNumber;

    public User(
            UserIdentifier identifier,
            Name name,
            DateOfBirth dateOfBirth,
            Gender gender,
            PhoneNumber phoneNumber
    ) {
        this.identifier = identifier;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
    }

    public User() {
        identifier = new UserIdentifier();
        name = new Name("");
        dateOfBirth = new DateOfBirth();
        gender = new Gender(GenderType.不明);
        phoneNumber = new PhoneNumber();
    }

    public UserIdentifier identifier() {
        return identifier;
    }

    public Name name() {
        return name;
    }

    public DateOfBirth dateOfBirth() {
        return dateOfBirth;
    }

    public Gender gender() {
        return gender;
    }

    public PhoneNumber phoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return "User{" +
                "identifier=" + identifier +
                ", name=" + name +
                ", dateOfBirth=" + dateOfBirth +
                ", phoneNumber=" + phoneNumber +
                ", gender=" + gender +
                '}';
    }
}
