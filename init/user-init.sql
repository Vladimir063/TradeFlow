CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE TABLE IF NOT EXISTS users (
                                     user_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                     username        VARCHAR(100) UNIQUE NOT NULL,
                                     password        VARCHAR(100) NOT NULL,
                                     email           VARCHAR(100) NOT NULL,
                                     role            VARCHAR(100) NOT NULL

);

insert into public.users
(user_id, username, password, email, role)
values ('1b2bdb22-9fd3-4d23-bcd0-c8e8da4f33e5',  'petr', '$2a$10$pn3Gl.CWd4FLYqvqvvj5funMPYK9zp0WR/GOHRv7zC8erCCj3AZMy','petr@mail.ru', 'ROLE_ADMIN');

