package ru.hogwarts.school.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.StudentNotFoundException;
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.models.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    Logger logger = LoggerFactory.getLogger(StudentService.class);
    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }
    public Student create(Student student){
        student.setId(null);
        logger.info("Создан новый студент: " + student.getName() + " " + student.getAge() + " лет");
        return studentRepository.save(student);
    }
    public List<Student> read(){
        logger.info("Вызван метод просмотра всех студентов");
        return studentRepository.findAll();
    }
    public void delete(Long id){
        if(get(id) != null){
            logger.warn("Удаление студента");
            studentRepository.deleteById(id);
        } else {
            logger.error("Удаление не существующего студента");
        }
    }
    public Student update(Long id, Student student){
        logger.info("Студент с id " + id + "был изменен");
        return studentRepository.findById(id)
                        .map(oldStudent -> {
                            oldStudent.setName(student.getName());
                            oldStudent.setAge(student.getAge());
                            return studentRepository.save(oldStudent);
                        }).orElseThrow(() -> new StudentNotFoundException(id));
    }
    public List<Student> findAllByAge(int age){
        logger.info("Вызван метод поиска студента по возрасту");
        return studentRepository.findAllByAge(age);
    }
    public List<Student> findAllByAgeBetween(int min, int max){
        logger.info("Вызван метод поиска студентов в диапазоне возрастов мин " + min + " макс " + max);
        return studentRepository.findAllByAgeBetween(min, max);
    }
    public Student get(Long id){
        logger.info("Получение студента по ид");
        return studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
    }
    public Faculty findFaculty(Long id){
        logger.debug("Вызов метода findFaculty");
        return get(id).getFaculty();
    }
    public Student findStudentById(Long id){
        logger.debug("Вызов метода findStudentById");
        return studentRepository.findStudentById(id);
    }
    public Long countStudents(){
        logger.info("Получение количества студентов");
        return studentRepository.countStudents();
    }
    public Double middleAgeStudents(){
        logger.info("Получение среднего возраста студентов");
        return (double) (studentRepository.findAll()
                        .stream()
                        .map(Student::getAge)
                        .mapToInt(i -> i)
                        .sum() / studentRepository.countStudents());
        /* метод через базу данных
        return studentRepository.middleAgeStudents();
        */
    }
    public List<Student> lastFiveStudents(){
        logger.debug("Вызов метода lastFiveStudents");
        return studentRepository.lastFiveStudents();
    }
    public List<String> sortAllStudents(char firstChar){
        logger.info("Сортировка студентов и вывод всех у кого имя начинается на " + firstChar);
        return studentRepository.findAll()
                .stream()
                .parallel()
                .filter(student -> student.getName().charAt(0) == firstChar)
                .map(string -> string.getName().toUpperCase())
                .sorted()
                .toList();
    }
}
