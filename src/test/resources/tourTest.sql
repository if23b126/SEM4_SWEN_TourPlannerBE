DROP ALL OBJECTS;
CREATE SCHEMA IF NOT EXISTS public;

CREATE TABLE public.tour (
    tour_name varchar(255) NULL,
    tour_description varchar(255) NULL,
    tour_start varchar(255) NULL,
    tour_end varchar(255) NULL,
    tour_transportmode varchar(255) NULL,
    tour_distance float8 NULL,
    tour_timestart timestamptz NULL,
    tour_timeend timestamptz NULL,
    tour_information varchar(255) NULL,
    tour_timecreated timestamptz DEFAULT CURRENT_TIMESTAMP NULL,
    CONSTRAINT tour_unique UNIQUE (id)
);

INSERT INTO public.tour (tour_name, tour_description, tour_start, tour_end, tour_transportmode, tour_distance, tour_timestart, tour_timeend, tour_information) VALUES ('test','test','test','test','test',3,'2025-05-22 08:35:00.000','2025-05-22 10:35:00.000','test');