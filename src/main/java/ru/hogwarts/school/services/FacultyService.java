package ru.hogwarts.school.services;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.models.Faculty;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class FacultyService {
    private final Map<Long, Faculty> faculties = new HashMap<>();
    private Long counter;
    public Faculty create(String name, String color){
        counter++;
        Faculty faculty = new Faculty(counter,name,color);
        faculties.put(counter,faculty);
        return faculty;
    }
    public Map<Long, Faculty> read(){
        return faculties;
    }
    public Faculty delete(long id){
        Faculty faculty = faculties.get(id);
        if(faculty != null){
            faculties.remove(id);
        } else {
            throw new NoSuchElementException();
        }
        return faculty;
    }
    public Faculty update(long id, String name, String color){
        Faculty oldFaculty = faculties.get(id);
        Faculty faculty = new Faculty(id,name,color);
        if(oldFaculty != null){
            faculties.put(id,faculty);
        } else {
            throw new NoSuchElementException();
        }
        return faculty;
    }
}
