
DROP TABLE IF EXISTS customer;
CREATE TABLE customer (
   id int NOT NULL AUTO_INCREMENT,
  name varchar(100) NOT NULL,
  email varchar(45) NOT NULL,
  address varchar(45) NOT NULL
);
DROP TABLE IF EXISTS product;
CREATE TABLE product (
  id int NOT NULL AUTO_INCREMENT,
  seller_id int  NOT NULL,
  name varchar(100)  NOT NULL,
  info varchar(100)  NOT NULL,
  value double NOT NULL,
  count int NOT NULL
);
DROP TABLE IF EXISTS seller;
CREATE TABLE seller (
  id int  NOT NULL AUTO_INCREMENT,
  name varchar(100)  NOT NULL,
  email varchar(100)  NOT NULL,
  address varchar(100)  NOT NULL
);