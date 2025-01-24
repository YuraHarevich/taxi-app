INSERT INTO car (id, color, number, brand) VALUES
(NEXTVAL('car_seq'), 'Red', '1234 AB-7', 'Toyota'),
(NEXTVAL('car_seq'), 'Blue', '2345 BC-6', 'Honda'),
(NEXTVAL('car_seq'), 'Green', '3456 CD-5', 'Ford'),
(NEXTVAL('car_seq'), 'Yellow', '4567 DE-4', 'BMW'),
(NEXTVAL('car_seq'), 'White', '5678 EF-3', 'Audi'),
(NEXTVAL('car_seq'), 'Black', '6789 FG-2', 'Mercedes'),
(NEXTVAL('car_seq'), 'Silver', '7890 GH-7', 'Volkswagen'),
(NEXTVAL('car_seq'), 'Orange', '8901 HI-6', 'Hyundai'),
(NEXTVAL('car_seq'), 'Purple', '9012 IJ-5', 'Nissan'),
(NEXTVAL('car_seq'), 'Pink', '0123 JK-4', 'Kia'),
(NEXTVAL('car_seq'), 'Brown', '1111 LM-7', 'Mazda'),
(NEXTVAL('car_seq'), 'Cyan', '2222 MN-6', 'Chevrolet'),
(NEXTVAL('car_seq'), 'Beige', '3333 NO-5', 'Subaru'),
(NEXTVAL('car_seq'), 'Gold', '4444 OP-4', 'Peugeot'),
(NEXTVAL('car_seq'), 'Teal', '5555 PQ-3', 'Lexus');


INSERT INTO driver (id, name, surname, email, rate, car_id) VALUES
(NEXTVAL('driver_seq'), 'John', 'Doe', 'john.doe@example.com', 4.5, 1),
(NEXTVAL('driver_seq'), 'Jane', 'Smith', 'jane.smith@example.com', 4.2, 2),
(NEXTVAL('driver_seq'), 'Alex', 'Johnson', 'alex.johnson@example.com', 4.8, 3),
(NEXTVAL('driver_seq'), 'Chris', 'Lee', 'chris.lee@example.com', 4.3, 4),
(NEXTVAL('driver_seq'), 'Michael', 'Brown', 'michael.brown@example.com', 4.6, 5),
(NEXTVAL('driver_seq'), 'Emily', 'Davis', 'emily.davis@example.com', 4.7, 6),
(NEXTVAL('driver_seq'), 'David', 'Wilson', 'david.wilson@example.com', 4.0, 7),
(NEXTVAL('driver_seq'), 'Sarah', 'Martinez', 'sarah.martinez@example.com', 4.4, 8),
(NEXTVAL('driver_seq'), 'Daniel', 'Moore', 'daniel.moore@example.com', 4.5, 9),
(NEXTVAL('driver_seq'), 'Sophia', 'Taylor', 'sophia.taylor@example.com', 4.1, 10),
(NEXTVAL('driver_seq'), 'Matthew', 'Anderson', 'matthew.anderson@example.com', 4.3, 11),
(NEXTVAL('driver_seq'), 'Olivia', 'Thomas', 'olivia.thomas@example.com', 4.2, 12),
(NEXTVAL('driver_seq'), 'Lucas', 'Jackson', 'lucas.jackson@example.com', 4.8, 13),
(NEXTVAL('driver_seq'), 'Mia', 'White', 'mia.white@example.com', 4.6, 14),
(NEXTVAL('driver_seq'), 'Ethan', 'Harris', 'ethan.harris@example.com', 4.7, 15);