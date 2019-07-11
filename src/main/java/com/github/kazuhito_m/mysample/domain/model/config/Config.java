package com.github.kazuhito_m.mysample.domain.model.config;


public class Config {
    Datasource mainDatasource;

    public Datasource mainDatasource() {
        return mainDatasource;
    }

    public Config(Datasource mainDatasource) {
        this.mainDatasource = mainDatasource;
    }
}
