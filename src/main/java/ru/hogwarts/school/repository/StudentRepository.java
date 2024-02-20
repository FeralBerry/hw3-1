package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.models.Student;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
    List<Student> findAllByAge(int age);
    Student findStudentById(long id);
    List<Student> findAllByAgeBetween(int min, int max);
    List<Student> findByFaculty_Id(long id);
}
