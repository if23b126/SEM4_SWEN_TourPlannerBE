DROP ALL OBJECTS;
CREATE SCHEMA IF NOT EXISTS public;
CREATE SEQUENCE LOG_SEQ START WITH 2 INCREMENT BY 1;

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

CREATE TABLE public.logs (
                             id int8 NOT NULL,
                             logs_comment varchar(255) NULL,
                             logs_difficulty float8 NULL,
                             logs_distance float8 NULL,
                             logs_rating float8 NULL,
                             logs_time timestamp(6) NULL,
                             logs_timeend timestamp(6) NULL,
                             logs_timestart timestamp(6) NULL,
                             logs_tourid int8 NULL,
                             CONSTRAINT logs_pkey PRIMARY KEY (id)
);

INSERT INTO public.tour (id, tour_name, tour_description, tour_start, tour_end, tour_transportmode, tour_distance, tour_duration, tour_information) VALUES (1, 'test','test','test','test','test',3,2,'test');
INSERT INTO public.logs (id, logs_comment, logs_difficulty, logs_distance, logs_rating, logs_time, logs_timestart, logs_timeend, logs_tourid) VALUES(1, 'test', 1, 2, 3, '2025-05-22 12:35:00.000', '2025-05-22 08:35:00.000', '2025-05-22 10:35:00.000', 1);