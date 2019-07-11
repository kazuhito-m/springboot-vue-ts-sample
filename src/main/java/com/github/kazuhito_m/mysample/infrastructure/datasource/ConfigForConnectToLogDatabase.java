package com.github.kazuhito_m.mysample.infrastructure.datasource;

import org.seasar.doma.boot.autoconfigure.DomaProperties;
import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.Naming;
import org.seasar.doma.jdbc.dialect.Dialect;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class ConfigForConnectToLogDatabase implements Config {
    final DataSource dataSource;
    final Dialect dialect;
    final Naming naming;

    public ConfigForConnectToLogDatabase(@Qualifier("logDataSource") DataSource originalLogDataSource,
                                         @Value("${doma.dialect}") String dialectName,
                                         @Value("${doma.naming}") String namingRuleName) {
        this.dataSource = new TransactionAwareDataSourceProxy(originalLogDataSource);
        this.dialect = DomaProperties.DialectType.valueOf(dialectName).create();
        this.naming = DomaProperties.NamingType.valueOf(namingRuleName).naming();
    }

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }

    @Override
    public Dialect getDialect() {
        return dialect;
    }

    @Override
    public Naming getNaming() {
        return naming;
    }
}
