package ru.hogwarts.school;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import ru.hogwarts.school.controllers.AvatarController;
import ru.hogwarts.school.controllers.FacultyController;
import ru.hogwarts.school.controllers.StudentController;
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.models.Student;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

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
    void testPostStudents() throws Exception{
        Student student = new Student();
        student.setId(1L);
        student.setName("Garry");
        student.setAge(11);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Student> request = new HttpEntity<>(student, headers);
        assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/student",request,Student.class))
                .isNotNull();
    }
    @Test
    void testPostFaculties() throws Exception{
        Faculty faculty = new Faculty();
        faculty.setId(1L);
        faculty.setName("Grifindor");
        faculty.setColor("red");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Faculty> request = new HttpEntity<>(faculty, headers);
        assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/faculty",request,Student.class))
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
        Map<String, String> params = new HashMap<>();
        params.put("id", "1");
        this.restTemplate.delete("http://localhost:" + port + "/student", params);
    }
    @Test
    void testDeleteFaculty() throws Exception{
        Map<String, String> params = new HashMap<>();
        params.put("id", "1");
        this.restTemplate.delete("http://localhost:" + port + "/faculty", params);
    }
}