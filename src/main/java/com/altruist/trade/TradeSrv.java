package com.altruist.trade;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
        trade.account_uuid = tradeDto.account_uuid;
        return tradeRepo.save(trade)
                .trade_uuid;
    }

    public List<TradeDto> read() {
        return tradeRepo.fetchAll()
                .stream()
                .map(this::mapToTradeDto)
                .collect(Collectors.toList());
    }

    public void cancel(final String tradeId) {
        final Trade trade = tradeRepo.findById(UUID.fromString(tradeId));
        if (!"SUBMITTED".equalsIgnoreCase(trade.status)) {
            throw new IllegalStateException("The trade status must be SUBMITTED in order to cancel it");
        }

        trade.status = "CANCELLED";

        tradeRepo.updateStatus(trade);
    }

    private TradeDto mapToTradeDto(final Trade trade) {
        final TradeDto dto = new TradeDto();
        dto.symbol = trade.symbol;
        dto.quantity = trade.quantity;
        dto.price = trade.price;
        dto.side = trade.side;
        dto.status = trade.status;
        dto.account_uuid = trade.account_uuid;
        return dto;
    }
}
