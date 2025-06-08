CREATE TABLE category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(255)
);

CREATE TABLE dish (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DOUBLE NOT NULL,
    description VARCHAR(255),
    image_url VARCHAR(255),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    restaurant_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    FOREIGN KEY (category_id) REFERENCES category(id),
    FOREIGN KEY (restaurant_id) REFERENCES restaurant(id)
);


INSERT INTO category (name, description)
VALUES ('Main Course', 'Main dishes typically served as the central part of a meal');


-- REVERT
-- DROP TABLE IF EXISTS category;
-- DELETE FROM category WHERE name = 'Main Course';
-- DELETE FROM flyway_schema_history WHERE version = '1.1';
