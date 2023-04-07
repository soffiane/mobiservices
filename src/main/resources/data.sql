DROP TABLE IF EXISTS client;

CREATE TABLE client (
  id int NOT NULL AUTO_INCREMENT,
  name varchar(45) DEFAULT NULL,
  email varchar(45) DEFAULT NULL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS bus;

CREATE TABLE bus (
  id int NOT NULL AUTO_INCREMENT,
  route varchar(45) DEFAULT NULL,
  seats int DEFAULT 0,
  departure_time TIME,
  price DOUBLE DEFAULT 0.0,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS reservation;

CREATE TABLE reservation (
  id int NOT NULL AUTO_INCREMENT,
  reservationDate DATE,
  bus_id int NOT NULL,
  client_id int NOT NULL,
  seats int DEFAULT 0,
  PRIMARY KEY (id),
  FOREIGN KEY (bus_id) REFERENCES bus(id),
  FOREIGN KEY (client_id) REFERENCES client(id)
);

DROP TABLE IF EXISTS bill;

CREATE TABLE bill (
  id int NOT NULL AUTO_INCREMENT,
  reservation_id int,
  paymentMethod varchar(45) DEFAULT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (reservation_id) REFERENCES reservation(id)
);

INSERT INTO Client VALUES
('1','Soffiane','soffiane.boudissa@gmail.com');

INSERT INTO Bus VALUES
('1','Saint-Léger - Coulommiers',5,'10:00:00',4.0),
('2','Creteil - Servon',8,'09:30:00',6.5),
('3','Villeneuve-Saint-Georges',14,'14:15:00',7.0);