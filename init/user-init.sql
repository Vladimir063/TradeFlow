CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE TABLE IF NOT EXISTS users (
                                     user_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                     first_name VARCHAR(100) NOT NULL,
                                     last_name VARCHAR(100) NOT NULL,
                                     available_money int4 NOT NULL
);

insert into public.users
(user_id, first_name, last_name, available_money)
values ('1b2bdb22-9fd3-4d23-bcd0-c8e8da4f33e5', 'Petr',
        'Petrov',  40000);
