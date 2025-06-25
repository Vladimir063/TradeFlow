CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE TABLE portfolio
(
    portfolio_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    company_id   UUID,
    user_id      UUID,
    available     int,
    reserved     int
);

insert into public.portfolio
(portfolio_id, company_id, user_id, available, reserved )
values ('0418dcc4-c66e-4f1a-89aa-a8d24685b61b', '8b0171a5-d0fa-4bae-b289-5a031dd2c554',
        '1b2bdb22-9fd3-4d23-bcd0-c8e8da4f33e5',  20000, 0);
