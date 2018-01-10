package integration;

import org.springframework.stereotype.Component;

@Component
final class TestConfig {

    private TestConfig() { }

    static final String API_STORE = "http://localhost:8080/api/store";
}
