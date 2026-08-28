package com.climbmetrics.backend;

import com.climbmetrics.backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class RegistrationIntegrationTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer postgres =
            new PostgreSQLContainer("postgres:16")
                    .withDatabaseName("climbmetrics_test")
                    .withUsername("test")
                    .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void cleanDatabase() {
        userRepository.deleteAll();
    }

    @Test
    void userCanRegisterWithValidDetails() throws Exception { // REG-1

        mockMvc.perform(
                        post("/api/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                            {
                                "email": "test@example.com",
                                "password": "Password123!"
                            }
                            """)
                )
                .andExpect(status().isCreated());

        assertTrue(
                userRepository.findByEmail("test@example.com").isPresent()
        );
    }

    @Test
    void userCantRegisterDuplicateEmail() throws Exception { // REG-2
        mockMvc.perform(
                        post("/api/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                            {
                                "email": "test@example.com",
                                "password": "Password123!"
                            }
                            """)
                )
                .andExpect(status().isCreated());

        mockMvc.perform(
                post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                    "email": "test@example.com",
                    "password": "Password123!"
                }
                """)
        ).andExpect(status().isConflict());

    }

    @Test
    void userCantRegisterNoPass() throws Exception {
        mockMvc.perform(
                        post("/api/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                            {
                                "email": "test@example.com",
                                "password": ""
                            }
                            """)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void userCantRegisterNoEmail() throws Exception {
        mockMvc.perform(
                        post("/api/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                            {
                                "email": "",
                                "password": "Password123!"
                            }
                            """)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void userCantRegisterBadPass() throws Exception {
        mockMvc.perform(
                        post("/api/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                            {
                                "email": "test@example.com",
                                "password": "test1"
                            }
                            """)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void userCanLoginWithValidDetails() throws Exception {
        mockMvc.perform(
                        post("/api/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                            {
                                "email": "test@example.com",
                                "password": "Password123!"
                            }
                            """)
                );
        mockMvc.perform(
                post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "email": "test@example.com",
                                "password": "Password123!"
                            }
                            """)
        ).andExpect(status().isNoContent()).andExpect(cookie().exists("accessToken"));
    }

    @Test
    void userCantLoginWithIncorrectPass() throws Exception {
        mockMvc.perform(
                post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "email": "test@example.com",
                                "password": "Password123!"
                            }
                            """)
        );
        mockMvc.perform(
                post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "email": "test@example.com",
                                "password": "NotPassword123!"
                            }
                            """)
        ).andExpect(status().isForbidden());
    }

    @Test
    void userCantLoginWithIncorrectEmail() throws Exception {
        mockMvc.perform(
                post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "email": "test@example.com",
                                "password": "Password123!"
                            }
                            """)
        );
        mockMvc.perform(
                post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "email": "fake@example.com",
                                "password": "NotPassword123!"
                            }
                            """)
        ).andExpect(status().isUnauthorized());
    }

    @Test
    void userCanLogOut() throws Exception {
        mockMvc.perform(
                post("/api/auth/logout")).andExpect(status().isNoContent());

    }

}

