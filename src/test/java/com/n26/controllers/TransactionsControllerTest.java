package com.n26.controllers;

import com.n26.Application;
import com.n26.io.Transaction;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class TransactionsControllerTest extends BaseControllerTest {
    private final static String URL = "/transactions";

    @Before
    public void setUp() {
        store.clear();
    }

    @Test
    public void deleteTest() throws Exception {
        buildDataSet();
        restMvc.perform(delete(URL))
                .andExpect(status().is(NO_CONTENT));
    }

    @Test
    public void deleteEmptyStateTest() throws Exception {
        restMvc.perform(delete(URL))
                .andExpect(status().is(NO_CONTENT));
    }

    @Test
    public void create_ValidDataTest() throws Exception {
        post_Assert(mapper.writeValueAsString(new Transaction(BigDecimal.valueOf(100.78), Instant.now())), CREATED);
    }

    @Test
    public void createValidation_InvalidJson() throws Exception {
        post_Assert("This should be bad request", BAD_REQUEST);
    }

    @Test
    public void createValidation_UnparsableFields() throws Exception {
        post_Assert("{\"timestamp\": \"2020-01-1T20:20:51.304Z\", \"amount\":\"WRONG\",\"other\":6}",
                UNPROCESSABLE_ENTITY);
    }

    @Test
    public void createValidation_InvalidAmount() throws Exception {
        post_Assert("{\"timestamp\": \"2018-11-07T19:32:51.312Z\", \"amount\":\"Hundred\"}", UNPROCESSABLE_ENTITY);
        post_Assert("{\"timestamp\": \"2018-11-07T19:32:51.312Z\", \"amount\":\"\"}", UNPROCESSABLE_ENTITY);
    }

    @Test
    public void testCreateTransaction_InvalidTimestamp() throws Exception {
        post_Assert("{\"timestamp\": \"2020-20-1T20:20:51.304Z\", \"amount\":\"345.001\"}", UNPROCESSABLE_ENTITY);
        post_Assert("{\"timestamp\": \"2020-01-1T20:20:51.304\", \"amount\":\"345.001\"}", UNPROCESSABLE_ENTITY);
        post_Assert("{\"timestamp\": \"2020-01-1 20:20:51.304Z\", \"amount\":\"345.001\"}", UNPROCESSABLE_ENTITY);

        post_Assert("{\"timestamp\": \"171347597351\", \"amount\":\"643.832\"}", UNPROCESSABLE_ENTITY);
        post_Assert("{\"timestamp\": \"2020PM\", \"amount\":\"99.02\"}", UNPROCESSABLE_ENTITY);
        post_Assert("{\"timestamp\": \"\", \"amount\":\"12.12\"}", UNPROCESSABLE_ENTITY);
    }

    @Test
    public void createValidation_passedTime() throws Exception {
        post_Assert(mapper.writeValueAsString(new Transaction(BigDecimal.valueOf(100.78), Instant.now().minusMillis(60100))),
                NO_CONTENT);
    }

    @Test
    public void createValidation_AdvancedTime() throws Exception {
        post_Assert(mapper.writeValueAsString(new Transaction(BigDecimal.valueOf(100.78), Instant.now().plusMillis(100))),
                UNPROCESSABLE_ENTITY);
    }

    protected void post_Assert(String content, int expectedStatusCode) throws Exception {
        restMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
                .andExpect(status().is(expectedStatusCode));
    }

    private void buildDataSet() {
        store.addAll(Arrays.asList(
                new Transaction(BigDecimal.valueOf(500), Instant.now()),
                new Transaction(BigDecimal.valueOf(1500), Instant.now()),
                new Transaction(BigDecimal.valueOf(200), Instant.now()),
                new Transaction(BigDecimal.valueOf(300), Instant.now()),
                new Transaction(BigDecimal.valueOf(300), Instant.now().minusMillis(60020L))));
    }


}