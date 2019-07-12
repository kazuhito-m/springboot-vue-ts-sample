package com.github.kazuhito_m.mysample.domain.model.config;

/**
 * 外部設定ファイル(application.propertiesに書いてあるpathのファイル)の設定値。
 */
public class Config {
    Datasource mainDatasource;
    Datasource logDatasource;

    public Datasource mainDatasource() {
        return mainDatasource;
    }

    public Datasource logDatasource() {
        return logDatasource;
    }

    public Config(Datasource mainDatasource, Datasource logDatasource) {
        this.mainDatasource = mainDatasource;
        this.logDatasource = logDatasource;
    }
}
