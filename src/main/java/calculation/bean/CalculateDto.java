package calculation.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@Validated
public class CalculateDto {

    @NotBlank
    private Double operand1;

    @NotBlank
    private Double operand2;

    @NotBlank
    private String operation;
}
