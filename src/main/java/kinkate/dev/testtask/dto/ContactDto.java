package kinkate.dev.testtask.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContactDto {

    @NotBlank
    private String phone;
    @Email()
    private String email;
}
