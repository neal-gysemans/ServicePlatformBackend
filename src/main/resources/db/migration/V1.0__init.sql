-- Create the User table
CREATE TABLE user (
                      id BIGINT PRIMARY KEY AUTO_INCREMENT,
                      name VARCHAR(100) NOT NULL,
                      email VARCHAR(100) NOT NULL UNIQUE,
                      active BOOLEAN NOT NULL,
                      role VARCHAR(20) NOT NULL
);

-- Create the Service table
CREATE TABLE service (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         userId BIGINT NOT NULL,
                         name VARCHAR(100) NOT NULL,
                         description VARCHAR(500) NOT NULL,
                         cost VARCHAR(50) NOT NULL,
                         active BOOLEAN NOT NULL,
                         FOREIGN KEY (userId) REFERENCES user(id)
);

-- Create the Booking table
CREATE TABLE booking (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         userId BIGINT NOT NULL,
                         serviceId BIGINT NOT NULL,
                         dateTime DATETIME NOT NULL,
                         notes VARCHAR(500),
                         FOREIGN KEY (userId) REFERENCES user(id),
                         FOREIGN KEY (serviceId) REFERENCES service(id)
);

-- User table entries:
INSERT INTO user (name, email, active, role)
VALUES ('John Doe', 'john@example.com', true, 'USER');

INSERT INTO user (name, email, active, role)
VALUES ('Jane Smith', 'jane@example.com', true, 'ADMIN');

INSERT INTO user (name, email, active, role)
VALUES ('Michael Johnson', 'michael@example.com', true, 'USER');

-- Service table entries:
INSERT INTO service (userId, name, description, cost, active)
VALUES (1, 'Web Development', 'Custom web development services', '500', true);

INSERT INTO service (userId, name, description, cost, active)
VALUES (2, 'Mobile App Development', 'Native and cross-platform mobile app development', '1000', true);

INSERT INTO service (userId, name, description, cost, active)
VALUES (3, 'Graphic Design', 'Logo design, branding, and visual identity services', '250', true);

-- Booking table entries:
INSERT INTO booking (userId, serviceId, dateTime, notes)
VALUES (1, 1, '2023-07-15 10:00:00', 'Need website development for my business');

INSERT INTO booking (userId, serviceId, dateTime, notes)
VALUES (2, 2, '2023-07-16 14:30:00', 'Looking to develop an iOS and Android app');

INSERT INTO booking (userId, serviceId, dateTime, notes)
VALUES (3, 3, '2023-07-17 11:15:00', 'Require a new logo for my startup');