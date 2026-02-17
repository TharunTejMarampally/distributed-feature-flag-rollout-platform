DROP TABLE IF EXISTS flag;

CREATE TABLE IF NOT EXISTS flag(
    id UUID PRIMARY KEY,
    flag_name VARCHAR(255) NOT NULL,
    flag_configurations JSONB,
    roll_out_percentage DOUBLE PRECISION,
    enabled BOOLEAN,
    version BIGINT,
    created_on TIMESTAMP,
    updated_on TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255)
)