INSERT INTO driver_car (driver_id, car_id)
SELECT d.id, c.id
FROM drivers d
JOIN cars c ON d.name = 'John' AND c.number = '1234 AB-5'
UNION
SELECT d.id, c.id
FROM drivers d
JOIN cars c ON d.name = 'Jane' AND c.number = '1234 CD-6'
UNION
SELECT d.id, c.id
FROM drivers d
JOIN cars c ON d.name = 'Bob' AND c.number = '1234 GH-8'
UNION
SELECT d.id, c.id
FROM drivers d
JOIN cars c ON d.name = 'Charlie' AND c.number = '1234 IJ-9'
UNION
SELECT d.id, c.id
FROM drivers d
JOIN cars c ON d.name = 'David' AND c.number = '1234 KL-0'
UNION
SELECT d.id, c.id
FROM drivers d
JOIN cars c ON d.name = 'Frank' AND c.number = '1234 MN-1'
UNION
SELECT d.id, c.id
FROM drivers d
JOIN cars c ON d.name = 'Grace' AND c.number = '1234 UV-5'
UNION
SELECT d.id, c.id
FROM drivers d
JOIN cars c ON d.name = 'Ian' AND c.number = '1234 WX-6'
UNION
SELECT d.id, c.id
FROM drivers d
JOIN cars c ON d.name = 'Karen' AND c.number = '1234 YZ-7'
UNION
SELECT d.id, c.id
FROM drivers d
JOIN cars c ON d.name = 'Jack' AND c.number = '1234 CD-9';
