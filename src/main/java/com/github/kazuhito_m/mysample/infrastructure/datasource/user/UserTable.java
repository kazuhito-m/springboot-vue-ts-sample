package com.github.kazuhito_m.mysample.infrastructure.datasource.user;

import com.github.kazuhito_m.mysample.domain.model.user.*;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

import java.time.LocalDate;

@Entity(immutable = true)
@Table(name = "users.users")
public class UserTable {
    @Id
    private final String userId;
    private final String name;
    private final LocalDate dateOfBirth;
    private final String phoneNumber;
    private final String gender;

    public User toUser() {
        return new User(
            new UserIdentifier(this.userId),
            new Name(this.name),
            new DateOfBirth(this.dateOfBirth),
            new Gender(GenderType.valueOf(this.gender)),
            new PhoneNumber(this.phoneNumber)
        );
    }
    
    public UserSummary toUserSummary() {
        return new UserSummary(
            new UserIdentifier(this.userId),
            new Name(this.name),
            new DateOfBirth(this.dateOfBirth)
        );
    }

    public UserTable(String userId, String name, LocalDate dateOfBirth, String phoneNumber, String gender) {
        this.userId = userId;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
    }

    public UserTable(User user) {
        this(
            user.identifier().value(),
            user.name().toString(),
            user.dateOfBirth().value(),
            user.phoneNumber().toString(),
            user.gender().toString()
        );
    }
}
