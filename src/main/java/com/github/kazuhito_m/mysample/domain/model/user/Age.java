package com.github.kazuhito_m.mysample.domain.model.user;

public class Age {
    int value;

    Age(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

}
