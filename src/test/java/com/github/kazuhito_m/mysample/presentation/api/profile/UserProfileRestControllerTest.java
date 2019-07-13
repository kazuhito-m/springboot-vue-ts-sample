package com.github.kazuhito_m.mysample.presentation.api.profile;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class UserProfileRestControllerTest {
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
    public void プロフィール画像がアップロード出来る() throws Exception {
        byte[] sourceFileBytes = loadImageBytesOf("profile.png.binaly");
        MockMultipartFile profileImageFile = new MockMultipartFile("file", "test.png", "image/png", sourceFileBytes);

        mvc.perform(
                multipart("/api/user/profile/image")
                        .file(profileImageFile)
                        .param("userIdentifier", "only.one@example.com")
        )
                .andExpect(status().isCreated())
                .andExpect(content().string(""))
                .andReturn();

        var results = jdbcTemplate.queryForList("SELECT * FROM profiles.profile_images");
        assertThat(results.size()).isEqualTo(1);
        var one = results.get(0);
        assertThat(one.get("user_id")).isEqualTo("only.one@example.com");
        byte[] uploadedBytes = (byte[]) one.get("image_binary");
        assertThat(uploadedBytes.length).isEqualTo(sourceFileBytes.length);
    }

    private byte[] loadImageBytesOf(String name) throws IOException {
        return getClass().getResourceAsStream(name).readAllBytes();
    }
}