package ru.hogwarts.school.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.FacultyNotFoundException;
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.models.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;

@Service
public class FacultyService {
    private  final FacultyRepository facultyRepository;
    private  final StudentRepository studentRepository;
    Logger logger = LoggerFactory.getLogger(FacultyService.class);
    public FacultyService(FacultyRepository facultyRepository,StudentRepository studentRepository){
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }
    public Faculty create(Faculty faculty){
        logger.info("Создан новый факультет: " + faculty.getName() + " с цветом " + faculty.getColor());
        faculty.setId(null);
        return facultyRepository.save(faculty);
    }
    public List<Faculty> read(){
        logger.info("Вызван метод просмотра всех факультетов");
        return facultyRepository.findAll();
    }
    public void delete(Long id){
        if(get(id) != null){
            logger.warn("Удаление факультета");
            facultyRepository.deleteById(id);
        } else {
            logger.error("Удаление не существующего факультета");
        }
    }
    public Faculty update(Long id,Faculty faculty){
        logger.info("Факультет с id " + id + "был изменен");
        return facultyRepository.findById(id)
                .map(oldFaculty -> {
                    oldFaculty.setName(faculty.getName());
                    oldFaculty.setColor(faculty.getColor());
                    return facultyRepository.save(oldFaculty);
                }).orElseThrow(() -> new FacultyNotFoundException(id));
    }
    public List<Faculty> findByColorIgnoreCase(String color){
        logger.info("Вызван метод поиска студента по цвету");
        return facultyRepository.findByColorIgnoreCase(color);
    }
    public List<Faculty> findByNameIgnoreCase(String name){
        logger.info("Вызван метод поиска факультета по названию " + name);
        return facultyRepository.findByNameIgnoreCase(name);
    }
    public List<Faculty> findAllByNameContainsIgnoreCase(String part){
        logger.info("Вызван метод поиска факультета по части слова " + part);
        return facultyRepository.findAllByNameContainsIgnoreCase(part);
    }
    public Faculty get(Long id){
        logger.info("Вызван метод поиска факультета по id " + id);
        return facultyRepository.findFacultyById(id);
    }
    public List<Student> findFacultyById(Long id){
        logger.info("Вызван метод поиска студентов факультета по id " + id);
        Faculty faculty = get(id);
        return studentRepository.findByFaculty_Id(faculty.getId());
    }
}
