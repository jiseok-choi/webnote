package toyproject.webnote.domain.user.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import toyproject.webnote.domain.user.application.UserDetailService;
import toyproject.webnote.domain.user.application.UserService;
import toyproject.webnote.domain.user.dto.UserCreateRequest;
import toyproject.webnote.global.security.filter.jwt.JwtAuthenticationProvider;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@MockBean(JpaMetamodelMappingContext.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @MockBean
    UserDetailService userDetailService;

    @MockBean
    JwtAuthenticationProvider jwtAuthenticationProvider;

    @Test
    @DisplayName("사용자 추가 테스트")
    void insertUserTest() throws Exception {
        UserCreateRequest request = new UserCreateRequest();
        request.setEmail("test@email.com");
        request.setName("test");
        request.setPassword("password");

        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(request);

        mockMvc.perform(post("/user/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isCreated());

        verify(userService).createUser(refEq(request));
    }
}