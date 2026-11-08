package kinkate.dev.testtask.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Time;

@Data
@AllArgsConstructor
public class ArrivalTimeDto {

    @NotBlank
    private String checkIn;

    private String checkOut;

}
