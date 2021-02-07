package com.altruist.trade;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import java.util.UUID;

@Data
@NoArgsConstructor
public class TradeDto {
  public String symbol;
  public String side;

  @Min(1)
  public Integer quantity;

  @DecimalMin("0.01")
  public double price;
  public String status;
  public UUID account_uuid;
}
