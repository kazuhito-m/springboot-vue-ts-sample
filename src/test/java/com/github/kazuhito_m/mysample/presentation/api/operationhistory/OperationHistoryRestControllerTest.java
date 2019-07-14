package com.github.kazuhito_m.mysample.presentation.api.operationhistory;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @Test
    public void ユーザ情報の一覧を取得できる() throws Exception {
        mvc.perform(get("/api/user"))
                .andExpect(status().isOk());

        var results = logDBJdbcTemplate.queryForList("SELECT * FROM operations.operation_histories");
        assertThat(results.size()).isEqualTo(1);
        var one = results.get(0);
//        assertThat(one.get("user_id")).isEqualTo("xxxxxxxxxxxa.kazuhito.sumpic@example.com");
//        assertThat(one.get("date_of_birth").toString()).isEqualTo("1977-08-17");
//        assertThat(one.get("gender")).isEqualTo("女性");
    }

}