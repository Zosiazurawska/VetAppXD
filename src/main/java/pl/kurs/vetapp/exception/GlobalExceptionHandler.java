package pl.kurs.vetapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import pl.kurs.vetapp.exception.client.ClientHaveNotAllParametersException;
import pl.kurs.vetapp.exception.client.ClientNotFoundException;
import pl.kurs.vetapp.exception.doctor.DoctorHaveNotAllParametersException;
import pl.kurs.vetapp.exception.doctor.DoctorNotFoundException;
import pl.kurs.vetapp.exception.doctor.DuplicationNIPException;
import pl.kurs.vetapp.exception.doctor.SalaryCanNotByNegative;
import pl.kurs.vetapp.exception.visit.*;

import java.time.LocalDateTime;

@ControllerAdvice
@EnableWebMvc
public class GlobalExceptionHandler {

    // Doctor exceptions
    @ExceptionHandler(value = DoctorHaveNotAllParametersException.class)
    public ResponseEntity<ExceptionResponse> handleDoctorHaveNotAllParametersException(Exception exception){
        ExceptionResponse exceptionResponse = new ExceptionResponse(exception.getMessage(), HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }
    @ExceptionHandler(value = DoctorNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleDoctorNotFoundException(Exception exception){
        ExceptionResponse exceptionResponse = new ExceptionResponse(exception.getMessage(), HttpStatus.NOT_FOUND, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
    }
    @ExceptionHandler(value = DuplicationNIPException.class)
    public ResponseEntity<ExceptionResponse> handleDuplicationNIPException(Exception exception){
        ExceptionResponse exceptionResponse = new ExceptionResponse(exception.getMessage(), HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }
    @ExceptionHandler(value = SalaryCanNotByNegative.class)
    public ResponseEntity<ExceptionResponse> handleSalaryCanNotByNegative(Exception exception){
        ExceptionResponse exceptionResponse = new ExceptionResponse(exception.getMessage(), HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }

    // Client exceptions
    @ExceptionHandler(value = ClientHaveNotAllParametersException.class)
    public ResponseEntity<ExceptionResponse> handleClientHaveNotAllParametersException(Exception exception){
        ExceptionResponse exceptionResponse = new ExceptionResponse(exception.getMessage(), HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }
    @ExceptionHandler(value = ClientNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleClientNotFoundException(Exception exception){
        ExceptionResponse exceptionResponse = new ExceptionResponse(exception.getMessage(), HttpStatus.NOT_FOUND, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
    }

    // Visit exceptions
    @ExceptionHandler(value = VisitHaveNotAllParametersException.class)
    public ResponseEntity<ExceptionResponse> handleVisitHaveNotAllParametersException(Exception exception){
        ExceptionResponse exceptionResponse = new ExceptionResponse(exception.getMessage(), HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }
    @ExceptionHandler(value = VisitNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleVisitNotFoundException(Exception exception){
        ExceptionResponse exceptionResponse = new ExceptionResponse(exception.getMessage(), HttpStatus.NOT_FOUND, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
    }
    @ExceptionHandler(value = StartDateIsBeforeNowException.class)
    public ResponseEntity<ExceptionResponse> handleStartDateIsBeforeNowException(Exception exception){
        ExceptionResponse exceptionResponse = new ExceptionResponse(exception.getMessage(), HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }
    @ExceptionHandler(value = VisitIsAlreadyBookedException.class)
    public ResponseEntity<ExceptionResponse> handleVisitIsAlreadyBookedException(Exception exception){
        ExceptionResponse exceptionResponse = new ExceptionResponse(exception.getMessage(), HttpStatus.NOT_FOUND, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
    }
    @ExceptionHandler(value = VisitTimeIsNotAvailableException.class)
    public ResponseEntity<ExceptionResponse> handleVisitTimeIsNotAvailableException(Exception exception){
        ExceptionResponse exceptionResponse = new ExceptionResponse(exception.getMessage(), HttpStatus.NOT_FOUND, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
    }
    @ExceptionHandler(value = VisitWithTokenNotFound.class)
    public ResponseEntity<ExceptionResponse> handleVisitWithTokenNotFound(Exception exception){
        ExceptionResponse exceptionResponse = new ExceptionResponse(exception.getMessage(), HttpStatus.NOT_FOUND, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
    }
}
