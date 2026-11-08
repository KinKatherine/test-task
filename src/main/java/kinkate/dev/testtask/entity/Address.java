package kinkate.dev.testtask.entity;


import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Address {

    private Integer houseNumber;
    private String street;
    private String city;
    private String country;
    private String postCode;

}
