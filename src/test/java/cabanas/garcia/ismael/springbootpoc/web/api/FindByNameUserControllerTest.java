package cabanas.garcia.ismael.springbootpoc.web.api;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

/**
 * Unit tests for method GET of endpoint /api/user/{name}.
 *
 * Created by ismael on 23/06/2016.
 */
public class FindByNameUserControllerTest extends AbstractControllerTest {

    private static final String ENDPOINT = "/api/user/{name}";

    @InjectMocks
    private UserController controller;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        super.setUp(controller);
    }

    @Test
    public void should_return_200_status_code_when_call_endpoint() throws Exception{

        // given
        String userName = "test name";

        // when
        mvc.perform(get(ENDPOINT, userName))
                .andExpect(status().is2xxSuccessful())
                .andDo(log());

    }

}
