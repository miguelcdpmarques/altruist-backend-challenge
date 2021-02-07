package com.altruist.address;

import lombok.Data;

import java.util.UUID;

@Data
public class Address {
  public UUID address_uuid;
  public String name;
  public String street;
  public String city;
  public String state;
  public Integer zipcode;
}
