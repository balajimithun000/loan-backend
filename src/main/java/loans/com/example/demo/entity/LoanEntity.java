package loans.com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="Loans")
@Getter
@Setter
public class LoanEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String loanType;
    private double loanAmount;
    private int tenureMonth;
    private double emi;
    private int creditScore;
    private double income;
    private String remarks;
    private double interestRate;
    @Enumerated(EnumType.STRING)
    private LoanStatus status=LoanStatus.APPLIED;


    @ManyToOne
    @JoinColumn(name="user_id")
    private UserEntity user;





}
