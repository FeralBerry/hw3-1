package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.models.Faculty;

import java.util.List;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty,Long> {
    List<Faculty> findByNameIgnoreCase(String name);
    List<Faculty> findByColorIgnoreCase(String color);
    List<Faculty> findAllByNameContainsIgnoreCase(String part);
    Faculty findFacultyById(Long id);
}
