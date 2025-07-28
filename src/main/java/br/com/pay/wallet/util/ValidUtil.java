package br.com.pay.wallet.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

public class ValidUtil {

    public static boolean isValidCPF(String cpf) {
        return cpf != null && cpf.matches("^\\d{11}$");
    }

    public static boolean isValidPhone(String phone) {
        return phone != null && phone.matches("^\\d{10,11}$");
    }
}
