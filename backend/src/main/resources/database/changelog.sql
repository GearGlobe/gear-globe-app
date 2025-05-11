--liquibase formatted sql

--changeset cookie9161:1
CREATE TABLE IF NOT EXISTS address (
id BIGSERIAL PRIMARY KEY,
city VARCHAR(255) NOT NULL,
street VARCHAR(255) NOT NULL,
house_number VARCHAR(255) NOT NULL,
apartment_number VARCHAR(255),
country VARCHAR(255) NOT NULL
);

--changeset cookie9161:2
CREATE TABLE IF NOT EXISTS client (
id BIGSERIAL PRIMARY KEY,
name VARCHAR(255) NOT NULL,
last_name VARCHAR(255),
client_type VARCHAR(255) NOT NULL,
birth_date DATE,
email VARCHAR(255) NOT NULL,
phone_number VARCHAR(255) NOT NULL,
password VARCHAR(255) NOT NULL,
role VARCHAR(255) NOT NULL,
created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
modified_date TIMESTAMP WITHOUT TIME ZONE,
status VARCHAR(255) NOT NULL,
address_id BIGINT,
FOREIGN KEY (address_id) REFERENCES address(id)
);

--changeset cookie9161:3
CREATE TABLE IF NOT EXISTS offer (
id BIGSERIAL PRIMARY KEY,
mark VARCHAR(255) NOT NULL,
production_year BIGINT NOT NULL,
millage BIGINT NOT NULL,
engine_capacity DOUBLE PRECISION NOT NULL,
description VARCHAR(255),
price DOUBLE PRECISION NOT NULL,
create_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
modified_date TIMESTAMP WITHOUT TIME ZONE,
status VARCHAR(255) NOT NULL,
client_id BIGINT NOT NULL
);

--changeset cookie9161:4
CREATE TABLE IF NOT EXISTS car_offer(
id BIGSERIAL PRIMARY KEY,
make VARCHAR(255) NOT NULL,
model VARCHAR(255) NOT NULL,
body VARCHAR(255) NOT NULL,
version VARCHAR(255) NOT NULL,
generation VARCHAR(255) NOT NULL,
vin VARCHAR(255) NOT NULL,
fuel VARCHAR(255) NOT NULL,
power INT NOT NULL,
gearbox VARCHAR(255) NOT NULL,
inside_city_fuel_consumption DOUBLE PRECISION NOT NULL,
outside_city_fuel_consumption DOUBLE PRECISION NOT NULL,
door_count INT NOT NULL,
color VARCHAR(255) NOT NULL,
paint_finish VARCHAR(255),
production_country VARCHAR(255),
production_year BIGINT NOT NULL,
registered_in_poland BOOLEAN NOT NULL,
after_incident BOOLEAN NOT NULL,
wear_stage VARCHAR(255) NOT NULL,
millage BIGINT NOT NULL,
engine_capacity DOUBLE PRECISION NOT NULL,
modified_date TIMESTAMP WITHOUT TIME ZONE,
FOREIGN KEY (id) REFERENCES offer(id)
);

--changeset cookie9161:5
ALTER TABLE IF EXISTS offer
    DROP COLUMN mark,
    DROP COLUMN production_year,
    DROP COLUMN millage,
    DROP COLUMN engine_capacity,
    ADD COLUMN offer_type VARCHAR(255) NOT NULL DEFAULT 'CAR',
    ADD COLUMN title VARCHAR(255) DEFAULT 'No title' NOT NULL,
    ADD COLUMN negotiable BOOLEAN DEFAULT false NOT NULL;