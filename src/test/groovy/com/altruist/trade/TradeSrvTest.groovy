package com.altruist.trade

import org.junit.jupiter.api.extension.ExtendWith
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import spock.lang.Specification
import spock.lang.Unroll

import javax.validation.ConstraintViolationException

@ExtendWith(SpringExtension.class)
@SpringBootTest
class TradeSrvTest extends Specification {

    @SpringBean
    TradeRepo mockTradeRepo = Mock()

    @Autowired
    TradeSrv srv

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
        thrown(ConstraintViolationException)

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
