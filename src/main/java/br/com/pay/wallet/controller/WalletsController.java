package br.com.pay.wallet.controller;

import br.com.pay.wallet.dto.WalletDTO;
import br.com.pay.wallet.service.WalletsService;
import br.com.pay.wallet.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallets")
public class WalletsController {

    @Autowired
    private WalletsService walletsService;

    @PostMapping
    public ResponseEntity<?> createWallet(@RequestHeader("Authorization") String authHeader,
                                          @RequestBody WalletDTO walletDTO) {
        try {
            final String token = authHeader.replace("Bearer ", "");
            if (JwtUtil.isTokenExpired(token)) {
                return ResponseEntity.status(401).body("Expired Token. Sign in again.");
            }
            walletsService.createWallet(walletDTO, JwtUtil.extractSubject(token));
            return ResponseEntity.ok("Wallet created.");
        } catch (JwtException e) {
            return ResponseEntity.status(401).body("Expired Token. Sign in again.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error to create wallets.");
        }
    }

    @GetMapping
    public ResponseEntity<?> listWallets(@RequestHeader("Authorization") String authHeader) {
        try {
            final String token = authHeader.replace("Bearer ", "");
            if (JwtUtil.isTokenExpired(token)) {
                return ResponseEntity.status(401).body("Expired Token. Sign in again.");
            }
            return ResponseEntity.ok(walletsService.listWallets(JwtUtil.extractSubject(token)));
        } catch (JwtException e) {
            return ResponseEntity.status(401).body("Expired Token. Sign in again.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error to list wallets.");
        }
    }

    @GetMapping("/{walletId}")
    public ResponseEntity<?> getWallet(@RequestHeader("Authorization") String authHeader,
                                          @PathVariable String walletId) {
        try {
            final String token = authHeader.replace("Bearer ", "");
            if (JwtUtil.isTokenExpired(token)) {
                return ResponseEntity.status(401).body("Expired Token. Sign in again.");
            }
            return ResponseEntity.ok(walletsService.findWallet(walletId, JwtUtil.extractSubject(token)));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(412).body(e.getMessage());
        } catch (JwtException e) {
            return ResponseEntity.status(401).body("Expired Token. Sign in again.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error to find wallet.");
        }
    }

    @DeleteMapping("/{walletId}")
    public ResponseEntity<?> deleteWallet(@RequestHeader("Authorization") String authHeader,
                                          @PathVariable String walletId) {
        try {
            final String token = authHeader.replace("Bearer ", "");
            if (JwtUtil.isTokenExpired(token)) {
                return ResponseEntity.status(401).body("Expired Token. Sign in again.");
            }
            walletsService.deleteWallet(walletId, JwtUtil.extractSubject(token));
            return ResponseEntity.ok("Wallet deleted with success.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(412).body(e.getMessage());
        } catch (JwtException e) {
            return ResponseEntity.status(401).body("Expired Token. Sign in again.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error to delete wallet.");
        }
    }
}