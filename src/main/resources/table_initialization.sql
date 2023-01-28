CREATE TYPE ROLE AS ENUM ('USER', 'ADMIN');
CREATE TYPE FORMAT AS ENUM ('LUX', 'D3', 'D2');

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
    role          ROLE      NOT NULL,
    CONSTRAINT PK_user PRIMARY KEY (id_user),
    CONSTRAINT UQ_user_email_address UNIQUE (email_address),
    CONSTRAINT UQ_phone_number UNIQUE (phone_number)
);

CREATE TABLE IF NOT EXISTS movies
(
    id_movie      BIGSERIAL,
    original_name TEXT                  NOT NULL,
    release_DATE  TIMESTAMP             NOT NULL,
    available_age TEXT                  NOT NULL,
    poster        bytea                 NOT NULL,
    deleted       BOOLEAN DEFAULT false NOT NULL,
    CONSTRAINT PK_movie PRIMARY KEY (id_movie)
);

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



CREATE TABLE IF NOT EXISTS languages
(
    id_language   BIGSERIAL,
    language_name TEXT NOT NULL,
    CONSTRAINT PK_language PRIMARY KEY (id_language)
);

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


CREATE TABLE IF NOT EXISTS seats
(
    id_seat BIGSERIAL,
    row     INTEGER NOT NULL,
    number  INTEGER NOT NULL,
    CONSTRAINT PK_seat PRIMARY KEY (id_seat)
);


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


