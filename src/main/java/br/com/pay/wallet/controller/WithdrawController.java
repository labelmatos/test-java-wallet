package br.com.pay.wallet.controller;

import br.com.pay.wallet.dto.WithdrawDTO;
import br.com.pay.wallet.service.WithdrawService;
import br.com.pay.wallet.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/withdraw")
public class WithdrawController {

    @Autowired
    private WithdrawService withdrawService;

    @PutMapping("/{walletId}")
    public ResponseEntity<?> withdraw(@RequestHeader("Authorization") String authHeader,
                                      @PathVariable String walletId,
                                      @RequestBody WithdrawDTO dto) {
        try {
            String token = authHeader.replace("Bearer ", "");
            if (JwtUtil.isTokenExpired(token)) {
                return ResponseEntity.status(401).body("Expired Token. Sign in again.");
            }
            String document = JwtUtil.extractSubject(token);
            withdrawService.withdraw(walletId, dto, document);
            return ResponseEntity.ok("Withdraw success.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(412).body(e.getMessage());
        } catch (JwtException e) {
            return ResponseEntity.status(401).body("Expired Token. Sign in again.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error to withdraw.");
        }
    }
}
