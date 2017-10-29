package calculation.controllers;

import calculation.bean.CalculateDto;
import calculation.bean.CalculationGetDto;
import calculation.exception.CalculationException;
import calculation.services.CalculationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CalculationController.class)
public class CalculationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CalculationService calculationService;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testCalculateWithValidDto() throws Exception {

        CalculateDto calculateDto = new CalculateDto()
                .setOperand1(9.0)
                .setOperand2(2.0);

        CalculationGetDto calculationGetDto = new CalculationGetDto().setResult(2.4);

        BDDMockito.given(calculationService.getCalculationResult(calculateDto)).willReturn(calculationGetDto);

        mockMvc.perform(post("/calculation")
                .content(mapper.writeValueAsString(calculateDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    public void testCalculateWithInvalidDtoWithEmptyOperation() throws Exception {
        CalculateDto calculateDto = new CalculateDto()
                .setOperand1(9.0)
                .setOperand2(2.0);
        ObjectMapper mapper = new ObjectMapper();

        BDDMockito.given(calculationService.getCalculationResult(calculateDto)).willThrow(
                new CalculationException("The operation cannot be empty")
        );

        mockMvc.perform(post("/calculation")
                .content(mapper.writeValueAsString(calculateDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isInternalServerError());
    }



}
