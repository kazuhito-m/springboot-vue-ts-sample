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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@Sql("classpath:clear-transaction-data.sql")
@Sql("add-initial-users.sql")
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

    @Test
    public void プロフィール画像のアップロード対象ユーザが無い場合404と理由を返す() throws Exception {
        byte[] sourceFileBytes = loadImageBytesOf("profile.png.binaly");
        MockMultipartFile profileImageFile = new MockMultipartFile("file", "test.png", "image/png", sourceFileBytes);

        mvc.perform(
                multipart("/api/user/profile/image")
                        .file(profileImageFile)
                        .param("userIdentifier", "only.one@example.comX")
        )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCause").value("指定されたデータは存在しません。"));
    }


    @Test
    public void プロフィール画像のアップロード時ファイルを指定しない無い場合400と理由を返す() throws Exception {
        mvc.perform(
                multipart("/api/user/profile/image")
                        .param("userIdentifier", "only.one@example.com")
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCause").value("プロファイル用の画像ファイルを指定してください。 [file:'null']"));
    }

    private byte[] loadImageBytesOf(String name) throws IOException {
        return getClass().getResourceAsStream(name).readAllBytes();
    }
}