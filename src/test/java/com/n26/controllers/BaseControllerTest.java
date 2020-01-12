package com.n26.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.n26.stores.TransactionStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;


public abstract class BaseControllerTest {
    protected static final int CREATED = HttpStatus.CREATED.value();
    protected static final int NO_CONTENT = HttpStatus.NO_CONTENT.value();
    protected static final int BAD_REQUEST = HttpStatus.BAD_REQUEST.value();
    protected static final int UNPROCESSABLE_ENTITY = HttpStatus.UNPROCESSABLE_ENTITY.value();

    @Autowired
    protected TransactionStore store;

    @Autowired
    protected MockMvc restMvc;

    @Autowired
    protected ObjectMapper mapper;

}
