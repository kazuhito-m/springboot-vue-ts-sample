INSERT INTO operations.operation_histories
  (operation_history_id, operation_description, client_ip_address, request_path, parameters, created_at)
VALUES
  (nextval('operations.operation_histories_seq'), '履歴1', 'localhost', '/api/user', '', '1999-01-01 00:00:00'),
  (nextval('operations.operation_histories_seq'), '履歴2', '192.168.1.1', '/api/operationhistory', '', '2009-01-01 00:00:00'),
  (nextval('operations.operation_histories_seq'), '履歴3', '127.0.0.1', '/api/user/profile/image', 'userIdentifier=test@example.com', '2019-01-01 00:00:00')
