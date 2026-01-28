package loans.com.example.demo.service.Impl;

import loans.com.example.demo.dto.LoanDto;
import loans.com.example.demo.entity.LoanEntity;
import loans.com.example.demo.entity.LoanStatus;
import loans.com.example.demo.entity.UserEntity;
import loans.com.example.demo.repository.LoanRepository;
import loans.com.example.demo.repository.UserRepository;
import loans.com.example.demo.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public LoanEntity applyLoan(LoanDto dto, String username) {
        UserEntity user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        LoanEntity loan = new LoanEntity();
        loan.setLoanType(dto.getLoanType());
        loan.setLoanAmount(dto.getLoanAmount());
        loan.setTenureMonth(dto.getTenureMonth());
        loan.setCreditScore(dto.getCreditScore());
        loan.setIncome(dto.getIncome());

        // ---------------------- CALCULATE INTEREST RATE ----------------------
        double baseRate = 10; // base interest 10%
        double creditAdjustment = (700 - dto.getCreditScore()) * 0.01; // reduce for good credit
        double incomeAdjustment = dto.getIncome() < 50000 ? 1.0 : 0; // high income discount

        double calculatedInterest = baseRate + creditAdjustment + incomeAdjustment;
        loan.setInterestRate(Math.round(calculatedInterest * 100.0) / 100.0); // round 2 decimals

        // ---------------------- EMI CALCULATION ----------------------
        double r = loan.getInterestRate() / 12 / 100;
        int n = dto.getTenureMonth();
        double P = dto.getLoanAmount();
        double emi = (P * r * Math.pow(1 + r, n)) / (Math.pow(1 + r, n) - 1);
        loan.setEmi(Math.round(emi * 100.0) / 100.0);

        loan.setStatus(LoanStatus.APPLIED);
        loan.setUser(user);

        return loanRepository.save(loan);
    }

    @Override
    public List<LoanEntity> getLoansByUser(String username) {
        UserEntity user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return loanRepository.findByUser(user);
    }
}
