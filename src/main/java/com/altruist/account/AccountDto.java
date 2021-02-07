package com.altruist.account;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class AccountDto {

    @NotBlank
    public String username;

    @NotBlank
    public String email;

    @NotBlank
    public String address_uuid;
}
