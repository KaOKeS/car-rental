  CREATE SCHEMA takethatcar;

  CREATE TABLE car (
  `id` BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  `car_type` VARCHAR(45) NOT NULL,
  `brand` VARCHAR(45) NOT NULL,
  `fuel` VARCHAR(45) NOT NULL,
  `car_engine` VARCHAR(45) NOT NULL,
  `hp` INT NOT NULL,
  `model` VARCHAR(45) NOT NULL,
  `sitting_places` TINYINT NOT NULL,
  `rent_price` REAL NOT NULL,
  `deleted` TINYINT NOT NULL,
  `rate` DOUBLE NOT NULL DEFAULT 5.0,
  `image_path` VARCHAR(255) NULL DEFAULT '/images/cars/no-car-image.png',
  `car_description` MEDIUMTEXT NULL DEFAULT NULL
  );

  CREATE TABLE user_role (
    `id` BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    `user_role` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`));

  CREATE TABLE rental_user (
    `id` BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    `username` VARCHAR(50) NOT NULL,
    `email` VARCHAR(120) NOT NULL,
    `user_password` VARCHAR(100) NOT NULL,
    `first_name` VARCHAR(120) NOT NULL,
    `last_name` VARCHAR(120) NOT NULL,
    `country` VARCHAR(60) NOT NULL,
    `city` VARCHAR(70) NOT NULL,
    `address` VARCHAR(255) NOT NULL,
    `zip` VARCHAR(45) NOT NULL,
    `birthdate` DATE NOT NULL,
    `phone` VARCHAR(45) NOT NULL,
    `blocked` TINYINT NOT NULL DEFAULT '0',
    `role_id` INT NOT NULL DEFAULT '1',
    FOREIGN KEY (`role_id`) REFERENCES user_role(`id`));

CREATE TABLE rental (
  `id` BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  `start_date` TIMESTAMP NOT NULL,
  `end_date` TIMESTAMP NOT NULL,
  `user_id` BIGINT NOT NULL,
  `car_id` BIGINT NOT NULL,
  `company_driver` TINYINT DEFAULT '0',
  `driving_license` VARCHAR(100) NULL,
    `rejected` TINYINT DEFAULT '0',
    `rejection_reason` MEDIUMTEXT NULL DEFAULT NULL,
    `confirmed` TINYINT DEFAULT '0',
    `started` TINYINT DEFAULT '0',
    `ended` TINYINT DEFAULT '0',
    `car_damaged` TINYINT DEFAULT '0',
    `damage_description` MEDIUMTEXT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES `rental_user` (`id`),
  FOREIGN KEY (`car_id`) REFERENCES `car` (`id`));

CREATE TABLE invoice (
  `id` BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  `rental_value` REAL NOT NULL,
  `additional_cost` REAL NULL,
  `RENTAL_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`rental_id`) REFERENCES `rental` (`id`));

  CREATE TABLE `feedback` (
    `id` BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    `content` MEDIUMTEXT NOT NULL,
    `rate` REAL NOT NULL,
    `feedback_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP(),
    `rental_id` BIGINT NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`rental_id`) REFERENCES `rental` (`id`));




