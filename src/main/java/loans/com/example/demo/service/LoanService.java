package loans.com.example.demo.service;

import loans.com.example.demo.dto.LoanDto;
import loans.com.example.demo.entity.LoanEntity;
import loans.com.example.demo.entity.UserEntity;

import java.util.List;
public interface LoanService {
    LoanEntity applyLoan(LoanDto dto, String username); // username from JWT
    List<LoanEntity> getLoansByUser(String username);
}
