package com.mscar.lease.exception;

import com.mscar.lease.model.ErrorMessages;
import com.mscar.lease.utils.LogConstants;
import com.mscar.lease.model.ErrorMessage;
import com.mscar.lease.model.ErrorWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Handler for Lease Api exception
 */
@ControllerAdvice
@Slf4j
public class LeaseApiExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handle technical exception
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorWrapper> handleTechnicalException(Exception ex) {
        log.error(LogConstants.LOG_LEASEAPI_999, ex);
        List<ErrorMessage> errorMessageList = new ArrayList<>();
        errorMessageList.add(new ErrorMessage("INTERNAL_SERVER_ERROR", "Internal Server Error Occurred"));
        return new ResponseEntity<>(new ErrorWrapper(errorMessageList), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle custom lease api exception
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({LeaseApiException.class})
    public ResponseEntity<ErrorWrapper> handleLeaseApiException(LeaseApiException ex) {
        return new ResponseEntity<>(ex.getErrors(), (ex.getStatus() != null ? HttpStatus.resolve(ex.getStatus()) : HttpStatus.INTERNAL_SERVER_ERROR));
    }

    /**
     * Handle database empty result data access exception
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({EmptyResultDataAccessException.class})
    public ResponseEntity<ErrorWrapper> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = new ArrayList<>();
        ErrorWrapper errorWrapper = new ErrorWrapper();
        List<ErrorMessage> errorMessages = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));
        errors.forEach(strMessage -> {
            ErrorMessage errorMessage = new ErrorMessage();
            errorMessage.setMessage(ErrorMessages.valueOf(strMessage).getValue());
            errorMessage.setCode(strMessage);
            errorMessages.add(errorMessage);
            errorWrapper.setErrors(errorMessages);
        });
        return new ResponseEntity<>(errorWrapper, HttpStatus.BAD_REQUEST);
    }
}
