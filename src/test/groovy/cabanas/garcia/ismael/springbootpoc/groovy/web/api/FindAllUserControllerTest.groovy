package cabanas.garcia.ismael.springbootpoc.groovy.web.api

import cabanas.garcia.ismael.springbootpoc.helpers.UserHelper
import cabanas.garcia.ismael.springbootpoc.model.User
import cabanas.garcia.ismael.springbootpoc.service.UserService
import cabanas.garcia.ismael.springbootpoc.web.api.UserController
import groovy.json.JsonSlurper
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log

/**
 * Created by ismael on 24/06/2016.
 */
class FindAllUserControllerTest extends Specification {

    static final String USER_NAME = "UserName Test";

    static final ENDPOINT = "/api/user";

    MockMvc mvc

    @Autowired
    WebApplicationContext webApplicationContext

    UserService userMockService

    UserController controller

    def setup(){
        userMockService = Mock(UserService)
        controller = new UserController(userMockService)
        mvc = MockMvcBuilders.standaloneSetup(controller).build()
    }

    def cleanup(){}

    def setupSpec(){}

    def cleanupSpec(){}

    def "200 status code when GET request to /api/user endpoint"(){
        given:

        when: 'call endpoint'
            def response = mvc.perform(
                get(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
            )
                .andDo(log()).andReturn().response
        then: 'response status code is 200'
            response.status == HttpStatus.OK.value()
    }

    def "should call findAll user service method"(){
        when: 'call endpoint'
            mvc.perform(
                get(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andDo(log())
        then: 'findAll method is called once'
            1 * userMockService.findAll()
            0 * userMockService.findAll()
    }

    def "should return users"(){
        given: "user service return users's list"
            userMockService.findAll() >> UserHelper.getUsersStubData()
        when: "call endpoint"
            def response = mvc.perform(
                    get(ENDPOINT)
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .accept(MediaType.APPLICATION_JSON_UTF8)
            )
                .andDo(log()).andReturn().response
            def content = response.contentAsString
        then: "return a list of users"
            content == "[{\"name\":\"UserName Test\"}]"
    }

    def "should return empty users's list"(){
        given: "user service return a empty users's list"
        userMockService.findAll() >> new ArrayList<User>()
        when: "call endpoint"
            def response = mvc.perform(
                    get(ENDPOINT)
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .accept(MediaType.APPLICATION_JSON_UTF8)
            )
                .andDo(log()).andReturn().response
            def content = response.contentAsString
        then: "return a list of users"
            content == "[]"
    }

}
