CREATE TABLE ride (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    from_address VARCHAR(256) NOT NULL,
    to_address VARCHAR(256) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    passenger_id UUID NOT NULL,
    driver_id UUID NOT NULL,
    ride_status INTEGER NOT NULL,
    created_at TIMESTAMP NOT NULL,
    accepted_at TIMESTAMP,
    started_at TIMESTAMP,
    finished_at TIMESTAMP
);