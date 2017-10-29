package calculation.services;

import calculation.bean.CalculateDto;
import calculation.bean.CalculationGetDto;
import calculation.components.Calculator;
import calculation.exception.CalculationException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class CalculationServiceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @TestConfiguration
    static class CalculationServiceTestContextConfiguration {

        @Bean
        public Calculator calculator() {
            return new Calculator();
        }

        @Bean
        public CalculationService calculationService() {
            return new CalculationService(calculator());
        }

    }

    @Autowired
    private CalculationService calculationService;

    @Test
    public void getResultWithAddtionDto() {
        CalculateDto additionDto = new CalculateDto()
                .setOperand1(1.5)
                .setOperation("+")
                .setOperand2(4.3);

        CalculationGetDto calculationGetDto = calculationService.getCalculationResult(additionDto);
        assertTrue(calculationGetDto.getResult().doubleValue() == 5.8);
        assertFalse(calculationGetDto.getResult().doubleValue() == 5.7);
    }

    @Test
    public void getResultWithSubtractionDto() {
        CalculateDto subtractionDto = new CalculateDto()
                .setOperand1(1.5)
                .setOperation("-")
                .setOperand2(4.3);

        CalculationGetDto calculationGetDto = calculationService.getCalculationResult(subtractionDto);
        assertTrue(calculationGetDto.getResult().doubleValue() == -2.8);
        assertFalse(calculationGetDto.getResult().doubleValue() == 5.9);
    }

    @Test
    public void getResultWithMultiplicationDto() {
        CalculateDto multiplicationDto = new CalculateDto()
                .setOperand1(1.5)
                .setOperation("*")
                .setOperand2(4.0);

        CalculationGetDto calculationGetDto = calculationService.getCalculationResult(multiplicationDto);
        assertTrue(calculationGetDto.getResult().doubleValue() == 6);
        assertFalse(calculationGetDto.getResult().doubleValue() == 9.9);
    }

    @Test
    public void getResultWithDivisionDto() {
        CalculateDto divisionDto = new CalculateDto()
                .setOperand1(9.0)
                .setOperation("/")
                .setOperand2(2.0);

        CalculationGetDto calculationGetDto = calculationService.getCalculationResult(divisionDto);
        assertTrue(calculationGetDto.getResult().doubleValue() == 4.5);
        assertFalse(calculationGetDto.getResult().doubleValue() == 5.9);
    }

    @Test
    public void throwExceptionWithInvalidCalculationDtoWithEmptyOperation() {
        // This dto is invalid as it does not have operation.
        CalculateDto calculateDto = new CalculateDto()
                .setOperand1(9.0)
                .setOperand2(2.0);

        expectedException.expect(CalculationException.class);
        expectedException.expectMessage("Operation cannot be empty");

        calculationService.getCalculationResult(calculateDto);
    }

    @Test
    public void throwExceptionWithInvalidCalculationDtoWithInvalidOperation() {
        // This dto is invalid as it has operation "%" which does not exist.
        // only "+" "-" "*" "/" are supported currently.
        CalculateDto calculateDto = new CalculateDto()
                .setOperand1(9.0)
                .setOperand2(2.0)
                .setOperation("%");

        expectedException.expect(CalculationException.class);
        expectedException.expectMessage("The operation % is not supported.");

        calculationService.getCalculationResult(calculateDto);
    }

}
