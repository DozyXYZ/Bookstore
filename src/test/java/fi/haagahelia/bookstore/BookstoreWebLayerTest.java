package fi.haagahelia.bookstore;

// import static org.hamcrest.Matchers.containsString;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
// add Spring Security Test dependency to pom.xml
import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest
@AutoConfigureMockMvc // disable Spring Boot security for Testing purpose
public class BookstoreWebLayerTest {

    @Autowired
    private MockMvc mockMvc;

    // Status 200: the request from the client was successfully processed
    // by the server
    @Test
    @WithMockUser(username = "user", authorities = { "USER" })
    public void testBooklist() throws Exception {
        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", authorities = { "USER" })
    public void testBookId() throws Exception {
        this.mockMvc.perform(get("/book/1")).andDo(print()).andExpect(status().isOk());
    }

    // Status 3xx: Found response, which means that the requested resource has
    // temporarily moved to a different Uniform Resource Identifier
    // In the Controller, after delete button clicked, redirect to /booklist
    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN" })
    public void testDeleteAdmin() throws Exception {
        this.mockMvc.perform(get("/delete/1")).andDo(print()).andExpect(status().is3xxRedirection());
    }

    // Status 403: Forbidden. Server understand the request but refuses to authorize
    // it. User is not authorized to perform delete
    @Test
    @WithMockUser(username = "user", authorities = { "USER" })
    public void testDeleteUser() throws Exception {
        this.mockMvc.perform(get("/delete/1")).andDo(print()).andExpect(status().isForbidden());
    }

    // Status 403: User cannot edit
    @Test
    @WithMockUser(username = "user", authorities = { "USER" })
    public void testEditUser() throws Exception {
        this.mockMvc.perform(get("/edit/1")).andDo(print()).andExpect(status().isForbidden());
    }

    // Status 200: Admin clicked edit button and go to edit page
    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN" })
    public void testEditAdmin() throws Exception {
        this.mockMvc.perform(get("/edit/1")).andDo(print()).andExpect(status().isOk());
    }

}
