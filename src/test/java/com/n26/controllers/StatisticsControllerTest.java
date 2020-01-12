package com.n26.controllers;

import com.n26.Application;
import com.n26.io.Transaction;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class StatisticsControllerTest extends BaseControllerTest {

    private static final String URL = "/statistics";

    @Before
    public void setUp() {
        store.clear();
    }

    @Test
    public void getStatistics() throws Exception {
        buildDataSet();

        restMvc.perform(get(URL).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.sum").value("1651.00"))
                .andExpect(jsonPath("$.max").value("1000.00"))
                .andExpect(jsonPath("$.min").value("50.00"))
                .andExpect(jsonPath("$.avg").value("275.17"))
                .andExpect(jsonPath("$.count").value(6));
    }


    @Test
    public void getStatistics_EmptyData() throws Exception {
        restMvc.perform(get(URL).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.sum").value("0.00"))
                .andExpect(jsonPath("$.max").value("0.00"))
                .andExpect(jsonPath("$.min").value("0.00"))
                .andExpect(jsonPath("$.avg").value("0.00"))
                .andExpect(jsonPath("$.count").value(0));
    }

    private void buildDataSet() {
        store.addAll(Arrays.asList(
                new Transaction(BigDecimal.valueOf(100), Instant.now()),
                new Transaction(BigDecimal.valueOf(250), Instant.now()),
                new Transaction(BigDecimal.valueOf(200), Instant.now()),
                new Transaction(BigDecimal.valueOf(1000), Instant.now()),
                new Transaction(BigDecimal.valueOf(50), Instant.now()),
                new Transaction(BigDecimal.valueOf(51), Instant.now()),
                new Transaction(BigDecimal.valueOf(500), Instant.now().minusMillis(80000L)),
                new Transaction(BigDecimal.valueOf(600), Instant.now().minusMillis(60010L))
        ));
    }
}