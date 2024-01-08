-- CREATE TABLE IF NOT EXISTS users (
--                                      id INT AUTO_INCREMENT PRIMARY KEY,
--                                      email VARCHAR(255) NOT NULL,
--     password VARCHAR (255) NOT NULL
--     );
-- INSERT INTO users (id, email, password) VALUES (1, 'user_test@mail.ru', 'user_test');
-- INSERT INTO users (id, email, password) VALUES (2, 'admin_test@mail.ru', 'admin_test');

CREATE TABLE IF NOT EXISTS posts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id integer NOT NULL,
--     date timestamp,
    mess varchar(255)
    );
INSERT INTO posts (id, user_id, mess) VALUES (1, 1, 'mess1');
-- INSERT INTO posts (id, user_id, date, mess) VALUES (1, 1, 2023-12-11 20:52:52.294000, 'mess2');