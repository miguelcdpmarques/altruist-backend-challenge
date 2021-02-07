package com.altruist.account

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
class AccountSrvTest extends Specification {

    @SpringBean
    AccountRepo mockAccountRepo = Mock()

    @Autowired
    AccountSrv srv

    @Unroll
    def "Should validate for missing account field #field"() {
        given: "an account missing fields"
        AccountDto account = new AccountDto(
                username: "username123",
                email: "email@example.com",
                address_uuid: UUID.randomUUID()
        )
        account[field] = null

        when:
        srv.create(account)

        then:
        thrown(ConstraintViolationException)

        where:
        field << ["username", "email"]
    }

    def "Should save account"() {
        given: "an account"
        UUID expectedAddressId = UUID.randomUUID()
        AccountDto account = new AccountDto(
                username: "username123",
                email: "email@example.com",
                address_uuid: expectedAddressId
        )
        UUID expectedAccountId = UUID.randomUUID()

        when:
        srv.create(account)

        then: "the account is saved"
        1 * mockAccountRepo.save(_) >> { Account arg ->
            with(arg){
                username == account.username
                email == account.email
                address_uuid == expectedAddressId
            }

            arg.account_uuid = expectedAccountId
            arg
        }
    }
}
