package ru.hogwarts.school.services;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.FacultyNotFoundException;
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.models.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;

@Service
public class FacultyService {
    private  final FacultyRepository facultyRepository;
    private  final StudentRepository studentRepository;
    public FacultyService(FacultyRepository facultyRepository,StudentRepository studentRepository){
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }
    public Faculty create(Faculty faculty){
        faculty.setId(null);
        return facultyRepository.save(faculty);
    }
    public List<Faculty> read(){
        return facultyRepository.findAll();
    }
    public void delete(Long id){
        facultyRepository.deleteById(id);
    }
    public Faculty update(Long id,Faculty faculty){
        return facultyRepository.findById(id)
                .map(oldFaculty -> {
                    oldFaculty.setName(faculty.getName());
                    oldFaculty.setColor(faculty.getColor());
                    return facultyRepository.save(oldFaculty);
                }).orElseThrow(() -> new FacultyNotFoundException(id));
    }
    public List<Faculty> findByColorIgnoreCase(String color){
        return facultyRepository.findByColorIgnoreCase(color);
    }
    public List<Faculty> findByNameIgnoreCase(String name){
        return facultyRepository.findByNameIgnoreCase(name);
    }
    public List<Faculty> findAllByNameContainsIgnoreCase(String part){
        return facultyRepository.findAllByNameContainsIgnoreCase(part);
    }
    public Faculty get(Long id){
        return facultyRepository.findFacultyById(id);
    }
    public List<Student> findFacultyById(Long id){
        Faculty faculty = get(id);
        return studentRepository.findByFaculty_Id(faculty.getId());
    }
}
