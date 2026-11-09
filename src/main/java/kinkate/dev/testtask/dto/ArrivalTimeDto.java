package kinkate.dev.testtask.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ArrivalTimeDto {

    @NotBlank
    private String checkIn;

    private String checkOut;

}
