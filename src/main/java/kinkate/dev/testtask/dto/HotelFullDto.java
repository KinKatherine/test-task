package kinkate.dev.testtask.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class HotelFullDto {

    private Long id;
    private String name;
    private String description;
    private String brand;
    private AddressDto address;       // Вложенный DTO
    private ContactDto contacts;      // Вложенный DTO
    private ArrivalTimeDto arrivalTime; // Вложенный DTO
    private List<String> amenities;
}
