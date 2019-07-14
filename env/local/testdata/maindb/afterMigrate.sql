DELETE FROM profiles.profile_images;
DELETE FROM users.users;

INSERT INTO users.users
(user_id, name, phone_number, date_of_birth, gender)
VALUES
 ('hoge@example.com', 'テストユーザ01', '03-1234-5678','1977-08-17','男性'),
 ('fuga@example.com', 'テストユーザ02', '03-1234-5678','1977-08-17','女性'),
 ('buhi@example.com', 'テストユーザ03', '03-1234-5678','1977-08-17','不明'),
 ('ahei@example.com', 'テストユーザ04', '03-1234-5678','1977-08-17','男性');