CREATE TABLE driver_queue (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    driver_id UUID NOT NULL,
    is_proceed BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    proceed_at TIMESTAMP DEFAULT NULL
);
GO

CREATE INDEX idx_driver_queue_is_proceed_created_at
ON driver_queue (is_proceed, created_at);
