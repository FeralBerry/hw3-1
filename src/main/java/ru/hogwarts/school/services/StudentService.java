package ru.hogwarts.school.services;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.models.Student;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class StudentService {
    private final Map<Long, Student> students = new HashMap<>();
    private Long counter;
    public Student create(String name, int age){
        counter++;
        Student student = new Student(counter,name,age);
        students.put(counter,student);
        return student;
    }
    public Map<Long, Student> read(){
        return students;
    }
    public Student delete(long id){
        Student student = students.get(id);
        if(student != null){
            students.remove(id);
        } else {
            throw new NoSuchElementException();
        }
        return student;
    }
    public Student update(long id, String name, int age){
        Student oldStudent = students.get(id);
        Student student = new Student(id,name,age);
        if(oldStudent != null){
            students.put(id,student);
        } else {
            throw new NoSuchElementException();
        }
        return student;
    }
}
