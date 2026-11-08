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
    private AddressDto address;
    private ContactDto contacts;
    private ArrivalTimeDto arrivalTime;
    private List<String> amenities;
}
