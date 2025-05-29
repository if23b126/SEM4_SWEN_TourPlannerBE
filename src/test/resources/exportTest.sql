DROP ALL OBJECTS;
CREATE SCHEMA IF NOT EXISTS public;
CREATE SEQUENCE LOG_SEQ START WITH 2 INCREMENT BY 1;
CREATE SEQUENCE TOUR_SEQ START WITH 1 INCREMENT BY 1;

CREATE TABLE public.tour (
                             id float NOT NULL,
                             tour_description varchar(255) NULL,
                             tour_distance float NULL,
                             tour_end varchar(255) NULL,
                             tour_information varchar(255) NULL,
                             tour_name varchar(255) NULL,
                             tour_start varchar(255) NULL,
                             tour_timecreated timestamp(6) NULL,
                             tour_timeend timestamp(6) NULL,
                             tour_timestart timestamp(6) NULL,
                             tour_transportmode varchar(255) NULL,
                             CONSTRAINT tour_pkey PRIMARY KEY (id)
);

CREATE TABLE public.logs (
                             id float NOT NULL,
                             logs_comment varchar(255) NULL,
                             logs_difficulty float NULL,
                             logs_distance float NULL,
                             logs_rating float NULL,
                             logs_time timestamp(6) NULL,
                             logs_timeend timestamp(6) NULL,
                             logs_timestart timestamp(6) NULL,
                             logs_tourid float NULL,
                             CONSTRAINT logs_pkey PRIMARY KEY (id)
);

INSERT INTO public.tour (id, tour_name, tour_description, tour_start, tour_end, tour_transportmode, tour_distance, tour_timestart, tour_timeend, tour_information) VALUES (1, 'test','test','test','test','test',3,'2025-05-22 08:35:00.000','2025-05-22 10:35:00.000','test');
INSERT INTO public.logs (id, logs_comment, logs_difficulty, logs_distance, logs_rating, logs_time, logs_timestart, logs_timeend, logs_tourid) VALUES(1, 'test', 1, 2, 3, '2025-05-22 12:35:00.000', '2025-05-22 08:35:00.000', '2025-05-22 10:35:00.000', 1);