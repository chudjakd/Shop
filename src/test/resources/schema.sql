
DROP TABLE IF EXISTS customer;
CREATE TABLE customer (
  id int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  email varchar(45) NOT NULL,
  address varchar(45) NOT NULL
);

DROP TABLE IF EXISTS seller;
CREATE TABLE seller (
  id int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  email varchar(45) NOT NULL,
  address varchar(45) NOT NULL
);

DROP TABLE IF EXISTS product;
CREATE TABLE product (
  id int(11) NOT NULL AUTO_INCREMENT,
  seller_id int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `info` varchar(80) NOT NULL,
  value double NOT NULL,
  created_at datetime NOT NULL,
  count int(11) NOT NULL
);

DROP TABLE IF EXISTS customer_account;
CREATE TABLE customer_account (
  id int(11) NOT NULL AUTO_INCREMENT,
  customer_id int(11) NOT NULL,
  money double NOT NULL
  );

DROP TABLE IF EXISTS bought_product;
CREATE TABLE bought_product (
  id int(11) NOT NULL AUTO_INCREMENT,
  customer_id int(11) NOT NULL,
  product_id int(11) NOT NULL,
  count int(11) NOT NULL,
  bought_at datetime NOT NULL
  );