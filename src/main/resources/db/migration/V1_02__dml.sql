INSERT INTO roles (name)
VALUES ('ROLE_USER'),
       ('ROLE_MODERATOR'),
       ('ROLE_ADMIN'),
       ('ROLE_SUPERADMIN');
INSERT INTO users ( first_name, last_name, nick_name, password, email)
VALUES ('Admin', 'Admin', 'admin', '100', 'admin@gmail.com');

INSERT INTO users_roles (user_id, role_id)
VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4);