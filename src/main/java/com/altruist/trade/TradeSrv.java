package com.altruist.trade;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.UUID;

@Validated
@Service
public class TradeSrv {
    private final TradeRepo tradeRepo;

    public TradeSrv(final TradeRepo tradeRepo) {
        this.tradeRepo = tradeRepo;
    }

    public UUID create(@Valid final TradeDto tradeDto) {
        final Trade trade = new Trade();
        trade.symbol = tradeDto.symbol;
        trade.side = tradeDto.side;
        trade.price = tradeDto.price;
        trade.quantity = tradeDto.quantity;
        trade.status = tradeDto.status;
        return tradeRepo.save(trade)
                .trade_uuid;
    }
}
