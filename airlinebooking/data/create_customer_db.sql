
CREATE DATABASE customerdb;

CREATE SCHEMA customer_schema;

CREATE TABLE
  customer_schema.customer (
    id integer NOT NULL,
    first_name character varying NULL,
    last_name character varying NULL,
    age_in_years integer NULL
  );

ALTER TABLE
  customer_schema.customer
ADD
  CONSTRAINT customer_pkey PRIMARY KEY (id);

CREATE TABLE
  customer_schema.trips (id integer NOT NULL, name character varying NULL);

ALTER TABLE
  customer_schema.trips
ADD
  CONSTRAINT trips_pkey PRIMARY KEY (id);

CREATE TABLE
  customer_schema.seats (
    id integer NOT NULL,
    name character varying NULL,
    trip_id integer NULL,
    customer_id integer NULL
  );

ALTER TABLE
  customer_schema.seats
ADD
  CONSTRAINT seats_pkey PRIMARY KEY (id);