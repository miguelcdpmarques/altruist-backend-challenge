package com.altruist.trade

import com.altruist.account.Account
import com.altruist.account.AccountRepo
import com.altruist.config.DbConfig
import com.altruist.config.RepoConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType
import org.springframework.context.annotation.Import
import org.springframework.stereotype.Repository
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Stepwise

@ActiveProfiles("test")
@DataJdbcTest(includeFilters = [@ComponentScan.Filter(type = FilterType.ANNOTATION, value = [Repository])])
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(value = [DbConfig, RepoConfig])
@Stepwise
@Rollback(false)
class TradeRepoTest extends Specification {
    @Autowired
    TradeRepo tradeRepo

    @Autowired
    AccountRepo accountRepo

    @Shared
    Trade trade

    def "Inserts a trade"() {
        given: "a trade"
        Account account = new Account();
        account.username = "dummy_user"
        account.email = "dummy@useremail.com"

        accountRepo.save(account)

        trade = new Trade(
                symbol: "AAPL",
                quantity: 10,
                side: "BUY",
                price: 138.45,
                status: "SUBMITTED",
                account_uuid: account.account_uuid
        )

        when:
        tradeRepo.save(trade)

        then: "the trade id is returned"
        trade.trade_uuid
    }
}
