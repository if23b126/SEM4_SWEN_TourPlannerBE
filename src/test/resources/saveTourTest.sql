
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
                             tour_timecreated timestamp DEFAULT CURRENT_TIMESTAMP NULL,
                             tour_popularity float8  DEFAULT 0 NULL,
                             tour_childfriendliness float8  DEFAULT 0 NULL
);

CREATE SEQUENCE tour_seq START WITH 1 INCREMENT BY 1;
