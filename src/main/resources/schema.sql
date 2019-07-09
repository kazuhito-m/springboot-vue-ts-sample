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

DROP SCHEMA IF EXISTS operations;
CREATE SCHEMA operations;

DROP TABLE IF EXISTS operations.operation_histories;
CREATE TABLE operations.operation_histories (
  operation_history_id BIGINT NOT NULL
    CONSTRAINT operation_histories_pkey PRIMARY KEY,
  operation_description VARCHAR(1024) NOT NULL,
  client_ip_address VARCHAR(16) NOT NULL,
  request_path VARCHAR(255) NOT NULL,
  parameters VARCHAR(512) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE SEQUENCE operations.operation_histories_seq;

DROP SCHEMA IF EXISTS profiles;
CREATE SCHEMA profiles;

DROP TABLE IF EXISTS profiles.profile_images;
CREATE TABLE profiles.profile_images (
  user_id VARCHAR(255) PRIMARY KEY,
  image_binary bytea NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users.users (user_id)
);
