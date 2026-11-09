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
import java.util.Map;

@Slf4j
@RestController
public class HotelController {

    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }


    @GetMapping("hotels")
    public ResponseEntity<List<HotelShortDto>> getAllHotels() {
        log.info("GET /hotels  получение всех отелей");
        List<HotelShortDto> hotels = hotelService.getAllHotels();
        log.info("GET /hotels  получено {} отелей", hotels.size());
        return ResponseEntity.ok(hotels);
    }

    @GetMapping("hotels/{id}")
    public ResponseEntity<HotelFullDto> getHotelById(@PathVariable Long id) {
        log.info("GET /hotels/{}  получение отеля с id {}", id, id);
        HotelFullDto hotelFullDto = hotelService.getHotelById(id);
        log.info("GET /hotels/{}  получен отель с id {}", id, id);
        return ResponseEntity.ok(hotelFullDto);
    }

    @GetMapping("search")
    public ResponseEntity<List<HotelShortDto>> searchHotels(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String amenities
            ) {
        log.info("GET /search  name: {}, brand: {}, city: {}, country: {}, amenities: {}", name, brand, city, country, amenities);
        List<HotelShortDto> hotelShortDtoList = hotelService.searchHotels(name, brand, city, country, amenities);
        log.info("GET /search  получено {} отелей", hotelShortDtoList.size());
        return ResponseEntity.ok(hotelShortDtoList);
    }

    @PostMapping("hotels")
    public ResponseEntity<HotelShortDto> createHotel(@RequestBody @Valid CreateHotelDto createHotelDto) {
        log.info("POST /hotels");
        HotelShortDto hotelShortDto = hotelService.createHotel(createHotelDto);
        log.info("POST /hotels  создан отель с id {}", hotelShortDto.getId());
        return ResponseEntity.status(201).body(hotelShortDto);
    }

    @PostMapping("hotels/{id}/amenities")
    public ResponseEntity<Void> addAmenities(@PathVariable Long id, @RequestBody List<String> amenities) {
        log.info("POST hotels/{id}/amenities добавление удобств к отелю с id {}: ", id);
        hotelService.addAmenities(id, amenities);
        log.info("POST /hotels  createHotelDto удобства успешно добавлены к отелю с id {}", id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("histogram/{param}")
    public ResponseEntity<Map<String, Long>> getHistogram(@PathVariable String param) {
        log.info("GET /histogram/{}  получение гистограммы по параметру: {}", param, param);
        Map<String, Long> histogram = hotelService.getHistogram(param);
        log.info("GET /histogram/{}  получена гистограмма: {}", param, histogram);
        return ResponseEntity.ok(histogram);
    }

}
