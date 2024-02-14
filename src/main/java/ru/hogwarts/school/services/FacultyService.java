package ru.hogwarts.school.services;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private  final FacultyRepository facultyRepository;
    public FacultyService(FacultyRepository facultyRepository){
        this.facultyRepository = facultyRepository;
    }
    public Faculty create(Faculty faculty){
        return facultyRepository.save(faculty);
    }
    public List<Faculty> read(){
        return facultyRepository.findAll();
    }
    public void delete(long id){
        facultyRepository.deleteById(id);
    }
    public Faculty update(Faculty faculty){
        return facultyRepository.save(faculty);
    }
    public List<Faculty> sortByColor(String color){
        return read().stream()
                .filter(s -> s.getColor().equals(color))
                .collect(Collectors.toList());
    }
}
