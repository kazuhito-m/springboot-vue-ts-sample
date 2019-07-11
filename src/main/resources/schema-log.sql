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
