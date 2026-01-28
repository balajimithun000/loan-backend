package loans.com.example.demo.service.Impl;


import loans.com.example.demo.entity.LoanEntity;
import loans.com.example.demo.entity.LoanStatus;
import loans.com.example.demo.repository.LoanRepository;
import loans.com.example.demo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private LoanRepository loanRepository;

    @Override
    public LoanEntity approveLoan(Long loanId, String remarks) {
        LoanEntity loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));
        loan.setStatus(LoanStatus.APPROVED);
        loan.setRemarks(remarks);
        return loanRepository.save(loan);
    }

    @Override
    public LoanEntity rejectLoan(Long loanId, String remarks) {
        LoanEntity loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));
        loan.setStatus(LoanStatus.REJECTED);
        loan.setRemarks(remarks);
        return loanRepository.save(loan);
    }
}
