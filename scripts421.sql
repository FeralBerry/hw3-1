create table students (
    age INTEGER CHECK ( age > 16 ),
    age INTEGER DEFAULT 20,
    name VARCHAR(50) NOT NULL,
    name VARCHAR(50) UNIQUE
                      );
create table faculties ADD UNIQUE unique_index (`color`, `name`);