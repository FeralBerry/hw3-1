SELECT * from students;
SELECT name from students;
SELECT * from students where age > 10 and age < 20;
SELECT * from students where lower(name) like '%о%';
SELECT * from students where age > student.id;
SELECT * from students ORDER BY age;