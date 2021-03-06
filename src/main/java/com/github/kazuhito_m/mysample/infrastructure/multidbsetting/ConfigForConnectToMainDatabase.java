package com.github.kazuhito_m.mysample.infrastructure.multidbsetting;

import org.seasar.doma.boot.autoconfigure.DomaProperties;
import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.Naming;
import org.seasar.doma.jdbc.dialect.Dialect;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * doma2で複数データベースへ接続する際のメインDBへの設定。
 */
@Component
@Primary
public class ConfigForConnectToMainDatabase implements Config {
    final DataSource dataSource;
    final Dialect dialect;
    final Naming naming;

    public ConfigForConnectToMainDatabase(@Qualifier("dataSource") DataSource originalMainDataSource,
                                          @Value("${doma.dialect}") String dialectName,
                                          @Value("${doma.naming}") String namingRuleName) {
        this.dataSource = new TransactionAwareDataSourceProxy(originalMainDataSource);
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
