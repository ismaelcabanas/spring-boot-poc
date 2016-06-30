package cabanas.garcia.ismael.springbootpoc.groovy.web.api

import cabanas.garcia.ismael.springbootpoc.helpers.UserHelper
import cabanas.garcia.ismael.springbootpoc.model.User
import cabanas.garcia.ismael.springbootpoc.service.UserService
import cabanas.garcia.ismael.springbootpoc.web.api.UserController
import groovy.json.JsonSlurper
import org.hamcrest.Matchers
import org.hamcrest.core.Is
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

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
            .andDo(log())
        then: "return a list of users"
        response
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath('$[0].name', Is.is("UserName Test")))
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
                .andDo(log())

        then: "return a empty list of users"
        response
                .andExpect(jsonPath('$', Matchers.hasSize(0)))
    }

    def "should return 500 status code when happens an unknown error in user service"(){
        given: "user service throw an exception"
        userMockService.findAll() >> {throw new Exception()}

        when: "call endpoint"
        def response = mvc.perform(
            get(ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
        )
            .andDo(log())

        then: "the response status is 500"
        response
            .andExpect(status().is5xxServerError())
    }

    def "should return a friendly error message when an error is raised in controller"(){
        given: "user service throw an runtime exception"
        def exceptionMessage = "User Service Exception"
        userMockService.findAll() >> {throw new RuntimeException(exceptionMessage)}

        when: "call endpoint"
        def response = mvc.perform(
                get(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andDo(log())

        then: "response contains a friendly error detail"
        response
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath('$.error', Is.is(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())))
                .andExpect(jsonPath('$.status', Is.is(HttpStatus.INTERNAL_SERVER_ERROR.value())))
                .andExpect(jsonPath('$.exception', Is.is(RuntimeException.class.getName())))
                .andExpect(jsonPath('$.timestamp', Matchers.notNullValue()))
                .andExpect(jsonPath('$.path', Matchers.notNullValue()))
                .andExpect(jsonPath('$.message', Is.is(exceptionMessage)))
    }
}
