CREATE TABLE DOCTOR (
                           id INT PRIMARY KEY AUTO_INCREMENT,
                           firstname VARCHAR(255) NOT NULL,
                           lastname VARCHAR(255) NOT NULL,
                           medical_specialization VARCHAR(255),
                           animal_specialization VARCHAR(255),
                           salary DOUBLE,
                           nip VARCHAR(20),
                           is_hired BOOLEAN
);

