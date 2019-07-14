package com.github.kazuhito_m.mysample.presentation.api.operationhistory;


import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class OperationHistoryRestControllerTest {
    static final Logger LOGGER = LoggerFactory.getLogger(OperationHistoryRestControllerTest.class);

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    @Qualifier("logDataSource")
    DataSource logDataSource;

    JdbcTemplate logDBJdbcTemplate;

    MockMvc mvc;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        logDBJdbcTemplate = new JdbcTemplate(logDataSource);

        logDBJdbcTemplate.execute("DELETE FROM operations.operation_histories");
    }

    @Ignore("テスト時のみ、AspectJが仕事しないので期待結果にならない。テストはパス。")
    @Test
    public void 情報取得のAPIを実行した際に操作履歴が登録されることを確認() throws Exception {
        mvc.perform(get("/api/user"))
                .andExpect(status().isOk());

        var results = logDBJdbcTemplate.queryForList("SELECT * FROM operations.operation_histories");
        assertThat(results.size()).isEqualTo(1);
    }

    @Test
    public void 操作履歴をAPIで取得できる() throws Exception {
        logDBJdbcTemplate.execute(loadSqlOf("add-intial-oepration-history.sql"));

        mvc.perform(get("/api/operationhistory"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].description", is("履歴3")))
                .andExpect(jsonPath("$[0].clientIpAddress", is("127.0.0.1")))
                .andExpect(jsonPath("$[0].requestPath", is("/api/user/profile/image")))
                .andExpect(jsonPath("$[2].description", is("履歴1")))
                .andExpect(jsonPath("$[2].clientIpAddress", is("localhost")))
                .andExpect(jsonPath("$[2].requestPath", is("/api/user")));
    }

    private String loadSqlOf(String name) throws URISyntaxException, IOException {
        URL url = getClass().getResource(name);
        byte[] bytes = Files.readAllBytes(Paths.get(url.toURI()));
        return new String(bytes, StandardCharsets.UTF_8);
    }
}