CREATE TABLE car (
    id INTEGER PRIMARY KEY UNIQUE,
    stamp VARCHAR(50) NOT NULL,
    model VARCHAR(20) NOT NULL,
    price NUMERIC(10,2) CHECK (price > 0)
);
CREATE TABLE human (
    name VARCHAR(50) NOT NULL,
    age INTEGER CHECK (age > 0),
    drivers_license BIT,
    car_id INTEGER,
    FOREIGN KEY (car_id)  REFERENCES car (id)
                   );