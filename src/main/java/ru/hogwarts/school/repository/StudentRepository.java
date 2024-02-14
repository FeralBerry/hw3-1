package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.models.Student;
@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
}
