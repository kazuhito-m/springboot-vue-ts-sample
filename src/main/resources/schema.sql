DROP SCHEMA IF EXISTS users;
CREATE SCHEMA users;

DROP TABLE IF EXISTS users.users;
CREATE TABLE users.users (
  user_id  VARCHAR(255) PRIMARY KEY,
  name VARCHAR(40) NOT NULL,
  phone_number VARCHAR(13) NOT NULL,
  date_of_birth DATE NOT NULL,
  gender CHAR(2) NOT NULL
);

COMMENT ON TABLE users.users IS '利用者';
COMMENT ON COLUMN users.users.user_id IS '利用者ID';
COMMENT ON COLUMN users.users.name IS '利用者名';
COMMENT ON COLUMN users.users.phone_number IS '電話番号';
COMMENT ON COLUMN users.users.date_of_birth IS '生年月日';
COMMENT ON COLUMN users.users.gender IS '性別';