package com.github.kazuhito_m.mysample.presentation.api.profile;

import com.github.kazuhito_m.mysample.domain.model.profile.ImageBinary;
import com.github.kazuhito_m.mysample.domain.model.profile.ProfileImage;
import com.github.kazuhito_m.mysample.domain.model.user.UserIdentifier;
import org.hibernate.validator.constraints.Email;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.IOException;

public class UserProfileImageResource {
    @NotNull(message = "メールアドレスを入力してください。")
    @Size(min = 1, message = "メールアドレスを入力してください。")
    @Email(message = "メールアドレスが正しくありません。")
    String userIdentifier;

    @NotNull(message = "プロファイル用の画像ファイルを指定してください。")
    MultipartFile file;

    public ProfileImage toProfileImage() {
        byte[] bytes = loadUploadFileBytes();
        return new ProfileImage(
                new UserIdentifier(userIdentifier),
                new ImageBinary(bytes)
        );
    }

    private byte[] loadUploadFileBytes() {
        try {
            return file.getBytes();
        } catch (IOException e) {
            throw new IllegalStateException("アップロードされてきたファイルのデータが読めなかった。", e);
        }
    }
}
