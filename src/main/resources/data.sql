-- CARS
INSERT INTO car
(`car_type`, `brand`, `fuel`, `car_engine`, `hp`, `model`, `sitting_places`, `rent_price`, `deleted`,`rate`,`car_description`,`image_path`)
VALUES
('family', 'Ford', 'diesel', '1997cc', '150', 'Mondeo', '5', '40', '0',5,'Ford mondeo dynamic text from DB','/images/cars/ford-mondeo-id1-200x120.png');
INSERT INTO car
(`car_type`, `brand`, `fuel`, `car_engine`, `hp`, `model`, `sitting_places`, `rent_price`, `deleted`,`rate`,`image_path`)
VALUES
('family', 'Ford', 'petrol', '1999cc', '203', 'Mondeo', '5', '43', '0',4.8,'/images/cars/ford-mondeo-id2-200x120.png');
INSERT INTO car
(`car_type`, `brand`, `fuel`, `car_engine`, `hp`, `model`, `sitting_places`, `rent_price`, `deleted`,`rate`,`image_path`)
VALUES
('family', 'Ford', 'diesel', '1498cc', '95', 'Focus', '5', '35', '0',4.7,'/images/cars/ford-focus-id3-200x120.png');
INSERT INTO car
(`car_type`, `brand`, `fuel`, `car_engine`, `hp`, `model`, `sitting_places`, `rent_price`, `deleted`,`image_path`)
VALUES
('family', 'Ford', 'diesel', '1498cc', '95', 'Focus', '5', '35', '0','/images/cars/ford-focus-id4-200x120.png');
INSERT INTO car
(`car_type`, `brand`, `fuel`, `car_engine`, `hp`, `model`, `sitting_places`, `rent_price`, `deleted`,`car_description`,`image_path`)
VALUES
('sport', 'Audi', 'petrol', '2894cc', '450', 'RS5', '5', '120', '0','Audi RS5 dynamic text from DB','/images/cars/audi-rs5-id5-200x120.png');
INSERT INTO car
(`car_type`, `brand`, `fuel`, `car_engine`, `hp`, `model`, `sitting_places`, `rent_price`, `deleted`,`car_description`,`image_path`)
VALUES
('sport', 'Jaguar', 'petrol', '2996cc', '380', 'F-Type', '2', '110', '0','Jaguar F-Type dynamic text from DB','/images/cars/jaguar-ftype-id6-200x120.png');
INSERT INTO car
(`car_type`, `brand`, `fuel`, `car_engine`, `hp`, `model`, `sitting_places`, `rent_price`, `deleted`,`image_path`)
VALUES
('family', 'Peugeot', 'diesel', '1560cc', '115', 'Traveller', '8', '45', '0','/images/cars/peugeot-traveller-id7-200x120.png');
INSERT INTO car
(`car_type`, `brand`, `fuel`, `car_engine`, `hp`, `model`, `sitting_places`, `rent_price`, `deleted`,`car_description`,`image_path`)
VALUES
('transport', 'Ford', 'petrol', '998cc', '100', 'Courier', '2', '35', '0','Ford Courier dynamic text from DB','/images/cars/ford-courier-id8-200x120.png');
INSERT INTO car
(`car_type`, `brand`, `fuel`, `car_engine`, `hp`, `model`, `sitting_places`, `rent_price`, `deleted`,`car_description`,`image_path`)
VALUES
('transport', 'Renault', 'diesel', '1997cc', '145', 'Trafic', '3', '42', '0','Peugeot Trafic dynamic text from DB','/images/cars/peugeot-trafic-id9-200x120.png');


-- USER ROlE
INSERT INTO `user_role` (`user_role`) VALUES ('USER');
INSERT INTO `user_role` (`user_role`) VALUES ('ADMIN');
INSERT INTO `user_role` (`user_role`) VALUES ('MANAGER');


-- USERS
-- username: admin          password:admin
-- username: user123        password: user123
-- username: user456        password: user456
INSERT INTO `rental_user`
(`username`,`email`,`user_password`,`first_name`,`last_name`,`country`,`city`,`address`,`zip`,`birthdate`,`phone`,`role_id`)
VALUES
('admin','admin@myemail.com','$2a$10$amPAOVNolNd1xmv34322OenZEBrU3JFPKshau3jYN0oqaY8pDUPOW','Dawid','Pater','Poland','Tychy','Street 55/14','43-100','1950-01-01','754432532',2);

