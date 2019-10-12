package com.project.wallet.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.wallet.dto.UserDTO;
import com.project.wallet.entity.User;
import com.project.wallet.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class UserControllerTest {

    private static final Long ID = 1L;
    private static final String NAME = "User Test";
    private static final String EMAIL = "email@test.com";
    private static final String PASSWORD = "123456";
    private static final String URL = "/users";

    @MockBean
    UserService userService;

    @Autowired
    MockMvc mvc;

    @Test
    public void testSave_UserController() throws Exception {

        BDDMockito.given(userService.save(Mockito.any(User.class))).willReturn(getMockUser());

        mvc.perform(MockMvcRequestBuilders.post(URL).content(getJsonPayload(ID, EMAIL, NAME, PASSWORD))
                .contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").value(ID))
                .andExpect(jsonPath("$.data.email").value(EMAIL))
                .andExpect(jsonPath("$.data.name").value(NAME))
                .andExpect(jsonPath("$.data.password").doesNotExist());
    }

    @Test
    public void testSaveInvalidUser_UserController() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post(URL).content(getJsonPayload(ID, "email", NAME, PASSWORD))
                .contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]").value("Invalid email"));
    }

    public User getMockUser() {
        User user = new User();
        user.setId(ID);
        user.setEmail(EMAIL);
        user.setName(NAME);
        user.setPassword(PASSWORD);

        return user;
    }

    public String getJsonPayload(Long id, String email, String name, String password) throws JsonProcessingException {
        UserDTO dto = new UserDTO();
        dto.setId(id);
        dto.setEmail(email);
        dto.setName(name);
        dto.setPassword(password);

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(dto);
    }

}
