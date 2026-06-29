package hr.algebra.gamelog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MvcControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private MockHttpSession loginAsAdmin() throws Exception {
        MvcResult result = mockMvc.perform(formLogin("/auth/login")
                        .user("admin").password("changeme_admin"))
                .andReturn();
        return (MockHttpSession) result.getRequest().getSession();
    }

    private MockHttpSession loginAsUser() throws Exception {
        MvcResult result = mockMvc.perform(formLogin("/auth/login")
                        .user("user").password("changeme_user"))
                .andReturn();
        return (MockHttpSession) result.getRequest().getSession();
    }

    @Test
    void testLoginPageLoads() throws Exception {
        mockMvc.perform(get("/auth/login"))
                .andExpect(status().isOk());
    }

    @Test
    void testRegisterPageLoads() throws Exception {
        mockMvc.perform(get("/auth/register"))
                .andExpect(status().isOk());
    }

    @Test
    void testGamesListPageLoadsForUser() throws Exception {
        MockHttpSession session = loginAsUser();
        mockMvc.perform(get("/games").session(session))
                .andExpect(status().isOk());
    }

    @Test
    void testNewGameFormLoadsForAdmin() throws Exception {
        MockHttpSession session = loginAsAdmin();
        mockMvc.perform(get("/games/new").session(session))
                .andExpect(status().isOk());
    }

    @Test
    void testNewGameFormForbiddenForUser() throws Exception {
        MockHttpSession session = loginAsUser();
        mockMvc.perform(get("/games/new").session(session))
                .andExpect(status().isForbidden());
    }

    @Test
    void testGameDetailRedirectsWhenNotFound() throws Exception {
        MockHttpSession session = loginAsUser();
        mockMvc.perform(get("/games/9999").session(session))
                .andExpect(status().is3xxRedirection());
    }
}