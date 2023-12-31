

--DROP TABLE IF EXISTS address;

CREATE TABLE IF NOT EXISTS address (
 id INT AUTO_INCREMENT  PRIMARY KEY,
 country VARCHAR(255),
 state VARCHAR(255),
 street VARCHAR(255)
);


--DROP TABLE IF EXISTS employee;

CREATE TABLE IF NOT EXISTS employee (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  first_name VARCHAR(250) NOT NULL,
  last_name VARCHAR(250) NOT NULL,
  career VARCHAR(250) DEFAULT NULL,
  salary INT NOT NULL,
  address_id INT,
  CONSTRAINT FK_ADDRESS_ID FOREIGN KEY (address_id) REFERENCES address(id)
);

