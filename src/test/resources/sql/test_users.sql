CREATE TABLE IF NOT EXISTS users (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       email VARCHAR(255) NOT NULL,
                       password VARCHAR (255) NOT NULL
);
-- CREATE TABLE IF NOT EXISTS USERS (
--                                     id INT AUTO_INCREMENT PRIMARY KEY,
--                                     NAME VARCHAR(255) NOT NULL,
--     ROLE VARCHAR(255) NOT NULL,
--     EMAIL VARCHAR(255) NOT NULL
--     );

-- INSERT INTO USERS(id, NAME, ROLE, EMAIL) VALUES ('1', 'Kirshi', 'developer', 'kirshi@example.org');
INSERT INTO users (id, email, password) VALUES (1, 'user_test@mail.ru', 'user_test');
INSERT INTO users (id, email, password) VALUES (2, 'admin_test@mail.ru', 'admin_test');