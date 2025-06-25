CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE TABLE order_main (
                        order_id        UUID             PRIMARY KEY DEFAULT uuid_generate_v4(),
                        side            VARCHAR(50)      NOT NULL,                        -- BUY или SELL
                        quantity        INTEGER,                                        -- количество
                        price           INTEGER,                                        -- цена
                        created_date    TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,  -- время создания
                        execution_date  TIMESTAMP,                                        -- время исполнения
                        company_id      UUID,                                           -- ссылка на компанию
                        user_id         UUID ,
                        status varchar(255),
                        type varchar(255)
);