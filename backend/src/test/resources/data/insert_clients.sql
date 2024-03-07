INSERT INTO client (email, birth_date, client_type, created_date, modified_date, last_name, name, password, phone_number, role, status)
VALUES ('email1@gmail.com', '1990-01-01', 'PERSON', '2019-01-01', '2019-01-01', 'LastName1', 'Name1', '$2a$10$GYhcrzQX9rd2Zth5KENEueaRvKWB9I/OAqgLhZAyMb5L6Bfo7tZFu', '123456789', 'CLIENT', 'ACTIVE'),
       ('email2@gmail.com', '1990-02-02', 'PERSON', '2019-02-02', '2019-02-02', 'LastName2', 'Name2', '$2a$10$c5K7OKiSblBOsqWPE/ORxet2fQNpf1HCELXpbfhaKIg8JGWF13cgC', '123456789', 'CLIENT', 'ACTIVE'),
       ('email3@gmail.com', null, 'COMPANY', '2019-03-03', '2019-03-03', null, 'Name3', '$2a$10$xM6MRsb9GSOpUJ54iIiiHONcPnoO17a/C97jUW9dq7dZAu24yoScy', '123456789', 'CLIENT', 'ACTIVE');
