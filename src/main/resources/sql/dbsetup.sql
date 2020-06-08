DROP TABLE IF EXISTS public.result CASCADE;
DROP TABLE IF EXISTS public.challange CASCADE;
DROP TABLE IF EXISTS public.user CASCADE;
DROP TYPE IF EXISTS public.gender;
DROP TYPE IF EXISTS public.challange_type;

CREATE TYPE gender AS ENUM('M', 'F');
CREATE TYPE challange_type AS ENUM('open', 'first 200');

CREATE TABLE public."user"
(
    id integer NOT NULL,
    name character(255) NOT NULL,
    gender gender NOT NULL,
	endomondo_profile_id integer NOT NULL,
	created timestamp without time zone NOT NULL DEFAULT NOW(),
    PRIMARY KEY (id),
	UNIQUE(name),
	UNIQUE(endomondo_profile_id)
);

CREATE TABLE public."challange"
(
	id integer NOT NULL,
	name character(255) NOT NULL,
	start_date timestamp without time zone NOT NULL,
    end_date timestamp without time zone NOT NULL,
	type character(255) NOT NULL,
    restriction character(255),
    sport text NOT NULL,
    total_active_days double precision NOT NULL,
    total_km double precision NOT NULL ,
    total_users integer NOT NULL ,
    total_calories double precision,
	endomondo_id integer NOT NULL,
	created timestamp without time zone NOT NULL DEFAULT NOW(),
    PRIMARY KEY (id),
	UNIQUE (name),
	CHECK(total_active_days > 0),
	CHECK(total_km > 0),
	CHECK(total_users > 0),
	CHECK(total_calories > 0),
	CHECK(start_date < end_date)
);

CREATE TABLE public."result"
(
    id serial NOT NULL,
    user_id integer NOT NULL REFERENCES public.user(id),
    challenge_id integer NOT NULL REFERENCES public.challange(id),
    km double precision NOT NULL,
    position integer NOT NULL,
    extra_points boolean NOT NULL,
    extra_points_value double precision NOT NULL,
	created timestamp without time zone NOT NULL DEFAULT NOW(),
    PRIMARY KEY (id, user_id, challenge_id),
	CHECK(position > 0),
	UNIQUE (user_id, challenge_id),
	UNIQUE (position, challenge_id),
	CHECK((extra_points IS true AND  extra_points_value>0 OR (extra_points IS false AND extra_points_value=0))),
	CHECK((position >3 AND extra_points_value=0 OR (position <=3 AND extra_points_value>0)))
);

INSERT INTO public."user"(id,  name, gender, endomondo_profile_id) VALUES ( 1, 'Wieslaw', 'M', 33133062);
INSERT INTO public."user"(id,  name,gender, endomondo_profile_id) VALUES ( 2, 'Michal', 'M', 2090913);
INSERT INTO public."user"(id,  name,gender, endomondo_profile_id) VALUES ( 3, 'Mareszka', 'F', 4435173);
INSERT INTO public."user"(id,  name,gender, endomondo_profile_id) VALUES ( 4, 'Agnieszka', 'F', 40942739);
INSERT INTO public."user"(id,  name,gender, endomondo_profile_id) VALUES ( 5, 'Justyna', 'F', 28564030);
INSERT INTO public."user"(id,  name,gender, endomondo_profile_id) VALUES ( 6, 'Marzenna', 'F', 40996195);
INSERT INTO public."user"(id,  name,gender, endomondo_profile_id) VALUES ( 7, 'Evela', 'F', 16158883);
INSERT INTO public."user"(id,  name,gender, endomondo_profile_id) VALUES ( 8, 'Radoslaw', 'M', 16158521);
INSERT INTO public."user"(id,  name,gender, endomondo_profile_id) VALUES ( 9, 'Marta', 'F', 41976560);
INSERT INTO public."user"(id,  name,gender, endomondo_profile_id) VALUES ( 10, 'Krzysztof', 'M', 25196849);
INSERT INTO public."user"(id,  name,gender, endomondo_profile_id) VALUES ( 11, 'Jakub', 'M', 5850966);
INSERT INTO public."user"(id,  name,gender, endomondo_profile_id) VALUES ( 12, '?ucja', 'F', 22576931);
INSERT INTO public."user"(id,  name,gender, endomondo_profile_id) VALUES ( 13, 'Katarzyna', 'F', 16220093);
INSERT INTO public."user"(id,  name,gender, endomondo_profile_id) VALUES ( 14, 'Marek', 'M', 16605548);
INSERT INTO public."user"(id,  name,gender, endomondo_profile_id) VALUES ( 15, 'Marcin', 'M', 21304717);
INSERT INTO public."user"(id,  name,gender, endomondo_profile_id) VALUES ( 16, 'Sara', 'F', 42418379);


INSERT INTO public.challange(id, name, start_date, end_date, type, restriction, sport, total_active_days, total_km, total_users, total_calories, endomondo_id)
	VALUES (1, 'Burn or Die 01/2020', '2020-01-07 09:00', '2020-02-16 00:00', 'open', 'distance', 'walking', 16.61, 1952, 14, 136261, 41975466 );

