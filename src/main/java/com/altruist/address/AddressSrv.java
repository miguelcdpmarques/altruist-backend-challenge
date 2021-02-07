package com.altruist.address;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.UUID;

@Service
@Validated
public class AddressSrv {
    private final AddressRepo addressRepo;

    public AddressSrv(final AddressRepo addressRepo) {
        this.addressRepo = addressRepo;
    }

    public UUID create(final @Valid AddressDto addressDto) {
        final Address address = new Address();
        address.name = addressDto.name;
        address.street = addressDto.street;
        address.city = addressDto.city;
        address.state = addressDto.state;
        address.zipcode = addressDto.zipcode;
        return addressRepo.save(address)
                .address_uuid;
    }
}
