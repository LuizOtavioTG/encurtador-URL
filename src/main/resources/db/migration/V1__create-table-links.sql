CREATE TABLE links (
    id SERIAL PRIMARY KEY,
    url_long VARCHAR(1024) NOT NULL,
    url_short VARCHAR(255),
    url_qr_code BYTEA,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expiry_at TIMESTAMP,
    views INT DEFAULT 0
);
