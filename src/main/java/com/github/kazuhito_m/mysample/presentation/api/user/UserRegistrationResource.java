package com.github.kazuhito_m.mysample.presentation.api.user;

import com.github.kazuhito_m.mysample.domain.model.user.*;
import org.hibernate.validator.constraints.Email;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class UserRegistrationResource {
    @NotNull(message = "メールアドレスを入力してください。")
    @Size(min = 1, message = "メールアドレスを入力してください。")
    @Email(message = "メールアドレスが正しくありません。")
    String userIdentifier;

    @NotNull(message = "名前を入力してください。")
    @Size(min = 1, max = 40, message = "名前は1文字以上40文字以内で入力してください。")
    String name;

    @NotNull(message = "誕生日を入力してください。")
    LocalDate dateOfBirth;

    @NotNull(message = "性別を選択してください。")
    GenderType gender;

    @NotNull(message = "電話番号を入力してください")
    @Pattern(regexp = "([0-9]{2,4}-[0-9]{2,4}-[0-9]{2,4})?", message = "xx-xxxx-xxxxの形式で入力してください")
    @Size(min = 8, max = 13, message = "桁数は8桁以上13桁以下で入力してください")
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
