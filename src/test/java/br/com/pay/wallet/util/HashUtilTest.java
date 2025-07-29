
package br.com.pay.wallet.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class HashUtilTest {

    @Test
    public void testHashAndMatch() throws Exception {
        String password = "1q2w3e4r";
        String hashed = HashUtil.hash(password);
        assertNotEquals(password, hashed);
    }
}
