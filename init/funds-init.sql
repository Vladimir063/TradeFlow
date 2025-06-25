CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE TABLE account
(
    user_id   UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    available_money INTEGER,
    reserved  INTEGER,
    last_update timestamp
);

insert into public.account
(user_id, available_money, reserved, last_update)
values ('1b2bdb22-9fd3-4d23-bcd0-c8e8da4f33e5', 40000, 0, TIMESTAMP '2011-05-16 15:36:38');
