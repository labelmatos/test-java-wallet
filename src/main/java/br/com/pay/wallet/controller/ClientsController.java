package br.com.pay.wallet.controller;

import br.com.pay.wallet.dto.ClientDTO;
import br.com.pay.wallet.repository.CurrencyTaxRepository;
import br.com.pay.wallet.service.ClientsService;
import br.com.pay.wallet.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.dao.DuplicateKeyException;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
public class ClientsController {

    @Autowired
    private ClientsService clientsService;
    @Autowired
    private CurrencyTaxRepository currencyTaxRepository;

    @PostMapping
    public ResponseEntity<?> register(@RequestBody ClientDTO clientDTO) {
        try {
            clientsService.register(clientDTO);
            return ResponseEntity.ok("Client created!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (JwtException e) {
            return ResponseEntity.status(401).body("Expired Token. Sign in again.");
        } catch(DuplicateKeyException dke) {
            return ResponseEntity.badRequest().body("Document already registered.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error to create client.");
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getProfile(@RequestHeader("Authorization") String authHeader) {
        try {
            final String token = authHeader.replace("Bearer ", "");
            if (JwtUtil.isTokenExpired(token)) {
                return ResponseEntity.status(401).body("Expired Token. Sign in again.");
            }
            String document = JwtUtil.extractSubject(token);
            return ResponseEntity.ok(clientsService.getClientProfile(document));
        } catch (JwtException e) {
            return ResponseEntity.status(401).body("Expired Token. Sign in again.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao obter perfil.");
        }
    }

    @PatchMapping
    public ResponseEntity<?> updatePartial(@RequestHeader("Authorization") String authHeader,
                                           @RequestBody ClientDTO toUpdate) {
        try {
            final String token = authHeader.replace("Bearer ", "");
            if (JwtUtil.isTokenExpired(token)) {
                return ResponseEntity.status(401).body("Expired Token. Sign in again.");
            }
            String document = JwtUtil.extractSubject(token);
            clientsService.updatePartial(document, toUpdate);
            return ResponseEntity.ok("Client data updated.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (JwtException e) {
            return ResponseEntity.status(401).body("Expired Token. Sign in again.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error on update.");
        }
    }

    @DeleteMapping("/me")
    public ResponseEntity<?> softDeleteAccount(@RequestHeader("Authorization") String authHeader) {
        try {
            final String token = authHeader.replace("Bearer ", "");
            if (JwtUtil.isTokenExpired(token)) {
                return ResponseEntity.status(401).body("Expired Token. Sign in again.");
            }
            String document = JwtUtil.extractSubject(token);
            clientsService.softDeleteAccount(document);
            return ResponseEntity.ok("Client deleted.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(412).body(e.getMessage());
        } catch (JwtException e) {
            return ResponseEntity.status(401).body("Expired Token. Sign in again.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error to delete client.");
        }
    }
}
