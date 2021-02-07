package com.altruist.address


import org.junit.jupiter.api.extension.ExtendWith
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import spock.lang.Specification
import spock.lang.Stepwise
import spock.lang.Unroll

import javax.validation.ConstraintViolationException

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Stepwise
class AddressSrvTest extends Specification {

    @SpringBean
    AddressRepo mockAddressRepo = Mock()

    @Autowired
    AddressSrv srv

    @Unroll
    def "Should validate for missing address field #field"() {
        given: "an address missing fields"
        AddressDto address = new AddressDto(
                name: "Some Name",
                street: "Some street",
                city: "Some city",
                state: "CA",
                zipcode: 99999
        )
        address[field] = null

        when:
        srv.create(address)

        then:
        thrown(ConstraintViolationException)

        where:
        field << ["name", "street", "city", "state", "zipcode"]
    }
}
