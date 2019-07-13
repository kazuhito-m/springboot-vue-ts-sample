package com.github.kazuhito_m.mysample.infrastructure.multidbsetting;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.ClassicConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.boot.autoconfigure.flyway.FlywayProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.stream.Collectors;

/**
 * 複数データベースかつ接続情報を設定ファイル以外から取得している場合のFlywayマイグレーションのコンフィグ。
 */
@Configuration
public class FlywayConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(FlywayConfig.class);

    @Bean(initMethod = "migrate")
    @FlywayDataSource
    @Primary
    public Flyway primaryFlyway(@Qualifier("maindbFlywayProperties") FlywayProperties properties,
                                @Qualifier("dataSource") DataSource dataSource) {
        return createFlywayOf(properties, dataSource, "MainDB");
    }

    @Bean(initMethod = "migrate")
    @FlywayDataSource
    public Flyway secondaryFlyway(@Qualifier("logdbFlywayProperties") FlywayProperties properties,
                                  @Qualifier("logDataSource") DataSource dataSource) {
        return createFlywayOf(properties, dataSource, "LogDB");
    }

    private Flyway createFlywayOf(FlywayProperties properties, DataSource dataSource, String description) {
        if (!properties.isEnabled()) {
            LOGGER.info(description + " 用のDBマイグレーション(Flyway)が設定により無効化されています。マイグレーションは行いません。");
            return null;
        }
        ClassicConfiguration conf = convertFlywayConfigurationOf(properties, dataSource);
        Flyway flyway = new Flyway(conf);
        LOGGER.info(description + " 用のDBマイグレーション(Flyway)設定を行いました。マイグレーションファイル群:" + properties.getLocations().stream().collect(Collectors.joining(",")));
        return flyway;
    }

    private ClassicConfiguration convertFlywayConfigurationOf(FlywayProperties properties, DataSource dataSource) {
        ClassicConfiguration conf = new ClassicConfiguration();
        conf.setDataSource(dataSource);
        conf.setEncoding(properties.getEncoding());
        conf.setLocationsAsStrings(properties.getLocations().toArray(new String[]{}));
        return conf;
    }

    @Bean("maindbFlywayProperties")
    @ConfigurationProperties(prefix = "maindb.flyway")
    public FlywayProperties maindbFlywayProperties() {
        return new FlywayProperties();
    }

    @Bean("logdbFlywayProperties")
    @ConfigurationProperties(prefix = "logdb.flyway")
    public FlywayProperties logdbFlywayProperties() {
        return new FlywayProperties();
    }
}
