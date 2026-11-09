package kinkate.dev.testtask;

import com.fasterxml.jackson.databind.ObjectMapper;
import kinkate.dev.testtask.dto.AddressDto;
import kinkate.dev.testtask.dto.ArrivalTimeDto;
import kinkate.dev.testtask.dto.ContactDto;
import kinkate.dev.testtask.dto.CreateHotelDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class HotelIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateAndRetrieveHotel() throws Exception {
        CreateHotelDto createDto = new CreateHotelDto(
                "President Hotel",
                "Luxury hotel in Minsk",
                "Independent",
                new AddressDto(18, "Kirova Street", "Minsk", "Belarus", "220030"),
                new ContactDto("+375172297000", "info@president.by"),
                new ArrivalTimeDto("14:00", "12:00")
        );

        mockMvc.perform(post("/hotels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("President Hotel"));

        mockMvc.perform(get("/hotels"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("President Hotel"));

        mockMvc.perform(get("/search").param("city", "Minsk"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].address").value("18 Kirova Street, Minsk, 220030, Belarus"));

        mockMvc.perform(get("/histogram/city"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Minsk").value(1));
    }
}