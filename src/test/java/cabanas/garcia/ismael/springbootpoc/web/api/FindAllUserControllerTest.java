package cabanas.garcia.ismael.springbootpoc.web.api;

import cabanas.garcia.ismael.springbootpoc.helpers.UserHelper;
import cabanas.garcia.ismael.springbootpoc.model.User;
import cabanas.garcia.ismael.springbootpoc.service.UserService;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.Collection;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit tests for method GET of endpoint /api/user.
 *
 * Created by ismael on 21/06/2016.
 */
public class FindAllUserControllerTest extends AbstractControllerTest {

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
                .andDo(log())
                .andExpect(status().is2xxSuccessful());

    }


    @Test
    public void should_return_users_when_call_endpoint() throws Exception{

        // given
        Collection<User> users = UserHelper.getUsersStubData();
        Mockito.when(userMockService.findAll()).thenReturn(users);

        // when
        mvc.perform(
                get(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                )
                .andDo(log())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$[0].name", Is.is("UserName Test")));

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
                .andDo(log())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", Matchers.hasSize(0)));

    }

    @Test
    public void should_return_500_status_code_when_user_service_throws_exception() throws Exception{
        // given
        Mockito.doThrow(Exception.class).when(userMockService).findAll();

        // when
        mvc.perform(
                get(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
             )
                .andDo(log())
                .andExpect(status().is5xxServerError());
    }

    @Test
    public void should_return_error_message_when_user_service_throws_exception() throws Exception{
        // given
        final String exceptionMessage = "User Service Exception";
        Mockito.when(userMockService.findAll()).thenThrow(new RuntimeException(exceptionMessage));

        // when
        mvc.perform(
                get(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                )
                .andDo(log())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.error", Is.is(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())))
                .andExpect(jsonPath("$.status", Is.is(HttpStatus.INTERNAL_SERVER_ERROR.value())))
                .andExpect(jsonPath("$.exception", Is.is(RuntimeException.class.getName())))
                .andExpect(jsonPath("$.timestamp", Matchers.notNullValue()))
                .andExpect(jsonPath("$.path", Matchers.notNullValue()))
                .andExpect(jsonPath("$.message", Is.is(exceptionMessage)));
    }

}
