CREATE TABLE client
(
    client_number            UUID         NOT NULL,
    license_number           UUID,
    full_name                VARCHAR(45)  NOT NULL,
    date_of_birth            DATE         NOT NULL,
    phone                    VARCHAR(11)  NOT NULL,
    address                  VARCHAR(100) NOT NULL,
    passport                 VARCHAR(10)  NOT NULL,
    passport_issue_date      DATE         NOT NULL,
    passport_department_code INT          NOT NULL,
    password                 VARCHAR(60)  NOT NULL,
    role                     VARCHAR(10)  NOT NULL,
    PRIMARY KEY (client_number),
    UNIQUE (passport),
    UNIQUE (phone),
    CONSTRAINT client_chk_2 CHECK (passport_issue_date <= CURRENT_DATE)
);

CREATE TABLE driving_license
(
    license_number  UUID         NOT NULL,
    client_number   UUID         NOT NULL,
    issue_date      DATE         NOT NULL DEFAULT '2024-01-01',
    expiration_date DATE         NOT NULL,
    department_code VARCHAR(45)  NOT NULL,
    PRIMARY KEY (license_number),
    UNIQUE (client_number),
    CONSTRAINT client_number FOREIGN KEY (client_number) REFERENCES client (client_number),
    CONSTRAINT driving_license_chk_1 CHECK (issue_date <= CURRENT_DATE),
    CONSTRAINT driving_license_chk_2 CHECK (expiration_date >= CURRENT_DATE),
    CONSTRAINT driving_license_chk_3 CHECK (expiration_date > issue_date)
);

CREATE TABLE accident
(
    accident_number UUID         NOT NULL,
    time            TIME         NOT NULL,
    location        VARCHAR(256) NOT NULL,
    description     VARCHAR(256)          DEFAULT NULL,
    date            DATE         NOT NULL DEFAULT CURRENT_DATE,
    PRIMARY KEY (accident_number)
);

CREATE TABLE category
(
    category_number UUID         NOT NULL,
    category_name   VARCHAR(45)  NOT NULL DEFAULT 'B',
    PRIMARY KEY (category_number),
    CONSTRAINT category_chk_1 CHECK (category_name IN
                                     ('A', 'A1', 'B', 'B1', 'C', 'C1', 'D', 'D1', 'BE', 'CE', 'C1E', 'DE', 'D1E', 'M',
                                      'Tm', 'Tb'))
);

CREATE TABLE driving_license_category
(
    license_number  UUID NOT NULL,
    category_number UUID NOT NULL,
    PRIMARY KEY (license_number, category_number),
    CONSTRAINT license_number_category FOREIGN KEY (license_number) REFERENCES driving_license (license_number),
    CONSTRAINT category_number_license FOREIGN KEY (category_number) REFERENCES category (category_number)
);

CREATE TABLE vehicle
(
    vehicle_number        UUID         NOT NULL,
    client_number         UUID         NOT NULL,
    model                 VARCHAR(45)  NOT NULL,
    manufacturer          VARCHAR(45)  NOT NULL,
    year_of_manufacture   INT          NOT NULL,
    color                 VARCHAR(45)  NOT NULL,
    mileage               INT          NOT NULL,
    engine_volume         FLOAT        NOT NULL,
    horsepower            INT          NOT NULL,
    registration_number   VARCHAR(45)  NOT NULL,
    registration_date     DATE         NOT NULL,
    registration_location VARCHAR(45)  DEFAULT NULL,
    PRIMARY KEY (vehicle_number),
    UNIQUE (registration_number),
    CONSTRAINT client_number FOREIGN KEY (client_number) REFERENCES client (client_number),
    CONSTRAINT vehicle_chk_1 CHECK (year_of_manufacture <= EXTRACT(YEAR FROM CURRENT_DATE)),
    CONSTRAINT vehicle_chk_2 CHECK (registration_date <= CURRENT_DATE)
);

CREATE TABLE accident_composition
(
    accident_number UUID NOT NULL,
    vehicle_number  UUID NOT NULL,
    PRIMARY KEY (accident_number, vehicle_number),
    CONSTRAINT accident_number FOREIGN KEY (accident_number) REFERENCES accident (accident_number),
    CONSTRAINT vehicle_number_accident FOREIGN KEY (vehicle_number) REFERENCES vehicle (vehicle_number)
);

CREATE TABLE fine
(
    fine_number    UUID         NOT NULL,
    vehicle_number UUID         NOT NULL,
    date           DATE         NOT NULL,
    time           TIME         NOT NULL,
    location       VARCHAR(45)  NOT NULL,
    amount         INT          NOT NULL,
    type           VARCHAR(45)  NOT NULL,
    description    VARCHAR(45)  DEFAULT NULL,
    article        VARCHAR(45)  NOT NULL,
    PRIMARY KEY (fine_number),
    UNIQUE (vehicle_number),
    CONSTRAINT vehicle_number FOREIGN KEY (vehicle_number) REFERENCES vehicle (vehicle_number) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fine_chk_1 CHECK (date <= CURRENT_DATE)
    );

CREATE TABLE password_reset_token
(
    password_reset_token_number UUID         NOT NULL,
    token                       VARCHAR(255) NOT NULL,
    client_number               UUID         NOT NULL,
    expiry_date                 TIMESTAMP    NOT NULL,
    PRIMARY KEY (password_reset_token_number),
    UNIQUE (client_number),
    CONSTRAINT client_number FOREIGN KEY (client_number) REFERENCES client (client_number)
);