package com.systemzarzadzaniaapteka.model;

import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

public class EPrescriptionTest {

    @Test
    void shouldValidateCode() {
        EPrescription e = new EPrescription();
        e.setCode("EP-1234");
        assertTrue(e.validateCode());
    }
}