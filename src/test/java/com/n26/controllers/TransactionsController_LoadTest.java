package com.n26.controllers;

import com.n26.Application;
import com.n26.io.Transaction;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.math.BigDecimal;
import java.time.Instant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class TransactionsController_LoadTest extends BaseControllerTest {
    private final static String URL = "/transactions";

    @Test
    public void createTransaction_ValidData() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100_000; i++) {
            try {

                String content = mapper.writeValueAsString(new Transaction(BigDecimal.valueOf(100 + i), Instant.now()));
                restMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(content))
                        .andExpect(status().is(CREATED));

                if (i % 10_000 == 0){
                    System.out.println("Current: " + i);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        long end = System.currentTimeMillis();
        System.out.format("Elapsed: %,d", (end - start));
    }
}
