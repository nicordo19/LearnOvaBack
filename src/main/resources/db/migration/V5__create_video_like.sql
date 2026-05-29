CREATE TABLE video_like (
                            id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                            user_id UUID NOT NULL,
                            video_id UUID NOT NULL,
                            created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            CONSTRAINT fk_video_like_user
                                FOREIGN KEY (user_id)
                                    REFERENCES app_user(id)
                                    ON DELETE CASCADE,
                            CONSTRAINT fk_video_like_video
                                FOREIGN KEY (video_id)
                                    REFERENCES video(id)
                                    ON DELETE CASCADE,
                            CONSTRAINT uk_video_like_user_video
                                UNIQUE (user_id, video_id)
);
