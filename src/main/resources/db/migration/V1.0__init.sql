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
    ('Michael Johnson', 'michael@example.com', '$2a$04$TgU06gllp/aPtNOitD/Du.v0aClpht8FUrzsmcAE3g9VL.ARiqTgK', true, 'USER'),
    ('Emily White', 'emily@example.com', '$2a$04$TgU06gllp/aPtNOitD/Du.v0aClpht8FUrzsmcAE3g9VL.ARiqTgK', true, 'USER'),
    ('David Brown', 'david@example.com', '$2a$04$TgU06gllp/aPtNOitD/Du.v0aClpht8FUrzsmcAE3g9VL.ARiqTgK', true, 'USER'),
    ('Sophia Johnson', 'sophia@example.com', '$2a$04$TgU06gllp/aPtNOitD/Du.v0aClpht8FUrzsmcAE3g9VL.ARiqTgK', true, 'USER'),
    ('Oliver Smith', 'oliver@example.com', '$2a$04$TgU06gllp/aPtNOitD/Du.v0aClpht8FUrzsmcAE3g9VL.ARiqTgK', true, 'USER'),
    ('Emma Davis', 'emma@example.com', '$2a$04$TgU06gllp/aPtNOitD/Du.v0aClpht8FUrzsmcAE3g9VL.ARiqTgK', true, 'USER'),
    ('William Martinez', 'william@example.com', '$2a$04$TgU06gllp/aPtNOitD/Du.v0aClpht8FUrzsmcAE3g9VL.ARiqTgK', true, 'USER'),
    ('Ava Johnson', 'ava@example.com', '$2a$04$TgU06gllp/aPtNOitD/Du.v0aClpht8FUrzsmcAE3g9VL.ARiqTgK', true, 'USER');

-- Service table entries:
INSERT INTO service (user_id, name, description, cost, active)
VALUES
    (1, 'Web Development', 'Custom web development services', 500, true),
    (2, 'Mobile App Development', 'Native and cross-platform mobile app development', 1000, true),
    (3, 'Graphic Design', 'Logo design, branding, and visual identity services', 250, true),
    (4, 'SEO Consultation', 'Search engine optimization services', 150, true),
    (5, 'Content Writing', 'Quality content writing for blogs and websites', 75, true),
    (6, 'Social Media Marketing', 'Strategies for boosting social media presence', 200, true),
    (7, 'E-commerce Development', 'Build scalable and secure e-commerce platforms', 800, true),
    (8, 'UI/UX Design', 'Create intuitive and user-friendly user interfaces', 300, true),
    (9, 'Data Analytics', 'Gain insights from data for informed decisions', 400, true),
    (10, 'Cloud Consulting', 'Assistance in adopting cloud technologies', 350, true);

-- Booking table entries:
INSERT INTO booking (user_id, application_service_id, date_time, notes)
VALUES
    (1, 1, '2023-07-15 10:00:00', 'Need website development for my business'),
    (2, 2, '2023-07-16 14:30:00', 'Looking to develop an iOS and Android app'),
    (3, 3, '2023-07-17 11:15:00', 'Require a new logo for my startup'),
    (4, 4, '2023-07-18 09:30:00', 'Interested in improving SEO for my website'),
    (5, 5, '2023-07-19 16:45:00', 'Looking for engaging content for my blog'),
    (6, 6, '2023-07-20 13:00:00', 'Plan to boost social media presence'),
    (7, 7, '2023-07-21 15:30:00', 'E-commerce platform development consultation'),
    (8, 8, '2023-07-22 12:45:00', 'Designing user interfaces for a new project'),
    (9, 9, '2023-07-23 10:30:00', 'Data analytics for business insights'),
    (10, 10, '2023-07-24 09:00:00', 'Consultation on adopting cloud technologies');
