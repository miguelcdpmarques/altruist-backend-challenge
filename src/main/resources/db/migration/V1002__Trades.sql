CREATE TYPE trade.side as ENUM ('BUY', 'SELL');

CREATE TYPE trade.status as ENUM ('SUBMITTED', 'CANCELLED', 'COMPLETED', 'FAILED');

CREATE TABLE IF NOT EXISTS trade.trade
(
    trade_uuid UUID NOT NULL DEFAULT uuid_generate_v4(),
    account_uuid UUID NOT NULL,
    symbol TEXT NOT NULL,
    side trade.side NOT NULL,
    status trade.status NOT NULL,
    quantity INTEGER NOT NULL,
    price NUMERIC(15,6) NOT NULL,
    created_date TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
    updated_date TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
    created_by TEXT,
    updated_by TEXT,
    PRIMARY KEY(trade_uuid),
    FOREIGN KEY(account_uuid) REFERENCES account(account_uuid)
);