INSERT INTO public.challange(id, name, start_date, end_date, type, restriction, sport, total_active_days, total_km, total_users, total_calories, endomondo_id)
	VALUES (2, 'Burn or Die 02/2020', '2020-02-20 00:00', '2020-03-06 14:10', 'first 200', 'distance', 'walking', 10.03, 1155, 12, 85442, 42286920);

INSERT INTO public.challange(id, name, start_date, end_date, type, restriction, sport, total_active_days, total_km, total_users, total_calories, endomondo_id)
	VALUES (3, 'Burn or Die 03/2020', '2020-03-08 00:00', '2020-03-29 23:59', 'open', 'distance', 'walking', 13.65, 1515, 14, 122688, 42402188);

INSERT INTO public.challange(id, name, start_date, end_date, type, restriction, sport, total_active_days, total_km, total_users, total_calories, endomondo_id)
	VALUES (4, 'Burn or Die 04/2020', '2020-04-02 00:00', '2020-04-21 07:44', 'first 200', 'distance', 'walking', 11.38, 1195, 13, 95269, 42716553);

INSERT INTO public.challange(id, name, start_date, end_date, type, restriction, sport, total_active_days, total_km, total_users, total_calories, endomondo_id)
	VALUES (5, 'Burn or Die 05/2020', '2020-04-30 00:00', '2020-05-21 00:00', 'open', 'distance', 'walking', 8.59, 996, 11, 70644, 43003329);


--- MICHAL
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (2, 1, 277.71, 1, true, 69);
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (2, 2, 126.28, 3, true, 13);
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (2, 3, 274.89, 2, true, 41);
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (2, 4, 114.41, 6, false, 0);
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (2, 5, 187.43, 2, true, 28);

--- WIESLAW
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (1, 1, 265.76, 2, true, 40);
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (1, 2, 116.93, 4, false, 0);
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (1, 3, 161.53, 4, false, 0);
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (1, 4, 121.30, 5, false, 0);
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (1, 5, 149.03, 4, false, 0);

--- MARESZKA
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (3, 1, 234.49, 3, true, 23);
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (3, 2, 103.09, 5, false, 0);
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (3, 3, 134.18, 5, false, 0);
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (3, 4, 132.23, 4, false, 0);
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (3, 5, 130.57, 5, false, 0);

--- AGNIESZKA
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (4, 1, 180.27, 4, false, 0);
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (4, 2, 88.31, 6, false, 0);
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (4, 3, 52.17, 9, false, 0);
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (4, 4, 28.97, 10, false, 0);
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (4, 5, 5.04, 9, false, 0);

--- JUSTA
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (5, 1, 178.87, 5, false, 0);
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (5, 2, 70.86, 9, false, 0);
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (5, 3, 165.77, 3, true, 17);
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (5, 4, 169.09, 2, true, 25);
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (5, 5, 204.32, 1, true, 51);


--- MARZENNA
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (6, 1, 178.86, 6, false, 0);
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (6, 2, 64.71, 10, false, 0);
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (6, 3, 119.98, 6, false, 0);
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (6, 4, 67.22, 8, false, 0);
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (6, 5, 82.57, 6, false, 0);

--- EVELA
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (7, 1, 129.63, 7, false, 0);
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (7, 2, 81.34, 7, false, 0);
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (7, 3, 72.69, 8, false, 0);
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (7, 4, 29.55, 9, false, 0);
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (7, 5, 18.30, 8, false, 0);

--- RADOSLAW
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (8, 1, 122.18, 8, false, 0);
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (8, 2, 200.00, 1, true, 50);
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (8, 3, 26.58, 12, false, 0);
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (8, 4, 13.36, 13, false, 0);
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (8, 5, 3.18, 10, false, 0);

--- MARTA
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (9, 1, 111.62, 9, false, 0);
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (9, 2, 60.27 , 11, false, 0);
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (9, 3, 42.69, 10, false, 0);
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (9, 4, 109.36, 7, false, 0);
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (9, 5, 41.76, 7, false, 0);

--- KRZYSZTOF
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (10, 1, 89.57, 10, false, 0);
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (10, 2, 80.55 , 8, false, 0);
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (10, 3, 20.07, 13, false, 0);

--- JAKUB
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (11, 1, 82.52, 11, false, 0);
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (11, 2, 157.91 , 2, true, 24);
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (11, 3, 99.99 , 7, false, 0);
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (11, 4, 200.00 , 1, true, 50);
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (11, 5, 174.10 , 3, true, 17);

--- LUCJA
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (12, 1, 31.20 , 13, false, 0);

--- KATARZYNA
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (13, 1, 8.47 , 14, false, 0);

--- MAREK
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (14, 1, 61.06, 12, false, 0);
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (14, 2, 4.26 , 12, false, 0);
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (14, 3, 38.47 , 11, false, 0);
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (14, 4, 18.39  , 12, false, 0);

--- MARCIN

INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (15, 3, 302.88  , 1, true, 76);
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (15, 4, 168.60  , 3, true, 17);

--- SARA

INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (16, 3, 3.26   , 14, false, 0);
INSERT INTO public.result(user_id, challenge_id, km, "position", extra_points, extra_points_value)
VALUES (16, 4, 22.25  , 11, false, 0);







