package br.com.pay.wallet.controller;

import br.com.pay.wallet.service.BalanceService;
import br.com.pay.wallet.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/balance")
public class BalanceController {

    @Autowired
    private BalanceService balanceService;

    @GetMapping
    public ResponseEntity<?> getAllBalances(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.replace("Bearer ", "");
            if (JwtUtil.isTokenExpired(token)) {
                return ResponseEntity.status(401).body("Expired Token. Sign in again.");
            }
            String document = JwtUtil.extractSubject(token);
            return ResponseEntity.ok(balanceService.getAllWalletBalances(document));
        } catch (JwtException e) {
            return ResponseEntity.status(401).body("Expired Token. Sign in again.");
        }
    }

    @GetMapping("/{walletId}")
    public ResponseEntity<?> getBalance(@RequestHeader("Authorization") String authHeader,
                                        @PathVariable String walletId) {
        try {
            String token = authHeader.replace("Bearer ", "");
            if (JwtUtil.isTokenExpired(token)) {
                return ResponseEntity.status(401).body("Expired Token. Sign in again.");
            }
            String document = JwtUtil.extractSubject(token);
            return ResponseEntity.ok(balanceService.getWalletBalance(walletId, document));
        } catch (JwtException e) {
            return ResponseEntity.status(401).body("Expired Token. Sign in again.");
        }
    }

    @GetMapping("/{walletId}/last")
    public ResponseEntity<?> getLastOperations(@RequestHeader("Authorization") String authHeader,
                                               @PathVariable String walletId) {
        try {
            String token = authHeader.replace("Bearer ", "");
            if (JwtUtil.isTokenExpired(token)) {
                return ResponseEntity.status(401).body("Expired Token. Sign in again.");
            }
            String document = JwtUtil.extractSubject(token);
            return ResponseEntity.ok(balanceService.getLastOperations(walletId, document));
        } catch (JwtException e) {
            return ResponseEntity.status(401).body("Expired Token. Sign in again.");
        }
    }

    @GetMapping("/{walletId}/extract")
    public ResponseEntity<?> getStatementFromDates(@RequestHeader("Authorization") String authHeader,
                                                   @PathVariable String walletId,
                                                   @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                   @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        try {
            String token = authHeader.replace("Bearer ", "");
            if (JwtUtil.isTokenExpired(token)) {
                return ResponseEntity.status(401).body("Expired Token. Sign in again.");
            }
            String document = JwtUtil.extractSubject(token);
            return ResponseEntity.ok(balanceService.getStatementByDate(walletId, document, startDate, endDate));
        } catch (JwtException e) {
            return ResponseEntity.status(401).body("Expired Token. Sign in again.");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Invalid date range.");
        }
    }
}
