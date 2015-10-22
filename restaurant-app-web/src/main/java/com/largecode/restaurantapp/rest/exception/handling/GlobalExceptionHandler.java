package com.largecode.restaurantapp.rest.exception.handling;

import com.largecode.restaurantapp.service.api.exception.VoteCannotBeAcceptedException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class GlobalExceptionHandler {
    
    private static final Logger LOG = Logger.getLogger(GlobalExceptionHandler.class.getName());
    
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorInfo handleDefault(HttpServletRequest request, Exception ex) {
        return handle(request, ex);
    }
    
    
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(VoteCannotBeAcceptedException.class)
    public ErrorInfo handleBadRequest(HttpServletRequest request, Exception ex) {
        return handle(request, ex);
    }
    
    private ErrorInfo handle(HttpServletRequest request, Exception ex) {
        LOG.log(Level.SEVERE, ex.getMessage(), ex);
        return new ErrorInfo(request.getRequestURL().toString(), ex);
    }
}
