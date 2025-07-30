package br.com.pay.wallet.controller;

import br.com.pay.wallet.dto.AuthDTO;
import br.com.pay.wallet.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthDTO authDTO) {
        try {
            return ResponseEntity.ok().body(authService.authenticate(authDTO.document, authDTO.password));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body("Invalid username or password.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Authentication failed.");
        }
    }
}
