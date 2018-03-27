/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private UserController userController;

    @MockBean
    private UserRepository userRepository;

    private JacksonTester<User> jsonTester;

    @Before
    public void setup() {
        JacksonTester.initFields(this, objectMapper);
    }

    @After
    public void cleanup(){
        userController.delete_all();
    }

    @Test
    public void getUserWithAnNonAuthorizedConsumer() throws Exception {
        given(userRepository.findUserByUserId(1)).willReturn(null);
        mockMvc.perform(get("/jrt/api/v1.0/users"))
                .andExpect(status().is(HttpStatus.FOUND.value()));
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    public void getNonExistentUser() throws Exception {
        given(userRepository.findUserByUserId(1)).willReturn(null);
        mockMvc.perform(get("/jrt/api/v1.0/user/{userId}", 1).with(csrf()))
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    public void getUserInvalidIdFormat() throws Exception {
        given(userRepository.findUserByUserId(1)).willReturn(null);
        mockMvc.perform(get("/jrt/api/v1.0/user/{userId}", "X").with(csrf()))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }


    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    public void getExistentUser() throws Exception {
        User user = new User(5, "Carlos", "Patino", 53.4239330, -7.9406900);
        given(userRepository.findUserByUserId(1)).willReturn(user);
        mockMvc.perform(get("/jrt/api/v1.0/user/{userId}", 1).with(csrf()))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.firstName", is(user.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(user.getLastName())))
                .andExpect(jsonPath("$.latitude", is(user.getLatitude())))
                .andExpect(jsonPath("$.longitude", is(user.getLongitude())));
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    public void addUserWhenPreviousUserAlreadyExists() throws Exception {
        User user = new User(5, "Carlos", "Patino", 53.4239330, -7.9406900);
        final String userJson = jsonTester.write(user).getJson();

        given(userRepository.findUserByUserId(user.getUserId())).willReturn(user);
        mockMvc.perform(post("/jrt/api/v1.0/user").with(csrf())
                .contentType(APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().is(HttpStatus.CONFLICT.value()));
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    public void addUserSuccessfully() throws Exception {
        User user = new User(5, "Carlos", "Patino", 53.4239330, -7.9406900);
        final String userJson = jsonTester.write(user).getJson();
        given(userRepository.findUserByUserId(user.getUserId())).willReturn(null);
        given(userRepository.save(any(User.class))).willReturn(user);
        mockMvc.perform(post("/jrt/api/v1.0/user").with(csrf())
                .contentType(APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().is(HttpStatus.CREATED.value()));
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    public void addUserInvalidDetails() throws Exception {
        User user = new User(5, "Very long name that should fail since this is not a valid name otherwise this person will be in the guiness record", "Patino", 53.4239330, -7.9406900);
        final String userJson = jsonTester.write(user).getJson();

        given(userRepository.findUserByUserId(user.getUserId())).willReturn(null);
        given(userRepository.save(any(User.class))).willReturn(user);
        mockMvc.perform(post("/jrt/api/v1.0/user").with(csrf())
                .contentType(APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    public void updateUserSuccessfully() throws Exception {
        User user = new User(5, "Carlos", "Patino", 53.4239330, -7.9406900);
        User userUpdated = new User(5, "Luis", "Hernandez", 53.4239330, -7.9406900);
        final String userJson = jsonTester.write(userUpdated).getJson();

        given(userRepository.findUserByUserId(user.getUserId())).willReturn(user);
        given(userRepository.save(any(User.class))).willReturn(user);
        mockMvc.perform(put("/jrt/api/v1.0/user/{userId}", user.getUserId()).with(csrf())
                .contentType(APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.firstName", is(userUpdated.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(userUpdated.getLastName())))
                .andExpect(jsonPath("$.latitude", is(userUpdated.getLatitude())))
                .andExpect(jsonPath("$.longitude", is(userUpdated.getLongitude())));
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    public void updateNonExistentUser() throws Exception {
        User user = new User(5, "Carlos", "Patino", 53.4239330, -7.9406900);
        final String userJson = jsonTester.write(user).getJson();
        given(userRepository.findUserByUserId(user.getUserId())).willReturn(null);
        mockMvc.perform(put("/jrt/api/v1.0/user/{userId}", user.getUserId()).with(csrf())
                .contentType(APPLICATION_JSON).content(userJson))
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }


    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    public void getUsers() throws Exception {
        User user = new User(1, "Carlos", "Patino", 53.4239330, -7.9406900);
        Collection<User>users = singletonList(user);

        given(userRepository.findAll()).willReturn((List) users);

        mockMvc.perform(get("/jrt/api/v1.0/users").with(csrf())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].firstName", is(user.getFirstName())))
                .andExpect(jsonPath("$[0].lastName", is(user.getLastName())))
                .andExpect(jsonPath("$[0].latitude", is(user.getLatitude())))
                .andExpect(jsonPath("$[0].longitude", is(user.getLongitude())));
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    public void getUsersEmpty() throws Exception {
        Collection<User>users = new ArrayList<>();
        given(userRepository.findAll()).willReturn((List) users);

        mockMvc.perform(get("/jrt/api/v1.0/users").with(csrf())
                .contentType(APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.NO_CONTENT.value()))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    public void deleteUserSuccessfully() throws Exception {
        User user = new User(5, "", "", 0.0, 0.0);
        final String userJson = jsonTester.write(user).getJson();

        given(userRepository.findUserByUserId(user.getUserId())).willReturn(user);
        mockMvc.perform(delete("/jrt/api/v1.0/user/{userId}", user.getUserId()).with(csrf())
                .contentType(APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().is(HttpStatus.OK.value()));
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    public void deleteAllUsers() throws Exception {
        mockMvc.perform(delete("/jrt/api/v1.0/user").with(csrf())
                .contentType(APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.OK.value()));
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    public void deleteNonExistentUser() throws Exception {
        User user = new User(5, "", "", 0.0, 0.0);
        final String userJson = jsonTester.write(user).getJson();

        given(userRepository.findUserByUserId(user.getUserId())).willReturn(null);
        mockMvc.perform(delete("/jrt/api/v1.0/user/{userId}", user.getUserId()).with(csrf())
                .contentType(APPLICATION_JSON).content(userJson))
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    public void getDistance() throws Exception {
        User uno = new User(1, "Carlos", "", 53.421543, -7.942274);
        User dos = new User(2, "Luis", "", 53.422303, -7.942306 );
        User tres = new User(3, "Pedro", "", 53.422287, -7.942070 );
        User cuatro = new User(4, "Jose", "", 53.422156, -7.942016 );
        List users = Arrays.asList(uno, dos, tres, cuatro);
        given(userRepository.findAll()).willReturn(users);
        mockMvc.perform(get("/jrt/api/v1.0/distances").with(csrf()))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.sum", closeTo(294, 2)))
                .andExpect(jsonPath("$.min", closeTo(15, 2)))
                .andExpect(jsonPath("$.average", closeTo(49, 2)))
                .andExpect(jsonPath("$.max", closeTo(84, 2)));
    }
}
