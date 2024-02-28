package ru.hogwarts.school.services;


import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.models.Faculty;

import ru.hogwarts.school.models.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class FacultyServiceTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FacultyRepository facultyRepository;
    @SpyBean
    private FacultyService facultyService;
    @MockBean
    private StudentRepository studentRepository;
    private final Long id = 1L;
    private final String name = "Grifindor";
    private final String color = "red";
    @Test
    void create() throws Exception{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name",name);
        jsonObject.put("color",color);
        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);
        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }
    @Test
    void read() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name",name);
        jsonObject.put("color",color);
        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);
        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
        when(facultyRepository.findAll()).thenReturn(List.of(faculty));
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect(jsonPath("$[0].color").value(color));
    }
    @Test
    void delete() throws Exception {
        create();
        assertThat(mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString()).isEqualTo("");
        // в принципе в данном случае разницы нет
        /*Assertions.assertEquals("",mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString()); */
    }
    @Test
    void update() throws Exception {
        create();
        String newName = "Vasa";
        String newColor = "green";
        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(newName);
        faculty.setColor(newColor);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name",newName);
        jsonObject.put("color",newColor);
        when(facultyService.update(id,faculty)).thenReturn(faculty);
        this.mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty/" + id)
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(newName))
                .andExpect(jsonPath("$.color").value(newColor));
    }
    @Test
    void findByColorIgnoreCase() throws Exception {
        create();
        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);
        when(facultyRepository.findByColorIgnoreCase(color)).thenReturn(List.of(faculty));
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty?color=" + color )
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect(jsonPath("$[0].color").value(color));
    }

    @Test
    void get() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name",name);
        jsonObject.put("color",color);
        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);
        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
        when(facultyService.get(id)).thenReturn(faculty);
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    void findFacultyById() throws Exception {
        create();
        get();
        Long studentId = 1L;
        String studentName = "Garry";
        int studentAge = 11;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name",studentName);
        jsonObject.put("age",studentAge);
        Student student = new Student();
        student.setId(studentId);
        student.setName(studentName);
        student.setAge(studentAge);
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(studentId))
                .andExpect(jsonPath("$.name").value(studentName))
                .andExpect(jsonPath("$.age").value(studentAge));
        when(facultyService.findFacultyById(id)).thenReturn(List.of(student));
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/" + id + "/students")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(id));
    }
}