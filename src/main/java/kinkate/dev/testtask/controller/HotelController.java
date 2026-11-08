package kinkate.dev.testtask.controller;

import jakarta.validation.Valid;
import kinkate.dev.testtask.dto.CreateHotelDto;
import kinkate.dev.testtask.dto.HotelFullDto;
import kinkate.dev.testtask.dto.HotelShortDto;
import kinkate.dev.testtask.service.HotelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class HotelController {

    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }


    @GetMapping("hotels")
    public ResponseEntity<List<HotelShortDto>> getAllHotels() {
        return ResponseEntity.ok(hotelService.getAllHotels());
    }

    @GetMapping("hotels/{id}")
    public ResponseEntity<HotelFullDto> getHotelById(@PathVariable Long id) {
        return ResponseEntity.ok(hotelService.getHotelById(id));
    }

    @GetMapping("hotels/search")
    public ResponseEntity<List<HotelShortDto>> searchHotels(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String amenities
            ) {
        log.info("GET /hotels/search  name: {}, brand: {}, city: {}, country: {}, amenities: {}", name, brand, city, country, amenities);
        List<HotelShortDto> hotelShortDtoList = hotelService.searchHotels(name, brand, city, country, amenities);
        log.info("GET /hotels/search  получено {} отелей", hotelShortDtoList.size());
        return ResponseEntity.ok(hotelShortDtoList);
    }

    @PostMapping("hotels")
    public ResponseEntity<HotelShortDto> createHotel(@RequestBody @Valid CreateHotelDto createHotelDto) {
        log.info("POST /hotels  createHotelDto");
        HotelShortDto hotelShortDto = hotelService.createHotel(createHotelDto);
        log.info("POST /hotels  создан отель с id {}", hotelShortDto.getId());
        return ResponseEntity.status(201).body(hotelService.createHotel(createHotelDto));
    }

}
