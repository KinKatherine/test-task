package kinkate.dev.testtask.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContactDto {
    private String phone;
    private String email;
}
