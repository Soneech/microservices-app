CREATE TABLE IF NOT EXISTS roles (
      id bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
      name varchar(100) NOT NULL UNIQUE
);

ALTER TABLE IF EXISTS public.roles OWNER to postgres;

CREATE TABLE IF NOT EXISTS users (
       id bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
       username varchar(100) NOT NULL UNIQUE,
       phone_number varchar(12) NOT NULL UNIQUE,
       email varchar(100) NOT NULL UNIQUE,
       password varchar NOT NULL,
       role_id bigint REFERENCES roles(id)
);

ALTER TABLE IF EXISTS public.users OWNER to postgres;

INSERT INTO roles (name) VALUES('ROLE_USER');
INSERT INTO roles (name) VALUES('ROLE_ADMIN');

INSERT INTO users (username, phone_number, email, password, role_id)
    VALUES('Soneech', '81234567890', 'soneech@example.com', '$2a$10$mGWK/lPDjOV25.UKCZqDrO4rpkgm7h2Ws3XLnvVYrokqgtSyQSVpW', 2);
