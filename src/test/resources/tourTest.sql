
DROP ALL OBJECTS;
CREATE SCHEMA IF NOT EXISTS public;
CREATE SEQUENCE TOUR_SEQ START WITH 2 INCREMENT BY 1;

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

INSERT INTO public.tour ( id, tour_name, tour_description, tour_start, tour_end, tour_transportmode, tour_distance, tour_duration, tour_information) VALUES ( 1, 'test','test','test','test','test',3,2,'test');
