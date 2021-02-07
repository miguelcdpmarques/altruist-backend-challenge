package com.altruist.trade;

import com.altruist.IdDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/trades")
@Slf4j
public class TradeCtrl {
    private final TradeSrv tradeSrv;

    public TradeCtrl(TradeSrv tradeSrv) {
        this.tradeSrv = tradeSrv;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<IdDto> create(
            @RequestBody @Valid TradeDto tradeDto,
            HttpServletRequest httpServletRequest
    ) throws URISyntaxException {
        log.info("Received Trade creation request [{}].", tradeDto);
        UUID tradeId = tradeSrv.create(tradeDto);
        return ResponseEntity.created(new URI(httpServletRequest.getRequestURL() + "/" + tradeId.toString()))
                .body(new IdDto(tradeId));
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<TradeDto> read() {
        log.info("Received Trade read request");
        return tradeSrv.read();
    }
}
