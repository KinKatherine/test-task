package kinkate.dev.testtask.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import kinkate.dev.testtask.dto.AddressDto;
import kinkate.dev.testtask.dto.ArrivalTimeDto;
import kinkate.dev.testtask.dto.ContactDto;
import kinkate.dev.testtask.dto.CreateHotelDto;
import kinkate.dev.testtask.dto.HotelFullDto;
import kinkate.dev.testtask.dto.HotelShortDto;
import kinkate.dev.testtask.entity.Address;
import kinkate.dev.testtask.entity.ArrivalTime;
import kinkate.dev.testtask.entity.Contact;
import kinkate.dev.testtask.entity.Hotel;
import kinkate.dev.testtask.repository.HotelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Transactional
    public HotelShortDto createHotel(CreateHotelDto dto) {
        log.info("Запрос на создание отеля");
        Hotel hotel = new Hotel();
        hotel.setName(dto.getName());
        hotel.setDescription(dto.getDescription());
        hotel.setBrand(dto.getBrand());

        Address address = new Address();
        address.setHouseNumber(dto.getAddress().getHouseNumber());
        address.setStreet(dto.getAddress().getStreet());
        address.setCity(dto.getAddress().getCity());
        address.setCountry(dto.getAddress().getCountry());
        address.setPostCode(dto.getAddress().getPostCode());
        hotel.setAddress(address);

        Contact contact = new Contact();
        contact.setPhone(dto.getContacts().getPhone());
        contact.setEmail(dto.getContacts().getEmail());
        hotel.setContact(contact);

        ArrivalTime arrivalTime = new ArrivalTime();
        arrivalTime.setCheckIn(dto.getArrivalTime().getCheckIn());
        arrivalTime.setCheckOut(dto.getArrivalTime().getCheckOut());
        hotel.setArrivalTime(arrivalTime);

        Hotel savedHotel = hotelRepository.save(hotel);
        log.info("Отель с id {} успешно создан", savedHotel.getId());
        return new HotelShortDto(
                savedHotel.getId(),
                savedHotel.getName(),
                savedHotel.getDescription(),
                savedHotel.getAddress().getHouseNumber() + " " + savedHotel.getAddress().getStreet() + ", " +
                        savedHotel.getAddress().getCity() + ", " +
                        savedHotel.getAddress().getPostCode() + ", " +
                        savedHotel.getAddress().getCountry(),
                savedHotel.getContact().getPhone()
        );
    }

    @Transactional
    public void addAmenities(Long id, List<String> amenities) {
        log.info("Запрос на добавление удобств к отелю с id {}", id);
        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Отель не найден"));
        if (hotel.getAmenities() == null) {
            log.info("У отеля с id {} нет удобств, создаем новый список", id);
            hotel.setAmenities(new ArrayList<>());
        }
        hotel.getAmenities().addAll(amenities);
        hotelRepository.save(hotel);
        log.info("Удобства успешно добавлены к отелю с id {}", id);
    }

    public Map<String, Long> getHistogram(String param) {
        log.info("Запрос на получение гистограммы по параметру: {}", param);
        List<Object[]> results;

        switch (param.toLowerCase()) {
            case "brand" -> results = hotelRepository.countByBrand();
            case "city" -> results = hotelRepository.countByCity();
            case "country" -> results = hotelRepository.countByCountry();
            case "amenities" -> results = hotelRepository.countByAmenities();
            default -> throw new IllegalArgumentException("Недопустимый параметр для гистограммы: " + param);
        }

        Map<String, Long> histogram = new HashMap<>();
        for (Object[] row : results) {
            if (row[0] != null) {
                histogram.put(String.valueOf(row[0]), ((Number) row[1]).longValue());
            }
        }

        log.info("Гистограмма по параметру {} успешно получена: {}", param, histogram);
        return histogram;
    }
}
