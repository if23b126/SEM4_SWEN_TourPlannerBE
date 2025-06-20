
DROP ALL OBJECTS;
CREATE SCHEMA IF NOT EXISTS public;

CREATE TABLE public.tour (
                             id int8 NOT NULL,
                             tour_description varchar(255) NULL,
                             tour_distance float8 NULL,
                             tour_end varchar(255) NULL,
                             tour_information varchar(255) NULL,
                             tour_name varchar(255) NULL,
                             tour_start varchar(255) NULL,
                             tour_timecreated timestamp(6) NULL,
                             tour_transportmode varchar(255) NULL,
                             tour_popularity float8 DEFAULT 0 NULL,
                             tour_childfriendliness float8 DEFAULT 0 NULL,
                             tour_duration float8 NULL,
                             CONSTRAINT tour_pkey PRIMARY KEY (id)
);

CREATE SEQUENCE tour_seq START WITH 1 INCREMENT BY 1;
