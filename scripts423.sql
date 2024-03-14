SELECT faculties.name, students.faculty_id AS id, students.name, students.age
FROM faculties INNER JOIN students
                      ON faculties.id = students.faculty_id