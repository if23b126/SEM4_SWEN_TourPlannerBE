
DROP ALL OBJECTS;
CREATE SCHEMA IF NOT EXISTS public;

CREATE TABLE public.tour (
    id float AUTO_INCREMENT,
    tour_name varchar(255) NULL,
    tour_description varchar(255) NULL,
    tour_start varchar(255) NULL,
    tour_end varchar(255) NULL,
    tour_transportmode varchar(255) NULL,
    tour_distance float8 NULL,
    tour_timestart timestamp with time zone NULL,
    tour_timeend timestamp with time zone NULL,
    tour_information varchar(255) NULL,
    tour_timecreated timestamp DEFAULT CURRENT_TIMESTAMP NULL
);

INSERT INTO public.tour (id, tour_name, tour_description, tour_start, tour_end, tour_transportmode, tour_distance, tour_timestart, tour_timeend, tour_information) VALUES (1, 'test','test','test','test','test',3,'2025-05-22 08:35:00.000','2025-05-22 10:35:00.000','test');