package br.com.pay.wallet.controller;

import br.com.pay.wallet.dto.DepositDTO;
import br.com.pay.wallet.service.DepositService;
import br.com.pay.wallet.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/deposit")
public class DepositController {

    @Autowired
    private DepositService depositService;

    @PutMapping("/{walletId}")
    public ResponseEntity<?> deposit(@RequestHeader("Authorization") String authHeader,
                                     @PathVariable String walletId,
                                     @RequestBody DepositDTO dto) {
        try {
            String token = authHeader.replace("Bearer ", "");
            if (JwtUtil.isTokenExpired(token)) {
                return ResponseEntity.status(401).body("Expired Token. Sign in again.");
            }
            depositService.deposit(walletId, dto, JwtUtil.extractSubject(token));
            return ResponseEntity.ok("Deposit success.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(412).body(e.getMessage());
        } catch (JwtException e) {
            return ResponseEntity.status(401).body("Expired Token. Sign in again.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error to deposit.");
        }
    }
}
