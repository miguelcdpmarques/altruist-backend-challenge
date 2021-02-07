package com.altruist.address;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class AddressDto {

    @NotBlank
    public String name;

    @NotBlank
    public String street;

    @NotBlank
    public String city;

    @NotBlank
    public String state;

    @NotNull
    @Min(0)
    public Integer zipcode;
}
