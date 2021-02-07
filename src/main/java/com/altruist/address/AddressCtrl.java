package com.altruist.address;

import com.altruist.IdDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/addresses")
@Slf4j
public class AddressCtrl {
    private final AddressSrv addressSrv;

    public AddressCtrl(final AddressSrv addressSrv) {
        this.addressSrv = addressSrv;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<IdDto> create(
            @RequestBody @Valid final AddressDto addressDto,
            final HttpServletRequest httpServletRequest
    ) throws URISyntaxException {
        log.info("Received Address creation request [{}].", addressDto);
        final UUID addressId = addressSrv.create(addressDto);
        return ResponseEntity.created(new URI(httpServletRequest.getRequestURL() + "/" + addressId.toString()))
                .body(new IdDto(addressId));
    }
}
