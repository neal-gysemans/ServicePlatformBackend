-- Create the User table
CREATE TABLE user (
                      id BIGINT PRIMARY KEY AUTO_INCREMENT,
                      name VARCHAR(100) NOT NULL,
                      email VARCHAR(100) NOT NULL UNIQUE,
                      password VARCHAR(100) NOT NULL,
                      active BOOLEAN NOT NULL,
                      role VARCHAR(20) NOT NULL
);

-- Create the Service table
CREATE TABLE service (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         user_id BIGINT NOT NULL,
                         name VARCHAR(100) NOT NULL,
                         description VARCHAR(500) NOT NULL,
                         cost float NOT NULL,
                         active BOOLEAN NOT NULL,
                         FOREIGN KEY (user_id) REFERENCES user(id)
);

-- Create the Booking table
CREATE TABLE booking (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         user_id BIGINT NOT NULL,
                         application_service_id BIGINT NOT NULL,
                         date_time DATETIME NOT NULL,
                         notes VARCHAR(500),
                         FOREIGN KEY (user_id) REFERENCES user(id),
                         FOREIGN KEY (application_service_id) REFERENCES service(id)
);

-- User table entries:
INSERT INTO user (name, email, password, active, role)
VALUES
    ('John Doe', 'john@example.com', '$2a$04$TgU06gllp/aPtNOitD/Du.v0aClpht8FUrzsmcAE3g9VL.ARiqTgK', true, 'USER'),
    ('Jane Smith', 'jane@example.com', '$2a$04$TgU06gllp/aPtNOitD/Du.v0aClpht8FUrzsmcAE3g9VL.ARiqTgK', true, 'ADMIN'),
    ('Michael Johnson', 'michael@example.com', '$2a$04$TgU06gllp/aPtNOitD/Du.v0aClpht8FUrzsmcAE3g9VL.ARiqTgK', true, 'USER');

-- Service table entries:
INSERT INTO service (user_id, name, description, cost, active)
VALUES
    (1, 'Web Development', 'Custom web development services', 500, true),
    (2, 'Mobile App Development', 'Native and cross-platform mobile app development', 1000, true),
    (3, 'Graphic Design', 'Logo design, branding, and visual identity services', 250, true);

-- Booking table entries:
INSERT INTO booking (user_id, application_service_id, date_time, notes)
VALUES
    (1, 1, '2023-07-15 10:00:00', 'Need website development for my business'),
    (2, 2, '2023-07-16 14:30:00', 'Looking to develop an iOS and Android app'),
    (3, 3, '2023-07-17 11:15:00', 'Require a new logo for my startup');
