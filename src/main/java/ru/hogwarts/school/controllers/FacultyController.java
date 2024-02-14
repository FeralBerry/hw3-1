package ru.hogwarts.school.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.services.FacultyService;

import java.util.List;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;
    public FacultyController(FacultyService facultyService){
        this.facultyService = facultyService;
    }
    @PostMapping("/")
    public Faculty create(@RequestBody Faculty faculty){
        return facultyService.create(faculty);
    }
    @GetMapping("/")
    public ResponseEntity<List<Faculty>> read(){
        List <Faculty> facultyList = facultyService.read();
        return ResponseEntity.ok(facultyList);
    }
    @PutMapping("/")
    public ResponseEntity<Faculty> put(@RequestBody Faculty faculty){
        Faculty facultyPut = facultyService.update(faculty);
        if(facultyPut == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(facultyPut);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id){
        facultyService.delete(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/sort-by-color")
    public ResponseEntity<List<Faculty>> sortByColor(@RequestBody String color){
        List<Faculty> facultyList = facultyService.sortByColor(color);
        return ResponseEntity.ok(facultyList);
    }
}
