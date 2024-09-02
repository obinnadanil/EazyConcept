package obi.com.eazyconcept.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(ElementNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public  String handleElementNotFound(){
        return "elementNotFound";
    }
}
