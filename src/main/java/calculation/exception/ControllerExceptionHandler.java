package calculation.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * This handler is to handle all the exceptions thrown from service.
 * When the exception is caught, it will be checked and transferred to proper rest
 * response back to the client.
 *
 */
@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ControllerExceptionHandler {

    /**
     * Handle CalculationException and return the response with http code 500
     * and corresponding error message.
     *
     * @param calculationException customerised message used to determine
     *                             the internal error occurred in the service.
     * @return The Response with error code and error details.
     */
    @ExceptionHandler(value = {CalculationException.class})
    public ResponseEntity<CalculationErrorDto> handleCalculationException(CalculationException calculationException) {
        log.error("Intercepted an CalculationException", calculationException);
        return respond(HttpStatus.INTERNAL_SERVER_ERROR, calculationException.getMessage());
    }

    private ResponseEntity<CalculationErrorDto> respond( HttpStatus errorCode, String errorMessage) {
        return ResponseEntity.status(errorCode).body(new CalculationErrorDto(Integer.toString(errorCode.value()), errorMessage));
    }

}
