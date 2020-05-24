package com.in28minutes.microservices.currencyconversionservice.resource;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(CurrencyConversionController.class)
@AutoConfigureDataJpa
@AutoConfigureMockMvc
//This auto configuration allows the test to configure CurrencyExchangeServiceProxy, which is a @FeignClient
@ImportAutoConfiguration(FeignAutoConfiguration.class)
class CurrencyConversionControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    //Run CurrencyExchangeServiceApplication before running this test
    public void convertCurrencyTest() throws Exception {
        MvcResult result = mockMvc.perform(get("/currency-converter/from/USD/to/INR/quantity/100")).andReturn();
        String currencyConversionBean_json = result.getResponse().getContentAsString();
        assertTrue(currencyConversionBean_json!=null);
        CurrencyConversionBean currencyConversionBean
                   = new Gson().fromJson(currencyConversionBean_json, CurrencyConversionBean.class);
        assertTrue(currencyConversionBean.getId()==10001);
        assertTrue(currencyConversionBean.getFrom().equals("USD"));
        assertTrue(currencyConversionBean.getTo().equals("INR"));
        assertTrue(currencyConversionBean.getConversionMultiple().doubleValue()==  65.00);
        assertTrue(currencyConversionBean.getTotalCalculatedAmount().doubleValue()== 6500.00);
    }
}