package com.climbmetrics.backend;


import com.climbmetrics.backend.entity.Climb;
import com.climbmetrics.backend.entity.User;
import com.climbmetrics.backend.repository.ClimbRepository;
import com.climbmetrics.backend.repository.UserRepository;
import com.climbmetrics.backend.service.JwtService;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
public class ClimbLoggingTests {
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

    @Autowired
    private ClimbRepository climbRepository;

    @BeforeEach
    void cleanDatabase() {
        userRepository.deleteAll();
    }



    @Autowired
    private JwtService jwtService;


    @Test
    void UserCanLogClimb() throws Exception {
        User user = new User();

        user.setEmail("test@example.com");
        user.setUsername("testuser");
        user.setPassword("password");
        user.setDescription("Test climber");


        userRepository.save(user);

        String token = jwtService.generateToken(user.getEmail());

        mockMvc.perform(
                        post("/api/climbs/log")
                                .cookie(
                                        new Cookie("accessToken", token)
                                )
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                            {
                                "date": "2026-09-01",
                                "grade": "V5",
                                "style": "Crimpy",
                                "attempts": 3,
                                "completed": true
                            }
                            """)
                )
                .andExpect(status().isCreated());

    }

    @Test
    void UserCantLogInvalidClimb() throws Exception {
        User user = new User();

        user.setEmail("test@example.com");
        user.setUsername("testuser");
        user.setPassword("password");
        user.setDescription("Test climber");


        userRepository.save(user);

        String token = jwtService.generateToken(user.getEmail());

        mockMvc.perform(
                        post("/api/climbs/log")
                                .cookie(
                                        new Cookie("accessToken", token)
                                )
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                            {
                                "date": "2026-09-01",
                                "grade": "V5",
                                "style": "Crimpy",
                                "attempts": -1,
                                "completed": true
                            }
                            """)
                )
                .andExpect(status().is4xxClientError());

    }


    @Test
    void UserCanEditClimb() throws Exception {
        User user = new User();

        user.setEmail("test@example.com");
        user.setUsername("testuser");
        user.setPassword("password");
        user.setDescription("Test climber");


        userRepository.save(user);

        String token = jwtService.generateToken(user.getEmail());

        mockMvc.perform(
                        post("/api/climbs/log")
                                .cookie(
                                        new Cookie("accessToken", token)
                                )
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                            {
                                "date": "2026-09-01",
                                "grade": "V5",
                                "style": "Crimpy",
                                "attempts": 3,
                                "completed": true
                            }
                            """)
                ).andExpect(status().isCreated());

        Climb climb = climbRepository.findAllByUserIdOrderByDateDescTimestampDesc(user.getId())
                .getFirst();

        Long climbId = climb.getId();

        mockMvc.perform(put("/api/climbs/edit")
                .cookie(new Cookie("accessToken", token)).contentType(MediaType.APPLICATION_JSON).content("""
                        {
                                "id": %d,
                                "date": "2026-09-01",
                                "grade": "V6",
                                "style": "Powerful",
                                "attempts": 3,
                                "completed": true
                        }
                        
                        """.formatted(climbId))
        ).andExpect(status().isOk());

        Climb editedClimb = climbRepository.findById(climbId)
                .orElseThrow();

        assertEquals("V6", editedClimb.getGrade());
        assertEquals("Powerful", editedClimb.getStyle());
        assertEquals(3, editedClimb.getAttempts());
        assertTrue(editedClimb.isCompleted());

    }

    @Test
    void UserCanDecreaseAttempts() throws Exception {
        User user = new User();

        user.setEmail("test@example.com");
        user.setUsername("testuser");
        user.setPassword("password");
        user.setDescription("Test climber");


        userRepository.save(user);

        String token = jwtService.generateToken(user.getEmail());

        mockMvc.perform(
                post("/api/climbs/log")
                        .cookie(
                                new Cookie("accessToken", token)
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "date": "2026-09-01",
                                "grade": "V5",
                                "style": "Crimpy",
                                "attempts": 3,
                                "completed": true
                            }
                            """)
        ).andExpect(status().isCreated());

        Climb climb = climbRepository.findAllByUserIdOrderByDateDescTimestampDesc(user.getId())
                .getFirst();

        Long climbId = climb.getId();

        mockMvc.perform(put("/api/climbs/edit")
                .cookie(new Cookie("accessToken", token)).contentType(MediaType.APPLICATION_JSON).content("""
                        {
                                "id": %d,
                                "date": "2026-09-01",
                                "grade": "V5",
                                "style": "Crimpy",
                                "attempts": 2,
                                "completed": true
                        }
                        
                        """.formatted(climbId))
        ).andExpect(status().isOk());

        Climb editedClimb = climbRepository.findById(climbId)
                .orElseThrow();

        assertEquals("V5", editedClimb.getGrade());
        assertEquals("Crimpy", editedClimb.getStyle());
        assertEquals(2, editedClimb.getAttempts());
        assertTrue(editedClimb.isCompleted());

    }

    @Test
    void UserCantEditOtherClimb() throws Exception {
        User userA = new User();

        userA.setEmail("testA@example.com");
        userA.setUsername("testAuser");
        userA.setPassword("passwordA");
        userA.setDescription("TestA climber");


        userRepository.save(userA);

        String tokenA = jwtService.generateToken(userA.getEmail());

        User userB = new User();

        userB.setEmail("testB@example.com");
        userB.setUsername("testBuser");
        userB.setPassword("passwordB");
        userB.setDescription("TestB climber");


        userRepository.save(userB);

        String tokenB = jwtService.generateToken(userB.getEmail());

        mockMvc.perform(
                post("/api/climbs/log")
                        .cookie(
                                new Cookie("accessToken", tokenA)
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "date": "2026-09-01",
                                "grade": "V5",
                                "style": "Crimpy",
                                "attempts": 3,
                                "completed": true
                            }
                            """)
        ).andExpect(status().isCreated());

        Climb climb = climbRepository.findAllByUserIdOrderByDateDescTimestampDesc(userA.getId())
                .getFirst();

        Long climbId = climb.getId();

        mockMvc.perform(put("/api/climbs/edit")
                .cookie(new Cookie("accessToken", tokenB)).contentType(MediaType.APPLICATION_JSON).content("""
                        {
                                "id": %d,
                                "date": "2026-09-01",
                                "grade": "V6",
                                "style": "Powerful",
                                "attempts": 3,
                                "completed": true
                        }
                        
                        """.formatted(climbId))
        ).andExpect(status().isForbidden());

        Climb editedClimb = climbRepository.findById(climbId)
                .orElseThrow();

        assertEquals("V5", editedClimb.getGrade());
        assertEquals("Crimpy", editedClimb.getStyle());
        assertEquals(3, editedClimb.getAttempts());
        assertTrue(editedClimb.isCompleted());

    }

    @Test
    void UserCanDeleteClimb() throws Exception {
        User user = new User();

        user.setEmail("test@example.com");
        user.setUsername("testuser");
        user.setPassword("password");
        user.setDescription("Test climber");


        userRepository.save(user);

        String token = jwtService.generateToken(user.getEmail());

        mockMvc.perform(
                post("/api/climbs/log")
                        .cookie(
                                new Cookie("accessToken", token)
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "date": "2026-09-01",
                                "grade": "V5",
                                "style": "Crimpy",
                                "attempts": 3,
                                "completed": true
                            }
                            """)
        ).andExpect(status().isCreated());

        Climb climb = climbRepository.findAllByUserIdOrderByDateDescTimestampDesc(user.getId())
                .getFirst();

        Long climbId = climb.getId();

        mockMvc.perform(delete("/api/climbs/%d".formatted(climbId))
                .cookie(new Cookie("accessToken", token))).andExpect(status().is2xxSuccessful());


        assertFalse(climbRepository.existsById(climbId));


    }

    @Test
    void UserCanAddNotes() throws Exception {
        User user = new User();

        user.setEmail("test@example.com");
        user.setUsername("testuser");
        user.setPassword("password");
        user.setDescription("Test climber");


        userRepository.save(user);

        String token = jwtService.generateToken(user.getEmail());

        mockMvc.perform(
                        post("/api/climbs/log")
                                .cookie(
                                        new Cookie("accessToken", token)
                                )
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                            {
                                "date": "2026-09-01",
                                "grade": "V5",
                                "style": "Crimpy",
                                "attempts": 3,
                                "completed": true,
                                "notes": "this is an example note"
                            }
                            """)
                )
                .andExpect(status().isCreated());

        Climb climb = climbRepository.findAllByUserIdOrderByDateDescTimestampDesc(user.getId())
                .getFirst();


        assertEquals("this is an example note", climb.getNotes());


    }
}