INSERT INTO `rental_user`
(`username`,`email`,`user_password`,`first_name`,`last_name`,`country`,`city`,`address`,`zip`,`birthdate`,`phone`,`blocked`)
VALUES
('user123','user123@myemail.com','$2a$10$tAxU2qOSwUjqflCXmLe8k..RSA/O2/ry2/UthdNHYtQ5b4dB/xq0y','Danil','Petrov','UK','London','Street 158','GU16 7HF','1987-01-01','75447632',true);

INSERT INTO `rental_user`
(`username`,`email`,`user_password`,`first_name`,`last_name`,`country`,`city`,`address`,`zip`,`birthdate`,`phone`)
VALUES
('user456','user456@myemail.com','$2a$10$SJcu7P0uYGUDdQHfQRhRpOEOI7l7fv3ccyFZVYFInM8tnxa/kLWpC','Андрій','Максим','Ukraine','Київ','вулиця Володимирська 43','02000','1987-01-01','75447632');

-- RENTALS
INSERT INTO `rental`
(`start_date`,`end_date`,`user_id`,`car_id`,`company_driver`,`driving_license`,`confirmed`,`started`,`ended`,`car_damaged`)
VALUES
('2022-07-01 17:42:48','2022-07-02 17:22:48',3,5,0,'ASN916371',1,1,1,0);

INSERT INTO `rental`
(`start_date`,`end_date`,`user_id`,`car_id`,`company_driver`,`driving_license`,`confirmed`,`started`,`ended`,`car_damaged`)
VALUES
('2022-07-03 17:42:48','2022-07-04 17:12:48',1,2,0,'ASN916371',1,1,1,0);

INSERT INTO `rental`
(`start_date`,`end_date`,`user_id`,`car_id`,`company_driver`,`driving_license`,`confirmed`,`started`,`ended`,`car_damaged`)
VALUES
('2022-07-05 17:42:48','2022-07-06 17:02:48',2,8,0,'ASN916371',1,1,1,0);

INSERT INTO `rental`
(`start_date`,`end_date`,`user_id`,`car_id`,`company_driver`,`driving_license`,`confirmed`,`started`,`ended`,`car_damaged`)
VALUES
('2022-07-07 17:42:48','2022-07-08 17:42:48',3,5,0,'ASN916371',1,1,1,0);

INSERT INTO `rental`
(`start_date`,`end_date`,`user_id`,`car_id`,`company_driver`,`driving_license`,`confirmed`,`started`,`ended`,`car_damaged`)
VALUES
('2022-07-09 17:42:48','2022-07-10 17:42:48',3,5,0,'ASN916371',1,1,1,0);

INSERT INTO `rental`
(`start_date`,`end_date`,`user_id`,`car_id`,`company_driver`,`driving_license`,`confirmed`,`started`,`ended`,`car_damaged`)
VALUES
('2022-07-11 17:42:48','2022-07-12 17:42:48',3,5,0,'ASN916371',1,1,1,0);

INSERT INTO `rental`
(`start_date`,`end_date`,`user_id`,`car_id`,`company_driver`,`driving_license`,`confirmed`,`started`,`ended`,`car_damaged`)
VALUES
('2022-07-13 17:42:48','2022-07-14 17:42:48',1,7,0,'ASN916371',1,1,1,0);
INSERT INTO `rental`
(`start_date`,`end_date`,`user_id`,`car_id`,`company_driver`,`driving_license`,`confirmed`,`started`,`ended`,`car_damaged`)
VALUES
('2023-07-13 17:42:48','2023-07-14 17:42:48',1,7,0,'ASN916371',0,0,0,0);
INSERT INTO `rental`
(`start_date`,`end_date`,`user_id`,`car_id`,`company_driver`,`driving_license`,`confirmed`,`started`,`ended`,`car_damaged`)
VALUES
('2024-07-13 17:42:48','2024-07-14 17:42:48',1,2,0,'ASN916371',0,0,0,0);
INSERT INTO `rental`
(`start_date`,`end_date`,`user_id`,`car_id`,`company_driver`,`driving_license`,`confirmed`,`started`,`ended`,`car_damaged`)
VALUES
('2023-01-13 17:42:48','2023-01-14 17:42:48',1,3,0,'ASN916371',0,0,0,0);
INSERT INTO `rental`
(`start_date`,`end_date`,`user_id`,`car_id`,`company_driver`,`driving_license`,`confirmed`,`started`,`ended`,`car_damaged`)
VALUES
('2023-01-11 17:42:48','2023-01-12 17:42:48',1,1,0,'ASN916371',0,0,0,0);
INSERT INTO `rental`
(`start_date`,`end_date`,`user_id`,`car_id`,`company_driver`,`driving_license`,`confirmed`,`started`,`ended`,`car_damaged`)
VALUES
('2022-12-01 17:42:48','2022-12-03 17:42:48',1,8,0,'ASN916371',0,0,0,0);
INSERT INTO `rental`
(`start_date`,`end_date`,`user_id`,`car_id`,`company_driver`,`driving_license`,`confirmed`,`started`,`ended`,`car_damaged`)
VALUES
('2023-03-11 17:42:48','2023-04-12 17:42:48',1,7,0,'ASN916371',0,0,0,0);

