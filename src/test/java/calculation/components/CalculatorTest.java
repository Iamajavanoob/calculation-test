package calculation.components;

import calculation.operation.FunctionInterface;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class CalculatorTest {

    @TestConfiguration
    static class CalculatorTestContextConfiguration {

        @Bean
        public Calculator calculator() {
            return new Calculator();
        }
    }

    @Autowired
    private Calculator calculator;

    @Test
    public void getOperationWithValidInputParameterPlus(){
        assertCorrectOperations("+");
    }

    @Test
    public void getOperationWithValidInputParameterMinus() {
        assertCorrectOperations("-");
    }

    @Test
    public void getOperationWithValidInputParameterMultiplication() {
        assertCorrectOperations("*");
    }

    @Test
    public void cannotGetOperationWithInvalidInputParameterModulo() {
        Optional<FunctionInterface> operationObject = calculator.getOperation("%");
        assertFalse(operationObject.isPresent());
    }

    @Test
    public void getOperationWithValidInputParameterDivision() {
        assertCorrectOperations("/");
    }

    private void assertCorrectOperations(String operation) {
        Optional<FunctionInterface> operationObject = calculator.getOperation(operation);
        assertNotNull(operationObject.get());
        assertNotNull(operationObject.get().getOperationSign());
        assertTrue(operationObject.get().getOperationSign().equals(operation));
    }
}
