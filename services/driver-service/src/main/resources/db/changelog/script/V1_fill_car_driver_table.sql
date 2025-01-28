INSERT INTO driver_car (driver_id, car_id)
VALUES
  ((SELECT id FROM drivers WHERE number = '1234567890'), (SELECT id FROM cars WHERE number = 'A1234')),
  ((SELECT id FROM drivers WHERE number = '1234567891'), (SELECT id FROM cars WHERE number = 'B1234')),
  ((SELECT id FROM drivers WHERE number = '1234567892'), (SELECT id FROM cars WHERE number = 'C1234')),
  ((SELECT id FROM drivers WHERE number = '1234567893'), (SELECT id FROM cars WHERE number = 'D1234')),
  ((SELECT id FROM drivers WHERE number = '1234567894'), (SELECT id FROM cars WHERE number = 'E1234')),
  ((SELECT id FROM drivers WHERE number = '1234567895'), (SELECT id FROM cars WHERE number = 'F1234')),
  ((SELECT id FROM drivers WHERE number = '1234567896'), (SELECT id FROM cars WHERE number = 'G1234')),
  ((SELECT id FROM drivers WHERE number = '1234567897'), (SELECT id FROM cars WHERE number = 'H1234')),
  ((SELECT id FROM drivers WHERE number = '1234567898'), (SELECT id FROM cars WHERE number = 'I1234')),
  ((SELECT id FROM drivers WHERE number = '1234567899'), (SELECT id FROM cars WHERE number = 'J1234')),
  ((SELECT id FROM drivers WHERE number = '1234567800'), (SELECT id FROM cars WHERE number = 'K1234')),
  ((SELECT id FROM drivers WHERE number = '1234567801'), (SELECT id FROM cars WHERE number = 'L1234')),
  ((SELECT id FROM drivers WHERE number = '1234567802'), (SELECT id FROM cars WHERE number = 'M1234')),
  ((SELECT id FROM drivers WHERE number = '1234567803'), (SELECT id FROM cars WHERE number = 'N1234')),
  ((SELECT id FROM drivers WHERE number = '1234567890'), (SELECT id FROM cars WHERE number = 'O1234'));