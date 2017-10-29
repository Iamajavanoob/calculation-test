package calculation.operation.impl;

import calculation.operation.FunctionInterface;
import org.springframework.stereotype.Component;

@Component
public class Subtraction implements FunctionInterface {

    @Override
    public Double operand(Double operand1, Double operand2) {
        return operand1 - operand2;
    }

    @Override
    public String getOperationSign() {
        return "-";
    }
}
