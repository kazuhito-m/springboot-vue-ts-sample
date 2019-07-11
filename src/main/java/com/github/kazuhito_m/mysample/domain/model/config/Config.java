package com.github.kazuhito_m.mysample.domain.model.config;

/**
 * 外部設定ファイル(application.propertiesに書いてあるpathのファイル)の設定値。
 */
public class Config {
    Datasource mainDatasource;

    public Datasource mainDatasource() {
        return mainDatasource;
    }

    public Config(Datasource mainDatasource) {
        this.mainDatasource = mainDatasource;
    }
}
