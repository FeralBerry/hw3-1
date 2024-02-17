package ru.hogwarts.school.services;


import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.StudentNotFoundException;
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.models.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }
    public Student create(Student student){
        student.setId(null);
        return studentRepository.save(student);
    }
    public List<Student> read(){
        return studentRepository.findAll();
    }
    public void delete(long id){
        studentRepository.deleteById(id);
    }
    public Student update(long id, Student student){
        return studentRepository.findById(id)
                        .map(oldStudent -> {
                            oldStudent.setName(student.getName());
                            oldStudent.setAge(student.getAge());
                            return studentRepository.save(oldStudent);
                        }).orElseThrow(() -> new StudentNotFoundException(id));
    }
    public List<Student> findAllByAge(int age){
        return studentRepository.findAllByAge(age);
    }
    public List<Student> findAllByAgeBetween(int min, int max){
        return studentRepository.findAllByAgeBetween(min, max);
    }
    public Student get(long id){
        return studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
    }
    public Faculty findFaculty(long id){
        return get(id).getFaculty();
    }
}
