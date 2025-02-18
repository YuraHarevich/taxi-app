CREATE TABLE passenger_queue (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    passenger_id UUID NOT NULL,
    is_proceed BOOLEAN DEFAULT FALSE,
    from_address VARCHAR(64) NOT NULL,
    to_address VARCHAR(64) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    proceed_at TIMESTAMP DEFAULT NULL
);
GO

CREATE INDEX idx_passenger_queue_is_proceed_created_at
ON passenger_queue (is_proceed, created_at);