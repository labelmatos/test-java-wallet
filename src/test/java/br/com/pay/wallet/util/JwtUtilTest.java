
package br.com.pay.wallet.util;

import br.com.pay.wallet.model.Client;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JwtUtilTest {

    @Test
    public void testGenerateAndParseToken() {
        String document = "35562363895";
        String token = JwtUtil.generateToken(Client.newInstance().setDocument("35562363895"));
        assertNotNull(token);

        String extracted = JwtUtil.extractSubject(token);
        assertEquals(document, extracted);

        assertFalse(JwtUtil.isTokenExpired(token));
    }
}
