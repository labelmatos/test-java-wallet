package br.com.pay.wallet.service;

import br.com.pay.wallet.model.Auth;
import br.com.pay.wallet.model.Client;
import br.com.pay.wallet.repository.AuthRepository;
import br.com.pay.wallet.repository.ClientsRepository;
import br.com.pay.wallet.util.HashUtil;
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

    private final SecretKey secretKey = Keys.hmacShaKeyFor("MySuperSecretKeyForJwtThatIsAtLeast32BytesLong".getBytes(StandardCharsets.UTF_8));

    public String authenticate(String document, String password) throws Exception {
        final Client client = clientsRepository.logIn(document, HashUtil.hashPassword(password));
        if (client != null) {
            final String token = Jwts.builder()
                    .setSubject(document)
                    .setIssuedAt(new Date())
                    .setExpiration(Date.from(Instant.now().plusSeconds(600))) // 10 minutes
                    .signWith(secretKey, SignatureAlgorithm.HS256)
                    .compact();

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
