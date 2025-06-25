CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE TABLE company_main
(
    company_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name       VARCHAR(50) NOT NULL,
    bid        INTEGER,
    ask        INTEGER

);
ALTER TABLE company_main
    ADD CONSTRAINT unique_company_name UNIQUE (name);

insert into public.company_main
(company_id, name, bid, ask)
values ('8b0171a5-d0fa-4bae-b289-5a031dd2c554', 'SBER', 550,  560);
