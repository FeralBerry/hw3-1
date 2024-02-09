package ru.hogwarts.school.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.models.Student;
import ru.hogwarts.school.services.StudentService;

import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;
    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }
    @PostMapping("/create")
    public Student create(@RequestBody Student student){
        return studentService.create(student);
    }
    @GetMapping("/read")
    public ResponseEntity<Map<Long, Student>> read(){
        Map <Long, Student> facultyMap = studentService.read();
        if(facultyMap == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(facultyMap);
    }
    @PutMapping("/put")
    public Student put(@RequestBody Student student){
        return studentService.update(student);
    }
    @DeleteMapping("/delete/{id}")
    public Student delete(@PathVariable long id){
        return studentService.delete(id);
    }
    @GetMapping("/sort_by_age")
    public ResponseEntity<Map<Long, Student>> sortByAge(@RequestBody Student student){
        Map <Long, Student> studentMap = studentService.read();
        if(studentMap == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(studentMap);
    }
}
