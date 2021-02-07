package com.altruist.account;

import lombok.Data;

import java.util.UUID;

@Data
public class Account {
    public UUID account_uuid;
    public UUID address_uuid;
    public String username;
    public String email;
    public String name;
    public String street;
    public String city;
    public String state;
    public Integer zipcode;
}
