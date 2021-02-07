package com.altruist.trade


import spock.lang.Specification
import spock.lang.Unroll

import java.security.InvalidParameterException

class TradeSrvTest extends Specification {
    TradeRepo mockTradeRepo = Mock()

    TradeSrv srv = new TradeSrv(mockTradeRepo)

    @Unroll
    def "Should validate for invalid field #field"() {
        given: "a trade with quantity or price equal to zero"
        TradeDto trade = new TradeDto(
                symbol: "WMT",
                quantity: 1,
                price: 1,
                side: "SELL",
                status: "SUBMITTED"
        )
        trade[field] = 0

        when:
        srv.create(trade)

        then:
        thrown(InvalidParameterException)

        where:
        field << ["quantity", "price"]
    }

    @Unroll
    def "Should not throw exceptions if the trade is valid"() {
        given: "a trade with quantity or price equal to zero"
        TradeDto trade = new TradeDto(
                symbol: "WMT",
                quantity: 1,
                price: 1,
                side: "SELL",
                status: "SUBMITTED"
        )
        UUID expectedTradeId = UUID.randomUUID()

        when:
        srv.create(trade)

        then:
        1 * mockTradeRepo.save(_) >> { Trade arg ->
            with(arg){
                symbol == trade.symbol
                side == trade.side
                price == trade.price
                quantity == trade.quantity
            }

            arg.trade_uuid = UUID.randomUUID()
            arg
        }
    }
}
