package br.com.pay.wallet.service;

import br.com.pay.wallet.model.Auth;
import br.com.pay.wallet.model.Client;
import br.com.pay.wallet.repository.AuthRepository;
import br.com.pay.wallet.repository.ClientsRepository;
import br.com.pay.wallet.util.HashUtil;
import br.com.pay.wallet.util.JwtUtil;
import com.mongodb.client.MongoClient;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Service
public class AuthService {

    @Autowired
    private MongoClient mongoClient;
    private ClientsRepository clientsRepository;
    private AuthRepository authRepository;

    public String authenticate(String document, String password) throws Exception {
        final Client client = clientsRepository.logIn(document, HashUtil.hash(password));
        if (client != null) {
            final String token = JwtUtil.generateToken(client);

            authRepository.create(Auth.build()
                    .setStatus("SUCCESS")
                    .setDocument(document)
                    .setCreatedAt(Date.from(Instant.now())));
            return token;
        } else {
            authRepository.create(Auth.build()
                    .setStatus("FAILURE")
                    .setDocument(document)
                    .setCreatedAt(Date.from(Instant.now())));
            throw new IllegalArgumentException("Invalid credentials");
        }
    }
}
