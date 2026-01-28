package loans.com.example.demo.controller;

import loans.com.example.demo.dto.LoanDto;
import loans.com.example.demo.entity.LoanEntity;
import loans.com.example.demo.security.JwtUtil;
import loans.com.example.demo.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/apply")
    public ResponseEntity<LoanEntity> applyLoan(@RequestBody LoanDto dto,
                                                @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);
        LoanEntity loan = loanService.applyLoan(dto, username);
        return ResponseEntity.ok(loan);
    }

    @GetMapping
    public ResponseEntity<List<LoanEntity>> getLoans(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);
        List<LoanEntity> loans = loanService.getLoansByUser(username);
        return ResponseEntity.ok(loans);
    }
}
