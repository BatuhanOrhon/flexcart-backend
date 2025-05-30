package com.app.flexcart.flexcart.backend.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.app.flexcart.flexcart.backend.exception.ActionFactoryException;
import com.app.flexcart.flexcart.backend.exception.ActionFactoryParameterCannotBeNullException;
import com.app.flexcart.flexcart.backend.exception.CampaignNotFoundException;
import com.app.flexcart.flexcart.backend.exception.CartNotFoundException;
import com.app.flexcart.flexcart.backend.exception.ConditionFactoryException;
import com.app.flexcart.flexcart.backend.exception.ConditionFactoryParameterCannotBeNullException;
import com.app.flexcart.flexcart.backend.exception.InvalidParametersFormatException;
import com.app.flexcart.flexcart.backend.exception.ProductNotFoundException;
import com.app.flexcart.flexcart.backend.exception.UserNotFoundException;
import com.app.flexcart.flexcart.backend.exception.dto.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler({
                        ActionFactoryException.class,
                        ConditionFactoryException.class,
                        InvalidParametersFormatException.class
        })
        public ResponseEntity<ErrorResponse> handleBadRequest(RuntimeException ex) {
                ErrorResponse body = new ErrorResponse(
                                ex.getMessage(),
                                HttpStatus.BAD_REQUEST.value());
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
                                HttpStatus.UNPROCESSABLE_ENTITY.value());
                return ResponseEntity
                                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                                .body(body);
        }

        @ExceptionHandler({
                        CartNotFoundException.class,
                        ProductNotFoundException.class,
                        UserNotFoundException.class,
                        CampaignNotFoundException.class,
        })
        public ResponseEntity<ErrorResponse> handleNotFound(RuntimeException ex) {
                ErrorResponse body = new ErrorResponse(
                                ex.getMessage(),
                                HttpStatus.NOT_FOUND.value());
                return ResponseEntity
                                .status(HttpStatus.NOT_FOUND)
                                .body(body);
        }

}
