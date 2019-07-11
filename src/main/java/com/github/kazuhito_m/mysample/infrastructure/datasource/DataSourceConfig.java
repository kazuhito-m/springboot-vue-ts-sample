package com.github.kazuhito_m.mysample.infrastructure.datasource;

import com.github.kazuhito_m.mysample.domain.model.config.Config;
import com.github.kazuhito_m.mysample.domain.model.config.ConfigRepository;
import com.github.kazuhito_m.mysample.domain.model.config.Datasource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
    private final ConfigRepository configRepository;

    @Bean
    public DataSource getDataSource() {
        Config config = configRepository.get();
        Datasource main = config.mainDatasource();

        DataSourceBuilder builder = DataSourceBuilder.create();
        builder.driverClassName(main.driverClassName());
        builder.url(main.url());
        builder.username(main.name());
        builder.password(main.password());
        return builder.build();
    }

    public DataSourceConfig(ConfigRepository configRepository) {
        this.configRepository = configRepository;
    }
}
