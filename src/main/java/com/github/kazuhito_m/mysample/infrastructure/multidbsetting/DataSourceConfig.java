package com.github.kazuhito_m.mysample.infrastructure.multidbsetting;

import com.github.kazuhito_m.mysample.domain.model.config.Config;
import com.github.kazuhito_m.mysample.domain.model.config.ConfigRepository;
import com.github.kazuhito_m.mysample.domain.model.config.Datasource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * 複数データベースかつ接続情報を設定ファイル以外から取得するコンフィグ。
 */
@Configuration
public class DataSourceConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceConfig.class);

    private final ConfigRepository configRepository;
    private final ApplicationContext context;

    private final String startupSqlPaths;

    @Bean("dataSource")
    @Primary
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

    @Bean("dataSourceInitializer")
    @Primary
    public DataSourceInitializer dataSourceInitializer(@Qualifier("dataSource") DataSource dataSource) {
        List<String> sqlFilePaths = sqlPaths();
        if (sqlFilePaths.isEmpty()) return null;

        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(dataSource);

        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        for (String path : sqlFilePaths) searchAndRegisterExecuteSql(path, "MainDBデータ初期化ファイル(オプション)", populator);
        dataSourceInitializer.setDatabasePopulator(populator);

        dataSourceInitializer.setEnabled(true);
        return dataSourceInitializer;
    }

    private void searchAndRegisterExecuteSql(String path, String description, ResourceDatabasePopulator populator) {
        Resource resource = searchFile(path);
        if (resource == null || !resource.exists()) {
            String template = "%s:%s が設定されていますが見つかりませんでした。SQL実行は行いません。";
            LOGGER.info(String.format(template, description, path));
            return;
        }

        String template = "%s:%s が見つかりました。SQLを実行します。";
        LOGGER.info(String.format(template, description, resource));

        populator.addScript(resource);
    }

    private Resource searchFile(String path) {
        Resource resource = context.getResource(path);
        if (resource != null && resource.exists()) return resource;

        resource = context.getResource("classpath:" + path);
        if (resource != null && resource.exists()) return resource;

        resource = new FileSystemResource(path);
        return resource;
    }


    private List<String> sqlPaths() {
        if (startupSqlPaths == null) return Collections.emptyList();
        return Stream.of(startupSqlPaths.split(","))
                .map(String::trim)
                .collect(toList());
    }

    @Bean("logDataSourceInitializer")
    public DataSourceInitializer logDataSourceInitializer(@Qualifier("logDataSource") DataSource dataSource) {
        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(dataSource);

        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        searchAndRegisterExecuteSqlOnClassPath("schema-log.sql", "DBスキーマ初期化ファイル", populator);
        searchAndRegisterExecuteSqlOnClassPath("data-log.sql", "DBデータ初期化ファイル", populator);
        dataSourceInitializer.setDatabasePopulator(populator);

        dataSourceInitializer.setEnabled(true);
        return dataSourceInitializer;
    }

    private void searchAndRegisterExecuteSqlOnClassPath(String fileName, String description, ResourceDatabasePopulator populator) {
        Resource resource = context.getResource("classpath:" + fileName);
        if (resource != null && resource.exists()) populator.addScript(resource);

        String template = "%s:%s が見つかりませんでした。初期化は行いません。";
        LOGGER.info(String.format(template, description, fileName));
    }

    public DataSourceConfig(ConfigRepository configRepository,
                            ApplicationContext context,
                            @Value("${maindb.startup.sql.paths}") String startupSqlPaths) {
        this.configRepository = configRepository;
        this.context = context;
        this.startupSqlPaths = startupSqlPaths;
    }
}
