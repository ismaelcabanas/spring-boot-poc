package cabanas.garcia.ismael.springbootpoc.exception;

import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Default implementation of {@link ErrorMessage}.
 *
 * It's a POJO with the following information:
 * <ul>
 *     <li>status - The HTTP status code in the response</li>
 *     <li>exception - The class name of the Exception</li>
 * </ul>
 *
 * Created by ismael on 29/06/2016.
 */
public class DefaultErrorMessage implements ErrorMessage {

    private int status;
    private String exception;
    private String path;
    private String message;
    private Date timestamp;
    private String error;

    /**
     * Set the data error raised in the controller.
     *
     * @param exception a Exception instance.
     * @param request   The HttpServletRequest in which the Exception was raised.
     * @param status    The response HttpStatus instance.
     */
    @Override
    public void fillError(Exception exception, HttpServletRequest request, HttpStatus status) {
        addTimestamp();
        addHttpStatus(status);
        addExceptionDetail(exception);
        addContext(request);
    }

    public int getStatus() {
        return status;
    }

    public String getException() {
        return exception;
    }

    public String getPath() {
        return path;
    }

    public String getMessage() {
        return message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getError() {
        return error;
    }

    private void addTimestamp() {
        this.timestamp = new Date();
    }

    private void addHttpStatus(HttpStatus status) {
        this.status = status.value();
        this.error = status.getReasonPhrase();
    }

    private void addExceptionDetail(Exception exception) {
        this.exception = exception.getClass().getName();
        this.message = exception.getMessage();
    }

    private void addContext(HttpServletRequest request) {
        this.path = request.getServletPath();
    }
}
