package com.altruist.trade;

import org.springframework.stereotype.Component;

import java.security.InvalidParameterException;
import java.util.UUID;

@Component
public class TradeSrv {
    private final TradeRepo tradeRepo;

    public TradeSrv(final TradeRepo tradeRepo) {
        this.tradeRepo = tradeRepo;
    }

    public UUID create(final TradeDto tradeDto) {
        if (tradeDto.price == 0 || tradeDto.quantity == 0) {
            throw new InvalidParameterException();
        }

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
