package ru.hogwarts.school.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.models.Student;
import ru.hogwarts.school.services.StudentService;

import java.util.List;
@Tag(name = "Студенты",description = "Эндпоинты для работы со студентами")
@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;
    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }
    @PostMapping
    @Operation(summary = "Создание студента")
    public Student create(@RequestBody Student student){
        return studentService.create(student);
    }
    @GetMapping
    @Operation(summary = "Получение списка всех студентов")
    public ResponseEntity<List<Student>> read(){
        List <Student> facultyList = studentService.read();
        return ResponseEntity.ok(facultyList);
    }
    @GetMapping("/{id}")
    @Operation(summary = "Получение студена по id")
    public ResponseEntity<Student> get(@PathVariable Long id){
        Student student = studentService.get(id);
        return ResponseEntity.ok(student);
    }
    @PutMapping("/{id}")
    @Operation(summary = "Обновление информации о студенте по id")
    public Student put(@PathVariable Long id,@RequestBody Student student){
        return studentService.update(id,student);
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление студента по id")
    public ResponseEntity delete(@PathVariable Long id){
        studentService.delete(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping(params = "age")
    @Operation(summary = "Получение списка студентов у которых возраст равен параметру")
    public ResponseEntity<List<Student>> findAllByAge(@RequestParam int age){
        List<Student> studentList = studentService.findAllByAge(age);
        return ResponseEntity.ok(studentList);
    }
    @GetMapping(params = {"min","max"})
    @Operation(summary = "Получение студентов у которых возраст между параметрами min & max")
    public ResponseEntity<List<Student>> findAllByAgeBetween(@RequestParam int min,
                                                          @RequestParam int max){
        List<Student> studentList = studentService.findAllByAgeBetween(min,max);
        return ResponseEntity.ok(studentList);
    }
    @GetMapping("/{id}/faculty")
    @Operation(summary = "Получение факультета по id студента")
    public Faculty findFaculty(@PathVariable Long id){
        return studentService.findFaculty(id);
    }
    @GetMapping("/count")
    @Operation(summary = "Получение количество студентов")
    public ResponseEntity<Long> countStudents(){
        return ResponseEntity.ok(studentService.countStudents());
    }
    @GetMapping("/middle-age")
    @Operation(summary = "Получение среднего возраста всех студентов")
    public ResponseEntity<Double> middleAgeStudents(){
        return ResponseEntity.ok(studentService.middleAgeStudents());
    }
    @GetMapping("/last-five")
    @Operation(summary = "Получение последних 5 студентов")
    public ResponseEntity<List<Student>> lastFiveStudents(){
        return ResponseEntity.ok(studentService.lastFiveStudents());
    }
    @GetMapping("/sort-all-student")
    @Operation(summary = "Сортировка студентов")
    public ResponseEntity<List<String>> sortAllStudents(@RequestParam char firstChar){
        return ResponseEntity.ok(studentService.sortAllStudents(firstChar));
    }
    @GetMapping("/print-parallel")
    @Operation(summary = "Параллельный вывод студентов")
    public void printParallel(){
        studentService.printParallel();
    }
    @GetMapping("/print-synchronized")
    @Operation(summary = "Параллельный синхронизированный вывод студентов")
    public void printSynchronized(){
        studentService.printSynchronized();
    }
}
