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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by ismael on 21/06/2016.
 */
public class UserControllerTest extends AbstractControllerTest {

    private static final String USER_NAME = "UserName Test";

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
    public void should_list_users() throws Exception{

        // given
        Collection<User> users = getUsersStubData();
        Mockito.when(userMockService.findAll()).thenReturn(users);

        // when
        String uri = "/api/user";
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .get(uri)
                .accept(MediaType.APPLICATION_JSON_UTF8)).andReturn();

        // then
        Mockito.verify(userMockService, VerificationModeFactory.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(userMockService);

        String content = result.getResponse().getContentAsString();
        HttpStatus status = HttpStatus.valueOf(result.getResponse().getStatus());

        Assert.assertThat("error - El estado HTTP esperado es " + HttpStatus.OK, status, IsEqual.equalTo(HttpStatus.OK));
        Assert.assertTrue("error - El cuerpo de la respuesta HTTP debe tener contenido", content.trim().length() > 0);
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
