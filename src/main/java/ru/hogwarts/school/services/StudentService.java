package ru.hogwarts.school.services;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.models.Student;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final Map<Long, Student> students = new HashMap<>();
    private Long counter = 0L;
    public Student create(Student student){
        student.setId(++counter);
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
    public Student update(Student student){
        Student oldStudent = students.get(student.getId());
        if(oldStudent != null){
            students.put(student.getId(),student);
        } else {
            throw new NoSuchElementException();
        }
        return student;
    }
    public Map<Long, Student> sortByAge(Student student){
        return students
                .entrySet()
                .stream()
                .filter(s -> s.getValue().getAge() == student.getAge())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
