/* Populate table client */

INSERT INTO region (id, name) VALUES (1, 'South America');
INSERT INTO region (id, name) VALUES (2, 'Central America');
INSERT INTO region (id, name) VALUES (3, 'North America');
INSERT INTO region (id, name) VALUES (4, 'Europe');
INSERT INTO region (id, name) VALUES (5, 'Asia');
INSERT INTO region (id, name) VALUES (6, 'Africa');
INSERT INTO region (id, name) VALUES (7, 'Oceania');
INSERT INTO region (id, name) VALUES (8, 'Antarctica');

INSERT INTO client (region_id, first_name, last_name, email, create_at) VALUES(1, 'Apps1', 'Angulars1', 'angular1@app.com', '2020-11-05');
INSERT INTO client (region_id, first_name, last_name, email, create_at) VALUES(1, 'Apps2', 'Angulars2', 'angular2@app.com', '2020-11-05');
INSERT INTO client (region_id, first_name, last_name, email, create_at) VALUES(1, 'Apps3', 'Angulars3', 'angular3@app.com', '2020-11-05');
INSERT INTO client (region_id, first_name, last_name, email, create_at) VALUES(1, 'Apps4', 'Angulars4', 'angular4@app.com', '2020-11-05');
INSERT INTO client (region_id, first_name, last_name, email, create_at) VALUES(1, 'Apps5', 'Angulars5', 'angular5@app.com', '2020-11-05');
INSERT INTO client (region_id, first_name, last_name, email, create_at) VALUES(1, 'Apps6', 'Angulars6', 'angular6@app.com', '2020-11-05');
INSERT INTO client (region_id, first_name, last_name, email, create_at) VALUES(1, 'Apps7', 'Angulars7', 'angular7@app.com', '2020-11-05');
INSERT INTO client (region_id, first_name, last_name, email, create_at) VALUES(1, 'Apps8', 'Angulars8', 'angular8@app.com', '2020-11-05');
INSERT INTO client (region_id, first_name, last_name, email, create_at) VALUES(1, 'Apps9', 'Angulars9', 'angular9@app.com', '2020-11-05');
INSERT INTO client (region_id, first_name, last_name, email, create_at) VALUES(1, 'Apps10', 'Angulars10', 'angular10@app.com', '2020-11-05');
INSERT INTO client (region_id, first_name, last_name, email, create_at) VALUES(1, 'Apps11', 'Angulars11', 'angular11@app.com', '2020-11-05');

/* We create some users with their roles */
INSERT INTO `user` (username, password, enabled, first_name, last_name, email) VALUES ('admin1','$2a$10$C3Uln5uqnzx/GswADURJGOIdBqYrly9731fnwKDaUdBkt/M3qvtLq',1, 'Admin1', 'User1','admin1@gmail.com');
INSERT INTO `user` (username, password, enabled, first_name, last_name, email) VALUES ('admin','$2a$10$RmdEsvEfhI7Rcm9f/uZXPebZVCcPC7ZXZwV51efAvMAp1rIaRAfPK',1, 'Admin', 'User','admin@gmail.com');

INSERT INTO `role` (name) VALUES ('ROLE_USER');
INSERT INTO `role` (name) VALUES ('ROLE_ADMIN');

INSERT INTO `user_role` (user_id, role_id) VALUES (1, 1);
INSERT INTO `user_role` (user_id, role_id) VALUES (2, 2);
INSERT INTO `user_role` (user_id, role_id) VALUES (2, 1);

/* Populate table product */
INSERT INTO product (name, price, create_at) VALUES('Panasonic Screen LCD', 259990, NOW());
INSERT INTO product (name, price, create_at) VALUES('Sony Camara digital DSC-W320B', 123490, NOW());
INSERT INTO product (name, price, create_at) VALUES('Apple iPod shuffle', 1499990, NOW());
INSERT INTO product (name, price, create_at) VALUES('Sony Notebook Z110', 37990, NOW());
INSERT INTO product (name, price, create_at) VALUES('Hewlett Packard Multifuncional F2280', 69990, NOW());
INSERT INTO product (name, price, create_at) VALUES('Bianchi Bicicleta Aro 26', 69990, NOW());
INSERT INTO product (name, price, create_at) VALUES('Mica Comoda 5 Cajones', 299990, NOW());

/* we create some bill */
INSERT INTO bill (description, observation, client_id, create_at) VALUES('Invoice office equipment', null, 1, NOW());
INSERT INTO bill (description, observation, client_id, create_at) VALUES('Bicycle Invoice', 'Any important note!', 1, NOW());

INSERT INTO invoice (quantity, invoice_id, product_id) VALUES(1, 1, 1);
INSERT INTO invoice (quantity, invoice_id, product_id) VALUES(2, 1, 2);
INSERT INTO invoice (quantity, invoice_id, product_id) VALUES(1, 1, 3);
INSERT INTO invoice (quantity, invoice_id, product_id) VALUES(1, 1, 4);
INSERT INTO invoice (quantity, invoice_id, product_id) VALUES(3, 2, 5);

