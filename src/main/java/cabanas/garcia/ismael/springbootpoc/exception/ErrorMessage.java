package cabanas.garcia.ismael.springbootpoc.exception;

import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * The ErrorMessage interface defines the behavioral contract to be
 * implemented by ErrorMessage concrete classs.
 *
 * Provides the necessary information for describe errors and the
 * context that they have happened.
 *
 * Created by ismael on 29/06/2016.
 */
public interface ErrorMessage {

    /**
     * Set the data error raised in the controller.
     *
     * @param exception a Exception instance.
     * @param request The HttpServletRequest in which the Exception was raised.
     * @param status The response HttpStatus instance.
     */
    void fillError(Exception exception, HttpServletRequest request, HttpStatus status);


}