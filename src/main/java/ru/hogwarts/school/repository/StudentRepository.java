package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.models.Student;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
    List<Student> findAllByAge(int age);
    Student findStudentById(long id);
    List<Student> findAllByAgeBetween(int min, int max);
    List<Student> findByFaculty_Id(long id);
    @Query(value = "SELECT COUNT(*) FROM students",nativeQuery = true)
    Long countStudents();
    @Query(value = "SELECT AVG(age) FROM students",nativeQuery = true)
    Double middleAgeStudents();
    @Query(value = "SELECT * FROM students ORDER BY id DESC LIMIT 5",nativeQuery = true)
    List<Student> lastFiveStudents();
}