-- feedbackS
INSERT INTO `feedback`
(`content`,`rate`,`feedback_date`,`rental_id`)
VALUES
('Best car I ever rented. I really recommend this rental',5.0,'2022-07-15 17:47:48',6);
INSERT INTO `feedback`
(`content`,`rate`,`feedback_date`,`rental_id`)
VALUES
('Best car I ever rented. I really recommend this rental',4.5,'2022-07-11 17:42:48',5);
INSERT INTO `feedback`
(`content`,`rate`,`rental_id`)
VALUES
('Best car I ever rented. I really recommend this rental',5.0,2);
INSERT INTO `feedback`
(`content`,`rate`,`rental_id`)
VALUES
('Car was to slow for a long journey',4.0,1);
INSERT INTO `feedback`
(`content`,`rate`,`rental_id`)
VALUES
('Car was to low for a long journey',3.2,4);
INSERT INTO `feedback`
(`content`,`rate`,`feedback_date`,`rental_id`)
VALUES
('Im sad',1.8,'2022-07-11 12:47:48',3);
INSERT INTO `feedback`
(`content`,`rate`,`feedback_date`,`rental_id`)
VALUES
('Im unhappy',2.5,'2022-07-11 12:47:48',7);
-- invoices
INSERT INTO `invoice` (`rental_value`, `additional_cost`, `RENTAL_id`,`status`) VALUES ('231.72', '0', '1','paid');
INSERT INTO `invoice` (`rental_value`, `additional_cost`, `RENTAL_id`,`status`) VALUES ('150', '0', '2','paid');
INSERT INTO `invoice` (`rental_value`, `additional_cost`, `RENTAL_id`,`status`) VALUES ('30', '0', '3','paid');
INSERT INTO `invoice` (`rental_value`, `additional_cost`, `RENTAL_id`,`status`) VALUES ('120', '0', '4','unpaid');
INSERT INTO `invoice` (`rental_value`, `additional_cost`, `RENTAL_id`,`status`) VALUES ('143', '0', '5','paid');
INSERT INTO `invoice` (`rental_value`, `additional_cost`, `RENTAL_id`,`status`) VALUES ('255.78', '0', '6','paid');
INSERT INTO `invoice` (`rental_value`, `additional_cost`, `RENTAL_id`,`status`) VALUES ('120.10', '0', '7','paid');
INSERT INTO `invoice` (`rental_value`, `additional_cost`, `RENTAL_id`,`status`) VALUES ('55.78', '0', '8','paid');
INSERT INTO `invoice` (`rental_value`, `additional_cost`, `RENTAL_id`,`status`) VALUES ('180', '0', '9','paid');
INSERT INTO `invoice` (`rental_value`, `additional_cost`, `RENTAL_id`,`status`) VALUES ('45.2', '0', '10','unpaid');
INSERT INTO `invoice` (`rental_value`, `additional_cost`, `RENTAL_id`,`status`) VALUES ('33.50', '0', '11','unpaid');
INSERT INTO `invoice` (`rental_value`, `additional_cost`, `RENTAL_id`,`status`) VALUES ('132.10', '0', '12','paid');
INSERT INTO `invoice` (`rental_value`, `additional_cost`, `RENTAL_id`,`status`) VALUES ('203.50', '0', '13','paid');