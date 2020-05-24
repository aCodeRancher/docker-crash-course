package com.in28minutes.microservices.currencyexchangeservice.resource;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
@WebMvcTest(CurrencyExchangeController.class)
@AutoConfigureMockMvc
@AutoConfigureDataJpa
class CurrencyExchangeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void retrieveTest() throws Exception {
        MvcResult result = mockMvc.perform(get("/currency-exchange/from/USD/to/INR"))
                .andReturn();
        String exchangeValue_inJson = result.getResponse().getContentAsString();
        assertTrue(exchangeValue_inJson!=null);
        ExchangeValue exchangeValue = new Gson().fromJson(exchangeValue_inJson, ExchangeValue.class);
        assertTrue(exchangeValue.getId()==10001);
        assertTrue(exchangeValue.getFrom().equals("USD"));
        assertTrue(exchangeValue.getTo().equals("INR"));
        assertTrue(exchangeValue.getConversionMultiple().doubleValue()==  65.00);
 }

}