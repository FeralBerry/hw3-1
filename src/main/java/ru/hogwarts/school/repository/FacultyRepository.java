package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.models.Student;

import java.util.List;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty,Long> {
    Faculty findByNameIgnoreCase(String name);
    Faculty findByColorIgnoreCase(String color);
    List<Faculty> findAllByNameContainsIgnoreCase(String part);
    Faculty findById(long id);
}
