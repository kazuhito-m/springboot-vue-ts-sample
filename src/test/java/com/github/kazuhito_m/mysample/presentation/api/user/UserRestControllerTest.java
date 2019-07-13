package com.github.kazuhito_m.mysample.presentation.api.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class UserRestControllerTest {
    static final Logger LOGGER = LoggerFactory.getLogger(UserRestControllerTest.class);

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    JdbcTemplate jdbcTemplate;

    MockMvc mvc;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @Sql("classpath:clear-transaction-data.sql")
    @Sql("add-initial-users.sql")
    public void ユーザ情報の一覧を取得できる() throws Exception {
        mvc.perform(get("/api/user"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].userIdentifier", is("x01.one@example.com")))
                .andExpect(jsonPath("$[0].name", is("勇座 一人")))
                .andExpect(jsonPath("$[0].age", is(31)))
                .andExpect(jsonPath("$[2].userIdentifier", is("x03.three@example.com")))
                .andExpect(jsonPath("$[2].name", is("勇座 酸忍")))
                .andExpect(jsonPath("$[2].age", is(219)));
    }

    @Test
    @Sql("classpath:clear-transaction-data.sql")
    public void ユーザの追加をJSONにて出来る() throws Exception {
        String userRegisterJson = loadTextOf("userRegister.json");
        mvc.perform(
                post("/api/user")
                        .content(userRegisterJson)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isCreated())
                .andReturn();

        var results = jdbcTemplate.queryForList("SELECT * FROM users.users");
        assertThat(results.size()).isEqualTo(1);
        var one = results.get(0);
        assertThat(one.get("user_id")).isEqualTo("xxxxxxxxxxxa.kazuhito.sumpic@example.com");
        assertThat(one.get("date_of_birth").toString()).isEqualTo("1977-08-17");
        assertThat(one.get("gender")).isEqualTo("女性");
    }

    @Test
    public void ユーザの追加でJSONに不備が在った場合404と理由を返す() throws Exception {
        String userRegisterJson = loadTextOf("userRegisterFail.json");
        MvcResult result = mvc.perform(
                post("/api/user")
                        .content(userRegisterJson)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json("{errorCause: \"名前は1文字以上40文字以内で入力してください。 [name:'三浦 一仁xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx']\"}"))
                .andReturn();

        LOGGER.info(result.getResponse().getContentAsString()); // 本当は要らない…がデバッグ時の例として。
    }

    private String loadTextOf(String name) throws URISyntaxException, IOException {
        URL url = getClass().getResource(name);
        byte[] bytes = Files.readAllBytes(Paths.get(url.toURI()));
        return new String(bytes, StandardCharsets.UTF_8);
    }
}