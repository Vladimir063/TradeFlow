CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE TABLE IF NOT EXISTS users (
                                     user_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                     username        VARCHAR(100) UNIQUE NOT NULL,
                                     password        VARCHAR(100) NOT NULL,
                                     first_name VARCHAR(100) NOT NULL,
                                     last_name VARCHAR(100) NOT NULL,
                                     enabled BOOLEAN  NOT NULL
);

insert into public.users
(user_id, username, password, first_name, last_name, enabled)
values ('1b2bdb22-9fd3-4d23-bcd0-c8e8da4f33e5',  'petr', '2a$10$4cgaW25ZNtZFNQdy/izksuDcZ5ZIbKZJWHqM6PKhR0dORSPiDe6PW','Petr',
        'Petrov333', true);


CREATE TABLE roles (
                       id   UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                       name VARCHAR(50) UNIQUE NOT NULL
);

insert into public.roles
(id, name)
values ('1b2bdb22-9fd3-4d23-bcd0-c8e8da4f33e5', 'admin');

CREATE TABLE user_roles (
                            user_id UUID NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
                            role_id UUID NOT NULL REFERENCES roles(id) ON DELETE CASCADE,
                            PRIMARY KEY (user_id, role_id)
);

insert into public.user_roles
(user_id, role_id)
values ('1b2bdb22-9fd3-4d23-bcd0-c8e8da4f33e5', '1b2bdb22-9fd3-4d23-bcd0-c8e8da4f33e5');