package br.com.pay.wallet.controller;

import br.com.pay.wallet.dto.TransferDTO;
import br.com.pay.wallet.service.TransferService;
import br.com.pay.wallet.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transfer")
public class TransferController {

    @Autowired
    private TransferService transferService;

    @PutMapping("/{walletId}")
    public ResponseEntity<?> transfer(@RequestHeader("Authorization") String authHeader,
                                      @PathVariable String walletId,
                                      @RequestBody TransferDTO dto) {
        try {
            String token = authHeader.replace("Bearer ", "");
            if (JwtUtil.isTokenExpired(token)) {
                return ResponseEntity.status(401).body("Expired Token. Sign in again.");
            }
            String document = JwtUtil.extractSubject(token);
            transferService.transfer(walletId, dto, document);
            return ResponseEntity.ok("Transfer success.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(412).body(e.getMessage());
        } catch (JwtException e) {
            return ResponseEntity.status(401).body("Expired Token. Sign in again.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error to transfer.");
        }
    }
}
