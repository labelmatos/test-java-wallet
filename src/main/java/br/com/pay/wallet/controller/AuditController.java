package br.com.pay.wallet.controller;

import br.com.pay.wallet.service.AuditService;
import br.com.pay.wallet.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/audit")
public class AuditController {

    @Autowired
    private AuditService auditService;

    @GetMapping("/{document}")
    public ResponseEntity<?> getAuditLogs(@RequestHeader("Authorization") String authHeader,
                                          @RequestParam(required = false) String startDate,
                                          @RequestParam(required = false) String endDate,
                                          @PathVariable String document) {
        try {
            String token = authHeader.replace("Bearer ", "");
            if (JwtUtil.isTokenExpired(token)) {
                return ResponseEntity.status(401).body("Expired Token. Sign in again.");
            }

            Claims claims = JwtUtil.extractAllClaims(token);
            if (!"admin".equals(claims.get("profile"))) {
                return ResponseEntity.status(403).body("Access denied.");
            }

            return ResponseEntity.ok(auditService.getLogsByDocument(document, startDate, endDate));
        } catch (JwtException e) {
            return ResponseEntity.status(401).body("Expired Token. Sign in again.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error to find audit.");
        }
    }
}
