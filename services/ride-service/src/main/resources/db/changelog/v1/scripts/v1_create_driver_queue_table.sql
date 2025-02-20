CREATE TABLE driver_queue (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    driver_id UUID NOT NULL,
    processing_status INTEGER NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
GO

CREATE INDEX idx_driver_queue_is_proceed_created_at
ON driver_queue (processing_status, created_at);
