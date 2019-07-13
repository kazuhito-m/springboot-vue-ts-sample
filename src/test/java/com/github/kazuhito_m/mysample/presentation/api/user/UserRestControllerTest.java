package com.github.kazuhito_m.mysample.presentation.api.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserRestControllerTest {
    @Autowired
    WebApplicationContext webApplicationContext;

    MockMvc mvc;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void ユーザ情報の一覧を取得できる() throws Exception {
        mvc.perform(get("/api/user"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].userIdentifier", is("fujimura_kaoru@example.com")))
                .andExpect(jsonPath("$[0].name", is("藤村 薫")))
                .andExpect(jsonPath("$[0].age", is(31)))
                .andExpect(jsonPath("$[5].userIdentifier", is("yamato_michiko@example.com")))
                .andExpect(jsonPath("$[5].name", is("大和 路子")))
                .andExpect(jsonPath("$[5].age", is(31)))
        ;
    }
}