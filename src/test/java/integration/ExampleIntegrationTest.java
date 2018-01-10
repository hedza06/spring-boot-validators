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
                TestConfig.API_STORE,
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
                TestConfig.API_STORE,
                exampleDTO,
                Object.class
        );

        Map<String, Object> responseBody = (Map<String, Object>) responseEntity.getBody();
        assertThat(responseBody.get("statusCode")).isEqualTo(400);
        assertThat(responseBody.get("status")).isEqualTo("BAD_REQUEST");
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testWithInvalidProfession() throws Exception
    {
        ExampleDTO exampleDTO = prepareInvalidRequestProfession();
        ResponseEntity<Object> responseEntity = testRestTemplate.postForEntity(
                TestConfig.API_STORE,
                exampleDTO,
                Object.class
        );

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        Map<String, Object> responseBody = (Map<String, Object>) responseEntity.getBody();
        assertThat(responseBody.get("statusCode")).isEqualTo(400);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testWithInvalidUsername() throws Exception
    {
        ExampleDTO exampleDTO = prepareInvalidRequestUsername();
        ResponseEntity<Object> responseEntity = testRestTemplate.postForEntity(
                TestConfig.API_STORE,
                exampleDTO,
                Object.class
        );

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        Map<String, Object> responseBody = (Map<String, Object>) responseEntity.getBody();
        assertThat(responseBody.get("statusCode")).isEqualTo(400);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testWithInvalidEmployed() throws Exception
    {
        ExampleDTO exampleDTO = prepareInvalidRequestEmployed();
        ResponseEntity<Object> responseEntity = testRestTemplate.postForEntity(
                TestConfig.API_STORE,
                exampleDTO,
                Object.class
        );

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        Map<String, Object> responseBody = (Map<String, Object>) responseEntity.getBody();
        assertThat(responseBody.get("statusCode")).isEqualTo(400);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testWithInvalidEmployedAndProfession() throws Exception
    {
        ExampleDTO exampleDTO = prepareInvalidRequestEmployedWithoutProfession();
        ResponseEntity<Object> responseEntity = testRestTemplate.postForEntity(
                TestConfig.API_STORE,
                exampleDTO,
                Object.class
        );

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        Map<String, Object> responseBody = (Map<String, Object>) responseEntity.getBody();
        assertThat(responseBody.get("statusCode")).isEqualTo(400);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testWithInvalidProfessionProvided() throws Exception
    {
        ExampleDTO exampleDTO = prepareInvalidRequestEmployedWithProfession();
        ResponseEntity<Object> responseEntity = testRestTemplate.postForEntity(
                TestConfig.API_STORE,
                exampleDTO,
                Object.class
        );

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        Map<String, Object> responseBody = (Map<String, Object>) responseEntity.getBody();
        assertThat(responseBody.get("statusCode")).isEqualTo(400);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testWithAlreadyPickedUsername() throws Exception
    {
        ExampleDTO exampleDTO = prepareInvalidRequestWithExistingUsername();
        ResponseEntity<Object> responseEntity = testRestTemplate.postForEntity(
                TestConfig.API_STORE,
                exampleDTO,
                Object.class
        );

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        Map<String, Object> responseBody = (Map<String, Object>) responseEntity.getBody();
        assertThat(responseBody.get("statusCode")).isEqualTo(400);
    }

    /**
     * Method for preparing request with regular parameters.
     *
     * @return ExampleDTO object.
     */
    private ExampleDTO prepareRegularRequest()
    {
        ExampleDTO exampleDTO = new ExampleDTO();
        exampleDTO.setAge(25);
        exampleDTO.setEmployed(true);
        exampleDTO.setUsername("hedza06");
        exampleDTO.setProfession("SOFTWARE_ENGINEER");

        return exampleDTO;
    }

    /**
     * Method for preparing request with invalid age parameter.
     *
     * @return ExampleDTO without age parameter.
     */
    private ExampleDTO prepareInvalidRequestWithoutAge()
    {
        ExampleDTO exampleDTO = new ExampleDTO();
        exampleDTO.setEmployed(true);
        exampleDTO.setUsername("hedza06");
        exampleDTO.setProfession("SOFTWARE_ENGINEER");

        return exampleDTO;
    }

    /**
     * Method for preparing request with invalid profession parameter.
     *
     * @return ExampleDTO with invalid profession parameter.
     */
    private ExampleDTO prepareInvalidRequestProfession()
    {
        ExampleDTO exampleDTO = new ExampleDTO();
        exampleDTO.setAge(25);
        exampleDTO.setEmployed(true);
        exampleDTO.setUsername("hedza06");
        exampleDTO.setProfession("DUMMY_PROFESSION");

        return exampleDTO;
    }

    /**
     * Method for preparing request with invalid username parameter.
     *
     * @return ExampleDTO with invalid username parameter.
     */
    private ExampleDTO prepareInvalidRequestUsername()
    {
        ExampleDTO exampleDTO = new ExampleDTO();
        exampleDTO.setAge(25);
        exampleDTO.setEmployed(true);
        exampleDTO.setUsername("    ");
        exampleDTO.setProfession("DUMMY_PROFESSION");

        return exampleDTO;
    }

    /**
     * Method for preparing request with invalid employed parameter.
     *
     * @return ExampleDTO with invalid employed parameter.
     */
    private ExampleDTO prepareInvalidRequestEmployed()
    {
        ExampleDTO exampleDTO = new ExampleDTO();
        exampleDTO.setAge(25);
        exampleDTO.setUsername("hedza06");
        exampleDTO.setProfession("OTHER");

        return exampleDTO;
    }

    /**
     * Method for preparing request with invalid profession when employed field is set to true.
     *
     * @return ExampleDTO with invalid employed and profession.
     */
    private ExampleDTO prepareInvalidRequestEmployedWithoutProfession()
    {
        ExampleDTO exampleDTO = new ExampleDTO();
        exampleDTO.setAge(25);
        exampleDTO.setEmployed(true);
        exampleDTO.setUsername("hedza06");

        return exampleDTO;
    }

    /**
     * Method for preparing request with invalid employed and profession. When employed is set to false, but
     * profession is provided.
     *
     * @return ExampleDTO with invalid employed and profession.
     */
    private ExampleDTO prepareInvalidRequestEmployedWithProfession()
    {
        ExampleDTO exampleDTO = new ExampleDTO();
        exampleDTO.setAge(25);
        exampleDTO.setEmployed(false);
        exampleDTO.setUsername("hedza06");
        exampleDTO.setProfession("OTHER");

        return exampleDTO;
    }

    /**
     * Method for preparing request with invalid username (already in use).
     *
     * @return ExampleDTO with picked username.
     */
    private ExampleDTO prepareInvalidRequestWithExistingUsername()
    {
        ExampleDTO exampleDTO = new ExampleDTO();
        exampleDTO.setAge(25);
        exampleDTO.setEmployed(true);
        exampleDTO.setUsername("already_in_use");
        exampleDTO.setProfession("OTHER");

        return exampleDTO;
    }
}
