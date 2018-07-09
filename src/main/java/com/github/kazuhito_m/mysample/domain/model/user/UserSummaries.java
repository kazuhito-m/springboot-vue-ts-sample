package com.github.kazuhito_m.mysample.domain.model.user;

import java.util.Collections;
import java.util.List;

public class UserSummaries {
    final List<UserSummary> values;

    public UserSummaries(List<UserSummary> values) {
        this.values = values;
    }

    public List<UserSummary> list() {
        return Collections.unmodifiableList(values);
    }

    @Override
    public String toString() {
        return "UserSummaries{" +
                "values=" + values +
                '}';
    }
}
