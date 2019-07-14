package com.github.kazuhito_m.mysample.presentation.api.profile;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@Sql("classpath:clear-transaction-data.sql")
@Sql("add-initial-users.sql")
public class UserProfileRestControllerTest {
    static final Logger LOGGER = LoggerFactory.getLogger(UserProfileRestControllerTest.class);

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

        mvc.perform(
                multipart("/api/user/profile/image")
                        .file(new MockMultipartFile("file", "test.png", "image/png", sourceFileBytes))
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

        mvc.perform(
                multipart("/api/user/profile/image")
                        .file(new MockMultipartFile("file", "test.png", "image/png", sourceFileBytes))
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

    @Test
    public void プロフィール画像のダウンロードが出来る() throws Exception {
        byte[] sourceFileBytes = loadImageBytesOf("profile.png.binaly");
        insertProfileImageOf(sourceFileBytes, "only.one@example.com");

        mvc.perform(
                get("/api/user/profile/image")
                        .param("userIdentifier", "only.one@example.com")
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(content().bytes(sourceFileBytes));
    }

    @Test
    public void プロフィール画像のダウンロードでIDを間違えると404と理由を返す() throws Exception {
        // 事前にinsertしない…のでuserIdではダウンロードできないはず。
        mvc.perform(
                get("/api/user/profile/image")
                        .param("userIdentifier", "only.one@example.com")
        )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCause").value("指定されたデータは存在しません。"));
    }

    private void insertProfileImageOf(byte[] bytes, String userId) throws IOException {
        jdbcTemplate.update("INSERT INTO profiles.profile_images (user_id, image_binary) VALUES (?, ?)", userId, bytes);
        int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM profiles.profile_images", Integer.class);
        assertThat(count).isEqualTo(1);
    }

    private byte[] loadImageBytesOf(String name) throws IOException {
        return getClass().getResourceAsStream(name).readAllBytes();
    }
}