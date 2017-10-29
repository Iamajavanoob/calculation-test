package calculation.services;


import calculation.bean.CalculateDto;
import calculation.bean.CalculationGetDto;
import calculation.components.Calculator;
import calculation.exception.CalculationException;
import calculation.operation.FunctionInterface;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * The service is used to do the calculation based on the input calculateDto
 * in which contains operand1, operand2 and operation. It returns the dto
 * which contains the result.
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class CalculationService {

    private final Calculator calculator;

    /**
     * This method is used to do the calculation based on the given dto
     * and return the corresponding result.
     *
     * The logic has two steps:
     * 1. get the operation object based on the operation sign. e.g.
     * if operation is "+", the object Addition is returned.
     * 2. do the calculation by calling the function method in the
     * object Addition and return the result.
     *
     * @param calculateDto  The dto containing operand1, operand2, operation
     * @return the dto containing the result.
     */
    @SneakyThrows
    public CalculationGetDto getCalculationResult(CalculateDto calculateDto) {
        if (StringUtils.isEmpty(calculateDto.getOperation())) {
            log.error("Operation cannot be empty");
            throw new CalculationException("Operation cannot be empty");
        }
        Optional<FunctionInterface> functionInterfaceOptional = calculator.getOperation(calculateDto.getOperation());
        functionInterfaceOptional.orElseThrow(() -> new CalculationException("The operation " + calculateDto.getOperation() + " is not supported."));

        Double result = calculator.getResult(calculateDto.getOperand1(), calculateDto.getOperand2(), functionInterfaceOptional.get()::operand);
        return new CalculationGetDto().setResult(result);
    }

}
