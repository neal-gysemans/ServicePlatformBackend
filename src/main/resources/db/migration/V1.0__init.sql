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
    ('Neal Gysemans', 'neal@admin.com', '$2a$10$2uvnvdmNR0Q6Dzbushnsn.xz.7pZvMAvXv1y2L5WDrZ1cSDGbIaZ2', true, 'ADMIN')