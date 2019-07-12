package com.github.kazuhito_m.mysample.domain.model.config;

public class Datasource {
    private final String driverClassName;
    private final String url;
    private final String name;
    private final String password;

    public String driverClassName() {
        return driverClassName;
    }

    public String url() {
        return url;
    }

    public String username() {
        return name;
    }

    public String password() {
        return password;
    }

    public Datasource(String driverClassName, String url, String name, String password) {
        this.driverClassName = driverClassName;
        this.url = url;
        this.name = name;
        this.password = password;
    }
}
