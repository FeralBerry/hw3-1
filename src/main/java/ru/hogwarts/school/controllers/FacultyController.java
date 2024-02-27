package ru.hogwarts.school.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.models.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.services.FacultyService;

import java.util.List;
@Tag(name = "Факультеты",description = "Эндпоинты для работы с факультетами")
@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    @Operation(summary = "Создание факультета")
    public Faculty create(@RequestBody Faculty faculty) {
        return facultyService.create(faculty);
    }

    @GetMapping
    @Operation(summary = "Получение списка факультетов")
    public ResponseEntity<List<Faculty>> read() {
        List<Faculty> facultyList = facultyService.read();
        return ResponseEntity.ok(facultyList);
    }
    @GetMapping("/{id}")
    @Operation(summary = "Получение списка факультетов")
    public ResponseEntity<Faculty> get(@PathVariable Long id) {
        Faculty faculty = facultyService.get(id);
        return ResponseEntity.ok(faculty);
    }
    @PutMapping("/{id}")
    @Operation(summary = "Изменение факультета по id")
    public ResponseEntity<Faculty> put(@PathVariable Long id, @RequestBody Faculty faculty) {
        Faculty facultyPut = facultyService.update(id,faculty);
        if (facultyPut == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(facultyPut);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление факультета по id")
    public ResponseEntity delete(@PathVariable Long id) {
        facultyService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(params = "color")
    @Operation(summary = "Поиск факультет по цвету")
    public ResponseEntity<List<Faculty>> findByColorIgnoreCase(@RequestParam String color) {
        List<Faculty> facultyList = facultyService.findByColorIgnoreCase(color);
        return ResponseEntity.ok(facultyList);
    }

    @GetMapping("/find-faculty")
    @Operation(summary = "Поиск факультета по названию или по цвету или по части названия")
    public ResponseEntity findByFaculty(@RequestParam(required = false) String name,
                                        @RequestParam(required = false) String color,
                                        @RequestParam(required = false) String part) {
        if (name != null && !name.isBlank()) {
            return ResponseEntity.ok(facultyService.findByNameIgnoreCase(name));
        }
        if (color != null && !color.isBlank()) {
            return ResponseEntity.ok(facultyService.findByColorIgnoreCase(color));
        }
        if (part != null && !part.isBlank()) {
            return ResponseEntity.ok(facultyService.findAllByNameContainsIgnoreCase(part));
        }
        return ResponseEntity.ok(read());
    }
    @GetMapping("/{id}/students")
    @Operation(summary = "Получение списка студентов по факультету")
    public List<Student> findStudents(@PathVariable Long id) {
        return facultyService.findFacultyById(id);
    }
}
