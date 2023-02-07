DROP TYPE IF EXISTS ROLE CASCADE;
CREATE TYPE ROLE AS ENUM ('USER', 'ADMIN');
DROP TYPE IF EXISTS FORMAT CASCADE;
CREATE TYPE FORMAT AS ENUM ('LUX', 'D3', 'D2');

DROP TABLE IF EXISTS users CASCADE;
CREATE TABLE IF NOT EXISTS users
(
    id_user       BIGSERIAL,
    first_name    TEXT      NOT NULL,
    last_name     TEXT      NOT NULL,
    password      TEXT      NOT NULL,
    phone_number  TEXT      NOT NULL,
    email_address TEXT      NOT NULL,
    create_date   TIMESTAMP DEFAULT NOW(),
    update_date   TIMESTAMP NOT NULL,
    role          ROLE      DEFAULT 'USER',
    CONSTRAINT PK_user PRIMARY KEY (id_user),
    CONSTRAINT UQ_user_email_address UNIQUE (email_address),
    CONSTRAINT UQ_phone_number UNIQUE (phone_number)
);
INSERT INTO public.users(
    id_user, first_name, last_name, phone_number, email_address, create_date, update_date, role, password)
VALUES (2, 'first_name', 'last_name', 'phone_number', 'email_address', now(), now(), 'USER', 'password');

DROP TABLE IF EXISTS movies CASCADE;
CREATE TABLE IF NOT EXISTS movies
(
    id_movie      BIGSERIAL,
    original_name TEXT                  NOT NULL,
    release_DATE  TIMESTAMP             NOT NULL,
    available_age TEXT                  NOT NULL,
    poster        bytea,
    deleted       BOOLEAN DEFAULT false NOT NULL,
    CONSTRAINT PK_movie PRIMARY KEY (id_movie)
);
INSERT INTO public.movies(id_movie, original_name, release_date, available_age, deleted, poster)
VALUES (2, 'originalName', now(), 18, false, null);


DROP TABLE IF EXISTS sessions CASCADE;
CREATE TABLE IF NOT EXISTS sessions
(
    id_session      BIGSERIAL,
    movie_id        BIGINT    NOT NULL,
    start_time      TIMESTAMP NOT NULL,
    end_time        TIMESTAMP NOT NULL,
    available_seats INTEGER   NOT NULL,
    format          FORMAT    NOT NULL,
    price           BIGINT    NOT NULL,
    CONSTRAINT PK_session PRIMARY KEY (id_session),
    CONSTRAINT FK_session_movie FOREIGN KEY (movie_id) REFERENCES movies
);

INSERT INTO public.sessions(
    id_session, movie_id, start_time, end_time, available_seats, price, format)
VALUES (2, 2, now() + INTERVAL '1 hour', now(), 2, 120, 'LUX'),
       (3, 2, now() + INTERVAL '1 day', now(), 2, 120, 'LUX');


DROP TABLE IF EXISTS languages CASCADE;
CREATE TABLE IF NOT EXISTS languages
(
    id_language   BIGSERIAL,
    language_name TEXT NOT NULL,
    CONSTRAINT PK_language PRIMARY KEY (id_language)
);

INSERT INTO public.languages(id_language, language_name)
VALUES (1, 'eng'),
       (2, 'ua');

DROP TABLE IF EXISTS movie_descriptions CASCADE;
CREATE TABLE IF NOT EXISTS movie_descriptions
(
    movie_id    BIGSERIAL,
    title       TEXT    NOT NULL,
    director    TEXT    NOT NULL,
    language_id INTEGER NOT NULL,
    CONSTRAINT PK_movie_description_language PRIMARY KEY (movie_id, language_id),
    CONSTRAINT fK_movie_description_movie FOREIGN KEY (movie_id) REFERENCES movies,
    CONSTRAINT fK_movie_description_language FOREIGN KEY (language_id) REFERENCES languages
);
INSERT INTO public.movie_descriptions(movie_id, title, director, language_id)
VALUES (2, 'title1', 'director1', 1),
       (2, 'назва1', 'режисер1', 2);


DROP TABLE IF EXISTS seats CASCADE;
CREATE TABLE IF NOT EXISTS seats
(
    id_seat BIGSERIAL,
    row     INTEGER NOT NULL,
    number  INTEGER NOT NULL,
    CONSTRAINT PK_seat PRIMARY KEY (id_seat)
);
INSERT INTO public.seats(
    id_seat, "row", "number")
VALUES (1, 1, 1);

DROP TABLE IF EXISTS purchased_seats CASCADE;
CREATE TABLE IF NOT EXISTS purchased_seats
(
    id_purchased_seat BIGSERIAL,
    session_id        BIGINT NOT NULL,
    seat_id           BIGINT NOT NULL,
    CONSTRAINT PK_purchased_seats PRIMARY KEY (id_purchased_seat),
    CONSTRAINT FK_seat_session_session FOREIGN KEY (session_id) REFERENCES sessions,
    CONSTRAINT FK_seat_session_seat FOREIGN KEY (seat_id) REFERENCES seats,
    CONSTRAINT UQ_seat_id_session_id UNIQUE (seat_id, session_id)
);
INSERT INTO public.purchased_seats(
    id_purchased_seat, session_id, seat_id)
VALUES (2, 2, 1);

DROP TABLE IF EXISTS tickets CASCADE;
CREATE TABLE IF NOT EXISTS tickets
(
    id_ticket         BIGSERIAL,
    user_id           BIGINT    NOT NULL,
    purchased_seat_id BIGINT    NOT NULL,
    purchase_date     TIMESTAMP NOT NULL,
    CONSTRAINT PK_ticket PRIMARY KEY (id_ticket),
    CONSTRAINT FK_ticket_user FOREIGN KEY (user_id) REFERENCES users,
    CONSTRAINT FK_ticket_purchased_seat FOREIGN KEY (purchased_seat_id) REFERENCES purchased_seats
);

INSERT INTO public.tickets(
    id_ticket, user_id, purchased_seat_id, purchase_date)
VALUES (2, 2, 2, now());


