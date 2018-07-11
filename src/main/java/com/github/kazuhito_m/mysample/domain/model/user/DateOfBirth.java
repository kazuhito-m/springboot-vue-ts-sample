package com.github.kazuhito_m.mysample.domain.model.user;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class DateOfBirth {

    @NotNull(message = "誕生日を入力してください。")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate value;

    public DateOfBirth() {
    }

    public DateOfBirth(LocalDate value) {
        this.value = value;
    }

    public LocalDate value() {
        return this.value;
    }

    Age age() {
        return new Age(LocalDate.now().getYear() - value.getYear());
    }

    @Override
    public String toString() {
        if (value == null) return "";
        return value.toString();
    }
}
