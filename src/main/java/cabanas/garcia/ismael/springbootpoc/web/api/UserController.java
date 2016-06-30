package cabanas.garcia.ismael.springbootpoc.web.api;

import cabanas.garcia.ismael.springbootpoc.exception.DefaultErrorMessage;
import cabanas.garcia.ismael.springbootpoc.exception.ErrorMessage;
import cabanas.garcia.ismael.springbootpoc.model.User;
import cabanas.garcia.ismael.springbootpoc.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Date;

/**
 * The UserController class is a RESTful web service controller.
 *
 * The <code>@RestController</code> annotation informs Spring that
 * each <code>@RequestMapping</code> method returns a <code>@ResponseBody</code>
 * which, by default, contains a ResponseEntity converted into JSON with an
 * associated HTTP status code.
 *
 * Created by ismael on 19/06/2016.
 */
@RestController
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    /**
     * The user service.
     */
    @Autowired
    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    /**
     * Find all users.
     *
     * @return A users's collection with 200 status code.
     */
    @RequestMapping(value = "/api/user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Collection<User>> findAll(){
        LOGGER.debug("findAll()");
        Collection<User> users = userService.findAll();

        return new ResponseEntity<Collection<User>>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/user/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public User findBy(){
        return null;
    }

    /**
     * Maneja todas aquellas excepciones que no están gestionadas por otros métodos
     * anotados por <code>ExceptionHandler</code>. Crea una respuesta con un código
     * de estado 500, error interno del servidor.
     *
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorMessage> handleException(Exception exception, HttpServletRequest request){
        ErrorMessage responseBody = new DefaultErrorMessage();
        responseBody.fillError(exception, request, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
