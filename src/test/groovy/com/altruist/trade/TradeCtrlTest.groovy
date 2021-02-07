package com.altruist.trade

import com.fasterxml.jackson.databind.ObjectMapper
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import spock.lang.Specification

import static org.hamcrest.Matchers.containsString
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(controllers = [TradeCtrl])
class TradeCtrlTest extends Specification {
    @Autowired
    MockMvc mvc

    @Autowired
    ObjectMapper objectMapper

    @SpringBean
    TradeSrv mockTradeSrv = Mock()

    def "Should accept trade requests"() {
        given: "an trade request"
        TradeDto req = new TradeDto(
                symbol: "TSLA",
                quantity: 2,
                price: 80,
                side: "BUY",
                status: "SUBMITTED",
                account_uuid: UUID.randomUUID()
        )
        UUID expectedId = UUID.randomUUID()

        when: "the request is submitted"
        ResultActions results = mvc.perform(post("/trades")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req))
        )

        then: "the request is processed"
        1 * mockTradeSrv.create(req) >> expectedId

        and: "a Created response is returned"
        results.andExpect(status().isCreated())

        and: "the order ID is returned"
        results.andExpect(header().exists("Location"))
                .andExpect(header().string("Location", containsString("/trades/$expectedId")))
        results.andExpect(content().json("""{"id":"$expectedId"}"""))
    }
}
