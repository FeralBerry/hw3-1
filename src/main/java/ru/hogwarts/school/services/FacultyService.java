package ru.hogwarts.school.services;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.models.Faculty;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private final Map<Long, Faculty> faculties = new HashMap<>();
    private Long counter = 0L;
    public Faculty create(Faculty faculty){
        faculty.setId(++counter);
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
    public Faculty update(Faculty faculty){
        Faculty oldFaculty = faculties.get(faculty.getId());
        if(oldFaculty != null){
            faculties.put(faculty.getId(),faculty);
        } else {
            throw new NoSuchElementException();
        }
        return faculty;
    }
    public Map<Long, Faculty> sortByColor(Faculty faculty){
        return faculties
                .entrySet()
                .stream()
                .filter(s -> s.getValue().getColor().equals(faculty.getColor()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
