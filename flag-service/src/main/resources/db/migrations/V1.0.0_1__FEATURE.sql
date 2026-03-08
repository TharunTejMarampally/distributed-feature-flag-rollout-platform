DROP TABLE IF EXISTS flag;

CREATE TABLE IF NOT EXISTS flag(
    id UUID PRIMARY KEY,
    flag_id VARCHAR(255) NOT NULL,
    flag_info JSONB,
    created_on TIMESTAMP,
    updated_on TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255)
)