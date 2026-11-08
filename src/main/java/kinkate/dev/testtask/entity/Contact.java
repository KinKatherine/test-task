package kinkate.dev.testtask.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;


@Embeddable
@Data
public class Contact {

    private String phone;
    private String email;

}
