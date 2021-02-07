package com.altruist.address


import com.altruist.config.DbConfig
import com.altruist.config.RepoConfig
import groovy.sql.Sql
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
class AddressRepoTest extends Specification {
    @Autowired
    AddressRepo repo

    @Autowired
    Sql sql

    @Shared
    Address address

    def "Inserts an address"() {
        given: "an address"
        address = new Address(
                name: "Some Name",
                street: "Some street",
                city: "Some city",
                state: "CA",
                zipcode: 98989
        )

        when:
        repo.save(address)

        then: "the address id is returned"
        address.address_uuid
    }
}
