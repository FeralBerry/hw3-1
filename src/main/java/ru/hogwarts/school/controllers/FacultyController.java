package ru.hogwarts.school.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.services.FacultyService;

import java.util.Map;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;
    public FacultyController(FacultyService facultyService){
        this.facultyService = facultyService;
    }
    @PostMapping("/create")
    public Faculty create(@RequestBody Faculty faculty){
        return facultyService.create(faculty);
    }
    @GetMapping("/read")
    public ResponseEntity<Map<Long,Faculty>> read(){
        Map <Long, Faculty> facultyMap = facultyService.read();
        if(facultyMap == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(facultyMap);
    }
    @PutMapping("/put")
    public ResponseEntity<Faculty> put(@RequestBody Faculty faculty){
        Faculty facultyPut = facultyService.update(faculty);
        if(facultyPut == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(facultyPut);
    }
    @DeleteMapping("/delete/{id}")
    public Faculty delete(@PathVariable long id){
        return facultyService.delete(id);
    }
    @GetMapping("/sort_by_color")
    public ResponseEntity<Map<Long,Faculty>> sortByColor(@RequestBody Faculty faculty){
        Map <Long, Faculty> facultyMap = facultyService.sortByColor(faculty);
        if(facultyMap == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(facultyMap);
    }
}
