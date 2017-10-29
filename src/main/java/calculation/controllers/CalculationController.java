package calculation.controllers;

import calculation.bean.CalculateDto;
import calculation.bean.CalculationGetDto;
import calculation.services.CalculationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This controller is used to handle request regarding the
 * mathematics calculation and return and result.
 */

@RestController
@RequestMapping("/calculation")
@RequiredArgsConstructor
public class CalculationController {

    private final CalculationService calculationServices;

    /**
     * This method is used to do the calculation based on the input
     * pramaters(operand1, operand2, operation. operation such as "+"
     * "-" "*" "/" etc) and return the response result.
     *
     * @param dto the input parameters including operand1, operand2, operation.
     * @return the response entity contains the calculation result.
     */
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CalculationGetDto> calculate(@RequestBody CalculateDto dto) {
        return ResponseEntity.ok(calculationServices.getCalculationResult(dto));
    }

}
