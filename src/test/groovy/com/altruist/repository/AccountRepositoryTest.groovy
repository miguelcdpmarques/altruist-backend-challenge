package com.altruist.repository

import com.altruist.config.DatabaseConfiguration
import com.altruist.config.RepositoryConfiguration
import com.altruist.model.Account
import groovy.sql.Sql
import org.junit.Before
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
@Import(value = [DatabaseConfiguration, RepositoryConfiguration])
@Stepwise
@Rollback(true)
class AccountRepositoryTest extends Specification {
    @Autowired
    AccountRepository repo

    @Autowired
    Sql sql

    @Shared
    Account account

    @Before
    def "Initializes the account"() {
        account = new Account(
                name: "Some Name",
                street: "Some street",
                city: "Some city",
                state: "CA",
                zipcode: 99999
        )
    }

    def "Inserts an address"() {
        given: "an address"

        when:
        repo.saveAddress(account)

        then: "the address id is returned"
        account.address_uuid
    }

    def "Inserts an account"() {
        given: "an account"
        account.username = "username123"
        account.email = "email@example.com"

        when:
        repo.save(account)

        then: "the account id is returned"
        account.account_uuid
    }
}
