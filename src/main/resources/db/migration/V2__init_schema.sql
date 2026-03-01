
ALTER TABLE app_user
    RENAME COLUMN name TO last_name;


ALTER TABLE app_user
    ADD COLUMN first_name VARCHAR(255) NOT NULL DEFAULT '';


ALTER TABLE app_user
    ADD COLUMN profession VARCHAR(255);