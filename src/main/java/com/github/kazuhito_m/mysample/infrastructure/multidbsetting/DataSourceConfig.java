package com.github.kazuhito_m.mysample.infrastructure.multidbsetting;

import com.github.kazuhito_m.mysample.domain.model.config.Config;
import com.github.kazuhito_m.mysample.domain.model.config.ConfigRepository;
import com.github.kazuhito_m.mysample.domain.model.config.Datasource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

/**
 * 複数データベースかつ接続情報を設定ファイル以外から取得するコンフィグ。
 */
@Configuration
public class DataSourceConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceConfig.class);

    private final ConfigRepository configRepository;
    private final ApplicationContext context;

    @Bean("dataSource")
    public DataSource dataSource() {
        Config config = configRepository.get();
        return buildDataSource(config.mainDatasource());
    }

    @Bean("logDataSource")
    public DataSource logDataSource() {
        Config config = configRepository.get();
        return buildDataSource(config.logDatasource());
    }

    private DataSource buildDataSource(Datasource dbConfig) {
        DataSourceBuilder builder = DataSourceBuilder.create();
        builder.driverClassName(dbConfig.driverClassName());
        builder.url(dbConfig.url());
        builder.username(dbConfig.username());
        builder.password(dbConfig.password());
        return builder.build();
    }

    @Bean("logDataSourceInitializer")
    public DataSourceInitializer logDataSourceInitializer(@Qualifier("logDataSource") DataSource dataSource) {
        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(dataSource);

        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        searchAndRegisterExecuteSql("schema-log.sql", "DBスキーマ初期化ファイル", populator);
        searchAndRegisterExecuteSql("data-log.sql", "DBデータ初期化ファイル", populator);
        dataSourceInitializer.setDatabasePopulator(populator);

        dataSourceInitializer.setEnabled(true);
        return dataSourceInitializer;
    }

    private void searchAndRegisterExecuteSql(String fileName, String description, ResourceDatabasePopulator populator) {
        Resource resource = context.getResource("classpath:" + fileName);
        if (resource != null && resource.exists()) populator.addScript(resource);

        String template = "%s:%s が見つかりませんでした。初期化は行いません。";
        LOGGER.debug(String.format(template, description, fileName));
    }

    public DataSourceConfig(ConfigRepository configRepository, ApplicationContext context) {
        this.configRepository = configRepository;
        this.context = context;
    }
}
