create table students (
    age INTEGER CHECK ( age > 16 ) DEFAULT 20,
    name VARCHAR(50) NOT NULL UNIQUE,
                      );
create table faculties ADD UNIQUE unique_index (color, name);