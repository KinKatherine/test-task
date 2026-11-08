package kinkate.dev.testtask.service;

import jakarta.persistence.EntityNotFoundException;
import kinkate.dev.testtask.dto.AddressDto;
import kinkate.dev.testtask.dto.ArrivalTimeDto;
import kinkate.dev.testtask.dto.ContactDto;
import kinkate.dev.testtask.dto.HotelFullDto;
import kinkate.dev.testtask.dto.HotelShortDto;
import kinkate.dev.testtask.repository.HotelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class HotelService {

    private final HotelRepository hotelRepository;


    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public List<HotelShortDto> getAllHotels() {
        return hotelRepository.findAll().stream()
                .map(hotel -> new HotelShortDto(
                        hotel.getId(),
                        hotel.getName(),
                        hotel.getDescription(),
                        hotel.getAddress().getHouseNumber() + " " + hotel.getAddress().getStreet() + ", " +
                                hotel.getAddress().getCity() + ", " +
                                hotel.getAddress().getPostCode() + ", " +
                                hotel.getAddress().getCountry(),
                        hotel.getContact().getPhone()
                ))
                .toList();
    }

    public HotelFullDto getHotelById(Long id) {
        return hotelRepository.findById(id)
                .map(hotel -> new HotelFullDto(
                        hotel.getId(),
                        hotel.getName(),
                        hotel.getDescription(),
                        hotel.getBrand(),
                        new AddressDto(
                                hotel.getAddress().getHouseNumber(),
                                hotel.getAddress().getStreet(),
                                hotel.getAddress().getCity(),
                                hotel.getAddress().getCountry(),
                                hotel.getAddress().getPostCode()
                        ),
                        new ContactDto(
                                hotel.getContact().getPhone(),
                                hotel.getContact().getEmail()
                        ),
                        new ArrivalTimeDto(
                                hotel.getArrivalTime().getCheckIn(),
                                hotel.getArrivalTime().getCheckOut()
                        ),
                        hotel.getAmenities()
                ))
                .orElseThrow(() -> new EntityNotFoundException("Отель не найден"));

    }

    public List<HotelShortDto> searchHotels(String name, String brand, String city, String country, String amenities) {
        log.info("Запрос на получение списка отелей с фильтрацией");
        List<HotelShortDto> list =  hotelRepository.searchHotels(name, brand, city, country, amenities).stream()
                .map(hotel -> new HotelShortDto(
                        hotel.getId(),
                        hotel.getName(),
                        hotel.getDescription(),
                        hotel.getAddress().getHouseNumber() + " " + hotel.getAddress().getStreet() + ", " +
                                hotel.getAddress().getCity() + ", " +
                                hotel.getAddress().getPostCode() + ", " +
                                hotel.getAddress().getCountry(),
                        hotel.getContact().getPhone()
                ))
                .toList();
        log.info("Найдено {} отелей с фильтрацией", list.size());
        return list;
    }
}
