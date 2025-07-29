
package br.com.pay.wallet.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateUtilTest {

    @Test
    public void testUTCDate() throws Exception {
        assertEquals(2025728, DateUtil.toNumeric(LocalDate.now()));
    }
}
