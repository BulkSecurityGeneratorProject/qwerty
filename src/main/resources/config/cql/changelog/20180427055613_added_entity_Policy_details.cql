
CREATE TABLE IF NOT EXISTS policy_details (
    id uuid,
    date date,
    scheme_id uuid,
    agent_id uuid,
    customer_name text,
    amount bigint,
    commision decimal,
    PRIMARY KEY(id)
);
