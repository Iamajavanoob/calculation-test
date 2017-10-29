package calculation.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CalculationErrorDto {

    private final String errorCode;
    private final String errorDetails;

}
