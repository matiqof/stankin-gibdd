CREATE TABLE client
(
    client_id                UUID         NOT NULL,
    license_id               UUID,
    full_name                VARCHAR(255) NOT NULL,
    date_of_birth            DATE         NOT NULL,
    phone                    VARCHAR(11)  NOT NULL,
    address                  VARCHAR(255) NOT NULL,
    passport_number          VARCHAR(10)  NOT NULL,
    passport_issue_date      DATE         NOT NULL,
    passport_department_code INT          NOT NULL,
    password                 VARCHAR(60),
    role                     VARCHAR(15)  NOT NULL,
    PRIMARY KEY (client_id),
    UNIQUE (passport_number),
    UNIQUE (phone),
    CONSTRAINT client_chk_2 CHECK (passport_issue_date <= CURRENT_DATE)
);

CREATE TABLE driving_license
(
    license_id      UUID        NOT NULL,
    client_id       UUID        NOT NULL,
    license_number  VARCHAR(10) NOT NULL,
    issue_date      DATE        NOT NULL DEFAULT CURRENT_DATE,
    expiration_date DATE        NOT NULL,
    department_code VARCHAR(45) NOT NULL,
    PRIMARY KEY (license_id),
    UNIQUE (client_id),
    CONSTRAINT client_id FOREIGN KEY (client_id) REFERENCES client (client_id),
    CONSTRAINT driving_license_chk_1 CHECK (issue_date <= CURRENT_DATE),
    CONSTRAINT driving_license_chk_2 CHECK (expiration_date >= CURRENT_DATE),
    CONSTRAINT driving_license_chk_3 CHECK (expiration_date > issue_date)
);

CREATE TABLE accident
(
    accident_id UUID         NOT NULL,
    location    VARCHAR(255) NOT NULL,
    description VARCHAR(255)          DEFAULT NULL,
    date        DATE         NOT NULL DEFAULT CURRENT_DATE,
    time        TIME         NOT NULL DEFAULT CURRENT_TIME,
    PRIMARY KEY (accident_id)
);

CREATE TABLE category
(
    category_id   UUID       NOT NULL,
    category_name VARCHAR(3) NOT NULL DEFAULT 'B',
    PRIMARY KEY (category_id),
    CONSTRAINT category_chk_1 CHECK (category_name IN
                                     ('A', 'A1', 'B', 'B1', 'C', 'C1', 'D', 'D1', 'BE', 'CE', 'C1E', 'DE', 'D1E', 'M',
                                      'Tm', 'Tb'))
);

CREATE TABLE driving_license_category
(
    license_id  UUID NOT NULL,
    category_id UUID NOT NULL,
    PRIMARY KEY (license_id, category_id),
    CONSTRAINT license_id_category FOREIGN KEY (license_id) REFERENCES driving_license (license_id),
    CONSTRAINT category_id_license FOREIGN KEY (category_id) REFERENCES category (category_id)
);

CREATE TABLE vehicle
(
    vehicle_id            UUID         NOT NULL,
    client_id             UUID         NOT NULL,
    model                 VARCHAR(255) NOT NULL,
    manufacturer          VARCHAR(255) NOT NULL,
    year_of_manufacture   INT          NOT NULL,
    color                 VARCHAR(255) NOT NULL,
    mileage               INT          NOT NULL,
    engine_volume         FLOAT        NOT NULL,
    horsepower            INT          NOT NULL,
    registration_number   VARCHAR(9)   NOT NULL,
    registration_date     DATE         NOT NULL,
    registration_location VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (vehicle_id),
    UNIQUE (registration_number),
    CONSTRAINT client_id FOREIGN KEY (client_id) REFERENCES client (client_id),
    CONSTRAINT vehicle_chk_1 CHECK (year_of_manufacture <= EXTRACT(YEAR FROM CURRENT_DATE)),
    CONSTRAINT vehicle_chk_2 CHECK (registration_date <= CURRENT_DATE)
);

CREATE TABLE accident_composition
(
    accident_id UUID NOT NULL,
    vehicle_id  UUID NOT NULL,
    PRIMARY KEY (accident_id, vehicle_id),
    CONSTRAINT accident_id FOREIGN KEY (accident_id) REFERENCES accident (accident_id),
    CONSTRAINT vehicle_id_accident FOREIGN KEY (vehicle_id) REFERENCES vehicle (vehicle_id)
);

CREATE TABLE fine
(
    fine_id     UUID         NOT NULL,
    vehicle_id  UUID         NOT NULL,
    date        DATE         NOT NULL DEFAULT CURRENT_DATE,
    time        TIME         NOT NULL DEFAULT CURRENT_TIME,
    location    VARCHAR(255) NOT NULL,
    amount      INT          NOT NULL,
    type        VARCHAR(255) NOT NULL,
    description VARCHAR(255)          DEFAULT NULL,
    article     VARCHAR(255) NOT NULL,
    PRIMARY KEY (fine_id),
    CONSTRAINT vehicle_id FOREIGN KEY (vehicle_id) REFERENCES vehicle (vehicle_id),
    CONSTRAINT fine_chk_1 CHECK (date <= CURRENT_DATE)
    );

CREATE TABLE password_reset_token
(
    password_reset_token_id UUID      NOT NULL,
    token                   UUID      NOT NULL,
    client_id               UUID      NOT NULL,
    expiry_date             TIMESTAMP NOT NULL,
    PRIMARY KEY (password_reset_token_id),
    UNIQUE (client_id),
    UNIQUE (token),
    CONSTRAINT client_id FOREIGN KEY (client_id) REFERENCES client (client_id)
);

CREATE TABLE persistent_logins
(
    username  VARCHAR(64) NOT NULL,
    series    VARCHAR(64) PRIMARY KEY,
    token     VARCHAR(64) NOT NULL,
    last_used TIMESTAMP   NOT NULL
);