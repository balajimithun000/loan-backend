package loans.com.example.demo.dto;

import loans.com.example.demo.entity.LoanEntity;
import loans.com.example.demo.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UserLoansDto {
    private UserEntity user;
    private List<LoanEntity> pendingLoans;
}
