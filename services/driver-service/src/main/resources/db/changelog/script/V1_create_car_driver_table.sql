CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE IF NOT EXISTS driver_car (
    driver_id UUID REFERENCES drivers(id) ON DELETE CASCADE,
    car_id UUID REFERENCES cars(id) ON DELETE CASCADE,
    PRIMARY KEY (driver_id, car_id)
);