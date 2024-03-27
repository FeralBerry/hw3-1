SELECT faculties.name, students.faculty_id AS id, students.name, students.age
FROM faculties INNER JOIN students
                    ON faculties.id = students.faculty_id;
SELECT avatars.filePath, avatars.student_id as id, avatars.fileSize, avatars.mediaType, avatars.data, students.name, students.age
FROM avatars INNER JOIN students
                    ON avatars.student_id = student.id WHERE avatars.filePath != NULL;

