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
    def "Should validate for missing field #field"() {
        given: "a trade with a missing field"
        TradeDto trade = new TradeDto(
                symbol: "V",
                quantity: 10,
                price: 2,
                side: "BUY",
                status: "SUBMITTED"
        )
        trade[field] = null

        when:
        srv.create(trade)

        then:
        thrown(ConstraintViolationException)

        where:
        field << ["symbol", "quantity", "price", "side", "status", "account_uuid"]
    }

    @Unroll
    def "Should validate for blank field #field"() {
        given: "a trade with a blank field"
        TradeDto trade = new TradeDto(
                symbol: "GOOG",
                quantity: 2900,
                price: 139.2,
                side: "BUY",
                status: "SUBMITTED"
        )
        trade[field] = ""

        when:
        srv.create(trade)

        then:
        thrown(ConstraintViolationException)

        where:
        field << ["symbol", "side", "status"]
    }

    @Unroll
    def "Should not throw exceptions if the trade is valid"() {
        given: "a valid trade object"
        TradeDto trade = new TradeDto(
                symbol: "WMT",
                quantity: 1,
                price: 1,
                side: "SELL",
                status: "SUBMITTED",
                account_uuid: UUID.randomUUID()
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