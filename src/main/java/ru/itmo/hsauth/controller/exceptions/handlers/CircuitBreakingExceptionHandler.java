package ru.itmo.hsauth.controller.exceptions.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.itmo.hsauth.controller.exceptions.ControllerException;
import ru.itmo.hsauth.controller.exceptions.fallback.DubControllerException;
import ru.itmo.hsauth.controller.exceptions.fallback.ServiceUnavailableException;
import ru.itmo.hsauth.model.dto.ErrorDTO;

@ControllerAdvice
public class CircuitBreakingExceptionHandler {

    @ExceptionHandler(ServiceUnavailableException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    protected ErrorDTO handleServiceUnavailability(ControllerException ex) {
        return new ErrorDTO(
                ex.getTimestamp(),
                ex.getMessage(),
                ex.getError()
        );
    }

    @ExceptionHandler(DubControllerException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    protected ErrorDTO handleServiceException(ControllerException ex) {
        return new ErrorDTO(
                ex.getTimestamp(),
                ex.getMessage(),
                ex.getError()
        );
    }
}
