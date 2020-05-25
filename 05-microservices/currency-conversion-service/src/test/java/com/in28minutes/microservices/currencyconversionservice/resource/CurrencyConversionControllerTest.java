package com.in28minutes.microservices.currencyconversionservice.resource;

import com.google.gson.Gson;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc

class CurrencyConversionControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    //Run Eureka Naming Service, CurrencyExchangeServiceApplication before running this test
    public void convertCurrencyTest() throws Exception {

            MvcResult result = mockMvc.perform(get("/currency-converter/from/USD/to/INR/quantity/100")).andReturn();
            String currencyConversionBean_json = result.getResponse().getContentAsString();
            assertTrue(currencyConversionBean_json != null);
            CurrencyConversionBean currencyConversionBean
                    = new Gson().fromJson(currencyConversionBean_json, CurrencyConversionBean.class);
            assertTrue(currencyConversionBean.getId() == 10001);
            assertTrue(currencyConversionBean.getFrom().equals("USD"));
            assertTrue(currencyConversionBean.getTo().equals("INR"));
            assertTrue(currencyConversionBean.getConversionMultiple().doubleValue() == 65.00);
            assertTrue(currencyConversionBean.getTotalCalculatedAmount().doubleValue() == 6500.00);

    }


}