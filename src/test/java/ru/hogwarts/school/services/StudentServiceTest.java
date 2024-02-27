package ru.hogwarts.school.services;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.models.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    @MockBean
    private StudentRepository studentRepository;
    @SpyBean
    private StudentService studentService;
    @MockBean
    private FacultyRepository facultyRepository;
    private final String name = "Гарри";
    private final int age = 11;
    private final Long id = 1L;
    @Test
    void create() throws Exception{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name",name);
        jsonObject.put("age",age);
        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }
    @Test
    void read() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name",name);
        jsonObject.put("age",age);
        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.findAll()).thenReturn(List.of(student));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*").isNotEmpty());
    }
    @Test
    void delete() throws Exception {
        create();
        Assertions.assertEquals("",mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString());
    }

    @Test
    void update() throws Exception {
        create();
        String newName = "Вася";
        int newAge = 20;
        Student student = new Student();
        student.setId(id);
        student.setName(newName);
        student.setAge(newAge);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name",newName);
        jsonObject.put("age",newAge);
        when(studentService.update(id,student)).thenReturn(student);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student/" + id)
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(newName))
                .andExpect(jsonPath("$.age").value(newAge));
    }
    @Test
    void findAllByAge() throws Exception {
        create();
        studentService.findAllByAge(age).forEach(s->Assertions.assertEquals(age,s.getAge()));
    }

    @Test
    void findAllByAgeBetween() throws Exception {
        int min = 0;
        int max = 11;
        Student student1 = new Student();
        student1.setId(1L);
        student1.setName("Гарри");
        student1.setAge(11);
        Student student2 = new Student();
        student2.setId(2L);
        student2.setName("Рон");
        student2.setAge(12);
        Student student3 = new Student();
        student3.setId(3L);
        student3.setName("Гермиона");
        student3.setAge(13);
        List<Student> expected = List.of(student1);
        when(studentRepository.save(any(Student.class))).thenReturn(student1);
        when(studentRepository.save(any(Student.class))).thenReturn(student2);
        when(studentRepository.save(any(Student.class))).thenReturn(student3);
        when(studentService.findAllByAgeBetween(min, max)).thenReturn(expected);
        studentService.findAllByAgeBetween(min,max).forEach(s->
            Assertions.assertTrue(min <= s.getAge() && s.getAge() <= max)
        );
    }

    @Test
    void get() throws Exception {
        create();
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    void findFaculty() throws Exception {
        create();
        Faculty faculty = new Faculty();
        faculty.setId(1L);
        faculty.setName("Грифиндор");
        faculty.setColor("Красный");
        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
        when(studentService.findFaculty(1L)).thenReturn(faculty);
        Assertions.assertEquals(faculty,studentService.findFaculty(1L));
    }
}