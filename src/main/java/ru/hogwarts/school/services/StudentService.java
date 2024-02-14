package ru.hogwarts.school.services;


import org.springframework.stereotype.Service;
import ru.hogwarts.school.models.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }
    public Student create(Student student){
        return studentRepository.save(student);
    }
    public List<Student> read(){
        return studentRepository.findAll();
    }
    public void delete(long id){
        studentRepository.deleteById(id);
    }
    public Student update(Student student){
        return studentRepository.save(student);
    }
    public List<Student> sortByAge(int age){
        return read().stream()
                .filter(s -> s.getAge() == age)
                .collect(Collectors.toList());
    }
}
