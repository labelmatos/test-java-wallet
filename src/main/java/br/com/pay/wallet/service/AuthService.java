package br.com.pay.wallet.service;

import br.com.pay.wallet.dto.TokenDTO;
import br.com.pay.wallet.model.Auth;
import br.com.pay.wallet.model.Client;
import br.com.pay.wallet.repository.AuthRepository;
import br.com.pay.wallet.repository.ClientsRepository;
import br.com.pay.wallet.util.HashUtil;
import br.com.pay.wallet.util.JwtUtil;
import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class AuthService {

    @Autowired
    private MongoClient mongoClient;
    @Autowired
    private ClientsRepository clientsRepository;
    @Autowired
    private AuthRepository authRepository;

    public TokenDTO authenticate(String document, String password) throws Exception {
        final Client client = clientsRepository.findByIdAndPasswordAndDeletedFalse(document, HashUtil.hash(password));
        if (client != null) {
            final TokenDTO token = new TokenDTO(JwtUtil.generateToken(client));
            authRepository.insert(Auth.build()
                    .setStatus("SUCCESS")
                    .setDocument(document)
                    .setCreatedAt(Date.from(Instant.now())));
            return token;
        } else {
            authRepository.insert(Auth.build()
                    .setStatus("FAILURE")
                    .setDocument(document)
                    .setCreatedAt(Date.from(Instant.now())));
            throw new IllegalArgumentException("Invalid credentials");
        }
    }
}
