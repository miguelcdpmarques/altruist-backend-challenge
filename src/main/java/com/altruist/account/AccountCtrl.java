package com.altruist.account;

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
@RequestMapping("/accounts")
@Slf4j
public class AccountCtrl {
    private final AccountSrv accountSrv;

    public AccountCtrl(final AccountSrv accountSrv) {
        this.accountSrv = accountSrv;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<IdDto> create(
            @RequestBody @Valid final AccountDto accountDto,
            final HttpServletRequest httpServletRequest
    ) throws URISyntaxException {
        log.info("Received Account creation request [{}].", accountDto);
        final UUID accountId = accountSrv.create(accountDto);
        return ResponseEntity.created(new URI(httpServletRequest.getRequestURL() + "/" + accountId.toString()))
                .body(new IdDto(accountId));
    }
}
