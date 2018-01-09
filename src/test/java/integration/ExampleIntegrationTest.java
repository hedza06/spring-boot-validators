package integration;

import com.starter.springboot.Application;
import com.starter.springboot.rest.dto.ExampleDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Application.class, TestConfig.class })
@ActiveProfiles({"dev", "no-liquibase"})
public class ExampleIntegrationTest {

    private TestRestTemplate testRestTemplate;

    @Before
    public void init() {
        this.testRestTemplate = new TestRestTemplate();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testWithRegularParams() throws Exception
    {
        ExampleDTO exampleDTO = prepareRegularRequest();
        ResponseEntity<Object> responseEntity = testRestTemplate.postForEntity(
                "http://localhost:8080/api/" + TestConfig.API_STORE,
                exampleDTO,
                Object.class
        );

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        Map<String, String> responseBody = (Map<String, String>) responseEntity.getBody();
        assertThat(responseBody.get("message")).isEqualTo("Example DTO successfully stored.");
        assertThat(responseBody.get("status")).isEqualTo("success");
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testWithInvalidAgeParam() throws Exception
    {
        ExampleDTO exampleDTO = prepareInvalidRequestWithoutAge();
        ResponseEntity<Object> responseEntity = testRestTemplate.postForEntity(
                "http://localhost:8080/api/" + TestConfig.API_STORE,
                exampleDTO,
                Object.class
        );

        Map<String, Object> responseBody = (Map<String, Object>) responseEntity.getBody();
        assertThat(responseBody.get("statusCode")).isEqualTo(400);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testWithInvalidProfession() throws Exception
    {
        ExampleDTO exampleDTO = prepareInvalidRequestProfession();
        ResponseEntity<Object> responseEntity = testRestTemplate.postForEntity(
                "http://localhost:8080/api/" + TestConfig.API_STORE,
                exampleDTO,
                Object.class
        );

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        Map<String, Object> responseBody = (Map<String, Object>) responseEntity.getBody();
        assertThat(responseBody.get("statusCode")).isEqualTo(400);
    }

    private ExampleDTO prepareRegularRequest()
    {
        ExampleDTO exampleDTO = new ExampleDTO();
        exampleDTO.setAge(25);
        exampleDTO.setEmployed(true);
        exampleDTO.setUsername("hedza06");
        exampleDTO.setProfession("SOFTWARE_ENGINEER");

        return exampleDTO;
    }

    private ExampleDTO prepareInvalidRequestWithoutAge()
    {
        ExampleDTO exampleDTO = new ExampleDTO();
        exampleDTO.setEmployed(true);
        exampleDTO.setUsername("hedza06");
        exampleDTO.setProfession("SOFTWARE_ENGINEER");

        return exampleDTO;
    }

    private ExampleDTO prepareInvalidRequestProfession()
    {
        ExampleDTO exampleDTO = new ExampleDTO();
        exampleDTO.setAge(25);
        exampleDTO.setEmployed(true);
        exampleDTO.setUsername("hedza06");
        exampleDTO.setProfession("DUMMY_PROFESSION");

        return exampleDTO;
    }
}
