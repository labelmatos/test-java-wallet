
package br.com.pay.wallet.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ValidUtilTest {

    @Test
    public void testValidCPF() {
        assertTrue(ValidUtil.isValidCPF("35562363895"));
        assertFalse(ValidUtil.isValidCPF("1234567890"));
    }

    @Test
    public void testValidPhone() {
        assertTrue(ValidUtil.isValidPhone("11993712235"));
        assertFalse(ValidUtil.isValidPhone("000"));
    }
}
