package com.app.flexcart.flexcart.backend.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.app.flexcart.flexcart.backend.exception.*;
import com.app.flexcart.flexcart.backend.exception.dto.ErrorResponse;

@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler({
        ActionFactoryException.class,
        ConditionFactoryException.class,
        InvalidParametersFormatException.class
    })
    public ResponseEntity<ErrorResponse> handleBadRequest(RuntimeException ex) {
        ErrorResponse body = new ErrorResponse(
            ex.getMessage(),
            HttpStatus.BAD_REQUEST.value()
        );
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(body);
    }

    @ExceptionHandler({
        ActionFactoryParameterCannotBeNullException.class,
        ConditionFactoryParameterCannotBeNullException.class
    })
    public ResponseEntity<ErrorResponse> handleUnprocessableEntity(RuntimeException ex) {
        ErrorResponse body = new ErrorResponse(
            ex.getMessage(),
            HttpStatus.UNPROCESSABLE_ENTITY.value()
        );
        return ResponseEntity
            .status(HttpStatus.UNPROCESSABLE_ENTITY)
            .body(body);
    }

    @ExceptionHandler({
        CartNotFoundException.class,
        ProductNotFoundException.class,
        UserNotFoundException.class
    })
    public ResponseEntity<ErrorResponse> handleNotFound(RuntimeException ex) {
        ErrorResponse body = new ErrorResponse(
            ex.getMessage(),
            HttpStatus.NOT_FOUND.value()
        );
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(body);
    }

}
