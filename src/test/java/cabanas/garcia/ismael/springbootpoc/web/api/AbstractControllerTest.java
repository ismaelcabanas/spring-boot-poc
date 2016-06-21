package cabanas.garcia.ismael.springbootpoc.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by ismael on 19/06/2016.
 */
public abstract class AbstractControllerTest {

    protected MockMvc mvc;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    protected void setUp(){
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    protected void setUp(UserController controller){
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

}
