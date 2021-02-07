package com.altruist.trade;

import lombok.Data;

import java.util.UUID;

@Data
public class Trade {
    public UUID trade_uuid;
    public String symbol;
    public String side;
    public int quantity;
    public double price;
    public String status;
    public UUID account_uuid;
}
