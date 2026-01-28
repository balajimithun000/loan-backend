package loans.com.example.demo.service;

import loans.com.example.demo.entity.LoanEntity;

public interface AdminService {
    LoanEntity approveLoan(Long loanId, String remarks);
    LoanEntity rejectLoan(Long loanId, String remarks);
}
