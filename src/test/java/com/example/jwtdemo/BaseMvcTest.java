package com.example.jwtdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc(secure = false)
public abstract class BaseMvcTest extends BaseTest {

    @Autowired
    protected MockMvc mockMvc;
}
