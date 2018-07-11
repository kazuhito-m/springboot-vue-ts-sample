package com.github.kazuhito_m.mysample.domain.model.user;

import javax.validation.constraints.NotNull;

public class Gender {
    @NotNull(message = "性別を選択してください。")
    GenderType value;

    public Gender(GenderType value) {
        this.value = value;
    }

    @Override
    public String toString() {
        if (value == null) return "";
        return value.name();
    }
}
