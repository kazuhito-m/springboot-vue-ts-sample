CREATE USER maindb_username WITH SUPERUSER PASSWORD 'maindb_password';
CREATE DATABASE maindb ENCODING 'UTF8' LC_COLLATE 'C' TEMPLATE 'template0' OWNER 'maindb_username';
ALTER DATABASE maindb SET timezone TO 'Asia/Tokyo';

CREATE USER logdb_username WITH SUPERUSER PASSWORD 'logdb_password';
CREATE DATABASE logdb ENCODING 'UTF8' LC_COLLATE 'C' TEMPLATE 'template0' OWNER 'logdb_username';
ALTER DATABASE logdb SET timezone TO 'Asia/Tokyo';

