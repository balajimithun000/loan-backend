package loans.com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoanDto {
    private Long loanId;
    private String loanType;
    private double loanAmount;
    private int tenureMonth;
    private double income;
    private int creditScore;
    private String status;
    private double interestRate;
}
