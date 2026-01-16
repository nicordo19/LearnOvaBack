-- Extensions
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- ======================
-- ROLE
-- ======================
CREATE TABLE role (
                      id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                      rol_name VARCHAR(100) NOT NULL UNIQUE
);

-- ======================
-- USER
-- ======================
CREATE TABLE app_user (
                          id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                          name VARCHAR(255) NOT NULL,
                          email VARCHAR(255) NOT NULL UNIQUE,
                          password VARCHAR(255) NOT NULL,
                          created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          role_id UUID NOT NULL,
                          CONSTRAINT fk_user_role
                              FOREIGN KEY (role_id)
                                  REFERENCES role(id)
);

-- ======================
-- CATEGORY
-- ======================
CREATE TABLE category (
                          id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                          name VARCHAR(255) NOT NULL,
                          description TEXT
);

-- ======================
-- VIDEO
-- ======================
CREATE TABLE video (
                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                       title VARCHAR(255) NOT NULL,
                       description TEXT,
                       url TEXT NOT NULL,
                       published_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       user_id UUID NOT NULL,
                       category_id UUID NOT NULL,
                       CONSTRAINT fk_video_user
                           FOREIGN KEY (user_id)
                               REFERENCES app_user(id)
                               ON DELETE CASCADE,
                       CONSTRAINT fk_video_category
                           FOREIGN KEY (category_id)
                               REFERENCES category(id)
);

-- ======================
-- COMMENT
-- ======================
CREATE TABLE comment (
                         id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                         content TEXT NOT NULL,
                         created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         user_id UUID NOT NULL,
                         video_id UUID NOT NULL,
                         CONSTRAINT fk_comment_user
                             FOREIGN KEY (user_id)
                                 REFERENCES app_user(id)
                                 ON DELETE CASCADE,
                         CONSTRAINT fk_comment_video
                             FOREIGN KEY (video_id)
                                 REFERENCES video(id)
                                 ON DELETE CASCADE
);
