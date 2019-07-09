package com.github.kazuhito_m.mysample.presentation.api.profile;

import org.hibernate.validator.constraints.Email;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserProfileImageResource {
    @NotNull(message = "メールアドレスを入力してください。")
    @Size(min = 1, message = "メールアドレスを入力してください。")
    @Email(message = "メールアドレスが正しくありません。")
    String userIdentifier;

    @NotNull(message = "プロファイル用の画像ファイルを指定してください。")
    MultipartFile file;
}
