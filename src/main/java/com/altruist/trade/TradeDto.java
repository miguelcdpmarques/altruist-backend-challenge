package com.altruist.trade;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@NoArgsConstructor
public class TradeDto {

  @NotBlank
  public String symbol;

  @NotBlank
  public String side;

  @NotNull
  @Min(1)
  public Integer quantity;

  @NotNull
  @DecimalMin("0.01")
  public Double price;

  @NotBlank
  public String status;

  @NotNull
  public UUID account_uuid;
}
