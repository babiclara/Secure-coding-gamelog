package hr.algebra.gamelog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testUnauthenticatedAccessToGamesReturns401() throws Exception {
        mockMvc.perform(get("/api/games")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testInvalidTokenReturns401() throws Exception {
        mockMvc.perform(get("/api/games")
                        .header("Authorization", "Bearer invalidtoken")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testLoginEndpointIsPublic() throws Exception {
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"wronguser\",\"password\":\"wrongpass\"}"))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void testRegisterEndpointIsPublic() throws Exception {
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"testuser99\",\"email\":\"test99@test.com\",\"password\":\"password123\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void testMvcLoginPageIsPublic() throws Exception {
        mockMvc.perform(get("/auth/login"))
                .andExpect(status().isOk());
    }

    @Test
    void testMvcGamesRequiresAuth() throws Exception {
        mockMvc.perform(get("/games"))
                .andExpect(status().is3xxRedirection());
    }
}