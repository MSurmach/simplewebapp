CREATE TABLE employee
(
    employee_id   INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    first_name    VARCHAR(255) NOT NULL,
    last_name     VARCHAR(255) NOT NULL,
    department_id SMALLINT     NOT NULL,
    job_title     VARCHAR(255) NOT NULL,
    gender        VARCHAR(6)   NOT NULL,
    date_of_birth DATE         NOT NULL
);