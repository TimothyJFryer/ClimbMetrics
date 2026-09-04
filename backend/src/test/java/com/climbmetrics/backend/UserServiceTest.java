package com.climbmetrics.backend;

import com.climbmetrics.backend.dto.EditRequest;
import com.climbmetrics.backend.entity.User;
import com.climbmetrics.backend.repository.UserRepository;
import com.climbmetrics.backend.service.JwtService;

import jakarta.servlet.http.Cookie;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;








@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
class ProfileTests {

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



    @Autowired
    private JwtService jwtService;


    @Test
    void userCanViewOwnProfile() throws Exception {
        User user = new User();

        user.setEmail("test@example.com");
        user.setUsername("testuser");
        user.setPassword("password");
        user.setDescription("Test climber");


        userRepository.save(user);

        String token = jwtService.generateToken(user.getEmail());
        mockMvc.perform(
                        get("/api/profile")
                                .cookie(
                                        new Cookie("accessToken", token)
                                )
                )

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email")
                        .value("test@example.com"))
                .andExpect(jsonPath("$.username")
                        .value("testuser"))
                .andExpect(jsonPath("$.description")
                        .value("Test climber"));
    }

    @Test
    void unAuthUserCantViewProfile() throws Exception {
        User user = new User();

        user.setEmail("test@example.com");
        user.setUsername("testuser");
        user.setPassword("password");
        user.setDescription("Test climber");


        userRepository.save(user);

        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.KMUFsIDTnFmyG3nMiGM6H9FNFUROf3wh7SmqJp-QV30";
        mockMvc.perform(
                        get("/api/profile")
                                .cookie(
                                        new Cookie("accessToken", token)
                                )
                )

                .andExpect(status().isForbidden());
    }

    @Test
    void userCanEditOwnProfile() throws Exception {
        User user = new User();

        user.setEmail("test@example.com");
        user.setUsername("testuser");
        user.setPassword("password");
        user.setDescription("Test climber");


        userRepository.save(user);

        String token = jwtService.generateToken(user.getEmail());






        mockMvc.perform(
                        put("/api/profile/editProfile")
                                .cookie(
                                        new Cookie("accessToken", token)
                                )
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                            {
                                "email": "newEmail@example.com",
                                "username": "newUsername",
                                "description": "new description"
                            }
                            """)
                )
                .andExpect(status().is2xxSuccessful());

    }


}


