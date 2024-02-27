package ru.hogwarts.school;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.hogwarts.school.controllers.AvatarController;
import ru.hogwarts.school.controllers.FacultyController;
import ru.hogwarts.school.controllers.StudentController;
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.models.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
class Hw31ApplicationTest {
    @LocalServerPort
    private int port;
    @InjectMocks
    private FacultyController facultyController;
    @InjectMocks
    private StudentController studentController;
    @InjectMocks
    private AvatarController avatarController;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    @MockBean
    private FacultyRepository facultyRepository;
    @Test
    void contextLoads() throws Exception {
        assertThat(facultyController).isNotNull();
        assertThat(studentController).isNotNull();
        assertThat(avatarController).isNotNull();
    }
    @Test
    void testDefaultMessage() throws Exception{
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/",String.class))
                .contains("App working");
    }
    @Test
    void testGetStudents() throws Exception{
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student",String.class))
                .isNotNull();
    }
    @Test
    void testGetFaculty() throws Exception{
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty",String.class))
                .isNotNull();
    }
    @Test
    void testPutStudents() throws Exception{
        Map<String, String> params = new HashMap<>();
        params.put("id", "1");
        Student updatedStudent = new Student();
        updatedStudent.setId(1L);
        updatedStudent.setName("Гарри");
        updatedStudent.setAge(11);
        this.restTemplate.put("http://localhost:" + port + "/student", updatedStudent, params);
    }
    @Test
    void testPutFaculty() throws Exception{
        Map<String, String> params = new HashMap<>();
        params.put("id", "1");
        Faculty updatedFaculty = new Faculty();
        updatedFaculty.setId(1L);
        updatedFaculty.setName("Грифиндор");
        updatedFaculty.setColor("Красный");
        this.restTemplate.put("http://localhost:" + port + "/faculty", updatedFaculty, params);
    }
    @Test
    void testDeleteStudents() throws Exception{
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", "1");
        this.restTemplate.delete("http://localhost:" + port + "/student", params);
    }
    @Test
    void testDeleteFaculty() throws Exception{
        Map<String, String> params = new HashMap<>();
        params.put("id", "1");
        this.restTemplate.delete("http://localhost:" + port + "/faculty", params);
    }


    @Test
    void testPostFaculty() throws Exception{
        final String name = "Грифиндор";
        final String color = "Красный";
        final Long id = 1L;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name",name);
        jsonObject.put("color",color);
        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);
        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }
}