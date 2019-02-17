-- noinspection SqlNoDataSourceInspectionForFile

DROP TABLE IF EXISTS users;

CREATE TABLE users
(
  id       SERIAL PRIMARY KEY,
  login    VARCHAR(100) UNIQUE NOT NULL,
  password VARCHAR(100)        NOT NULL,
  is_admin boolean             NOT NULL
);

-- login with: user/password or admin/password
INSERT INTO users
VALUES (1, 'admin', '$2a$11$2QPdRL3U8OW1aoiIBp23A.5leb8GqDDknRx/jhAmdg6XBMQlVW9vi', true);

INSERT INTO users
VALUES (2, 'user', '$2a$11$2QPdRL3U8OW1aoiIBp23A.5leb8GqDDknRx/jhAmdg6XBMQlVW9vi', false);