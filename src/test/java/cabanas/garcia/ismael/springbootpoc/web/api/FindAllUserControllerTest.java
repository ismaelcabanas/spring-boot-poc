package cabanas.garcia.ismael.springbootpoc.web.api;

import cabanas.garcia.ismael.springbootpoc.model.User;
import cabanas.garcia.ismael.springbootpoc.service.UserService;
import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Unit tests for method GET of endpoint /api/user.
 *
 * Created by ismael on 21/06/2016.
 */
public class FindAllUserControllerTest extends AbstractControllerTest {

    private static final String USER_NAME = "UserName Test";

    private static final String ENDPOINT = "/api/user";

    @Mock
    private UserService userMockService;

    @InjectMocks
    private UserController controller;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        setUp(controller);
    }

    @Test
    public void should_call_findAll_user_service_when_call_endpoint() throws Exception{

        // given

        // when
        mvc.perform(
                get(ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                )
                .andDo(log());

        // then
        Mockito.verify(userMockService, VerificationModeFactory.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(userMockService);

    }

    @Test
    public void should_return_200_status_code_when_call_endpoint() throws Exception{

        // given

        // when
        mvc.perform(
                get(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                )
                .andExpect(status().is2xxSuccessful())
                .andDo(log());

    }


    @Test
    public void should_return_users_when_call_endpoint() throws Exception{

        // given
        Collection<User> users = getUsersStubData();
        Mockito.when(userMockService.findAll()).thenReturn(users);

        // when
        mvc.perform(
                get(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("[{\"name\":\"UserName Test\"}]"))
                .andDo(log());

    }

    @Test
    public void should_return_empty_users_when_call_endpoint() throws Exception{

        // given
        Collection<User> users = new ArrayList<User>();
        Mockito.when(userMockService.findAll()).thenReturn(users);

        // when
        mvc.perform(
                get(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("[]"))
                .andDo(log());

    }

    private Collection<User> getUsersStubData() {
        Collection<User> list = new ArrayList<User>();
        list.add(getUserStubData());
        return list;
    }

    private User getUserStubData() {
        User user = new User.UserBuilder()
                .name(USER_NAME)
                .build();
        return user;
    }

}
