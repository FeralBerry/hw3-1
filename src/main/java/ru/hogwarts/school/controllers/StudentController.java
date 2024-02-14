package ru.hogwarts.school.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.models.Student;
import ru.hogwarts.school.services.StudentService;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;
    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }
    @PostMapping("/")
    public Student create(@RequestBody Student student){
        return studentService.create(student);
    }
    @GetMapping("/")
    public ResponseEntity<List<Student>> read(){
        List <Student> facultyList = studentService.read();
        return ResponseEntity.ok(facultyList);
    }
    @PutMapping("/")
    public Student put(@RequestBody Student student){
        return studentService.update(student);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id){
        studentService.delete(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/sort-by-age")
    public ResponseEntity<List<Student>> sortByAge(@RequestBody int age){
        List<Student> studentList = studentService.sortByAge(age);
        return ResponseEntity.ok(studentList);
    }
}
