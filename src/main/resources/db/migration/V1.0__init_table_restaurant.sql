CREATE TABLE restaurant (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    nit VARCHAR(20) NOT NULL UNIQUE,
    address VARCHAR(255) NOT NULL,
    phone VARCHAR(20) NOT NULL UNIQUE,
    logo_url VARCHAR(255) NOT NULL,
    user_id BIGINT NOT NULL
);

-- REVERT
-- DROP TABLE IF EXISTS restaurant;
-- DELETE FROM flyway_schema_history WHERE version = '1.0';
