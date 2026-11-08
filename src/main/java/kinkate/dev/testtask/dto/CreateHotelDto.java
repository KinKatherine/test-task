package kinkate.dev.testtask.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateHotelDto {

    @NotBlank()
    private String name;

    private String description;

    @NotBlank()
    private String brand;

    @NotNull()
    @Valid
    private AddressDto address;

    @NotNull()
    @Valid
    private ContactDto contacts;

    @NotNull()
    @Valid
    private ArrivalTimeDto arrivalTime;
}