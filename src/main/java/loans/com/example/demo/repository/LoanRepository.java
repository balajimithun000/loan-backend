package loans.com.example.demo.repository;

import loans.com.example.demo.entity.LoanEntity;
import loans.com.example.demo.entity.LoanStatus;
import loans.com.example.demo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface LoanRepository extends JpaRepository<LoanEntity, Long> {
    List<LoanEntity> findByUser_Id(Long userId);


    List<LoanEntity>findByUser_IdAndStatusIn(Long userId, List<LoanStatus>statuses);
    List<LoanEntity>findByUser(UserEntity user);
}
