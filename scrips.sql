SELECT * from student;
SELECT name from student;
SELECT * from student where age > 10 and age < 20;
SELECT * from student where lower(name) like '%о%';
SELECT * from student where age > student.id;
SELECT * from student ORDER BY age;