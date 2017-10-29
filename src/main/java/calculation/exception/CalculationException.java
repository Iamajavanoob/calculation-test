package calculation.exception;

import lombok.Getter;

@Getter
public class CalculationException extends RuntimeException {

    public CalculationException(String errors) {
        super(errors);
    }
}
