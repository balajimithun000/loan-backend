package loans.com.example.demo.controller;

import loans.com.example.demo.dto.UserLoansDto;
import loans.com.example.demo.entity.LoanEntity;
import loans.com.example.demo.entity.Role;
import loans.com.example.demo.entity.UserEntity;
import loans.com.example.demo.repository.LoanRepository;
import loans.com.example.demo.repository.UserRepository;
import loans.com.example.demo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoanRepository loanRepository;

    // ✅ APPROVE
    @PostMapping("/approve/{loanId}")
    public ResponseEntity<LoanEntity> approveLoan(
            @PathVariable Long loanId,
            @RequestParam String remarks) {

        return ResponseEntity.ok(
                adminService.approveLoan(loanId, remarks));
    }

    // ✅ REJECT
    @PostMapping("/reject/{loanId}")
    public ResponseEntity<LoanEntity> rejectLoan(
            @PathVariable Long loanId,
            @RequestParam String remarks) {

        return ResponseEntity.ok(
                adminService.rejectLoan(loanId, remarks));
    }

    // ✅ ALL USERS
    @GetMapping("/users")
    public ResponseEntity<List<UserEntity>> getAllUsers() {

        List<UserEntity> users =
                userRepository.findByRole(Role.USER);

        return ResponseEntity.ok(users);
    }

    // ✅ SINGLE USER
    @GetMapping("/users/{userId}")
    public ResponseEntity<UserEntity> getUser(
            @PathVariable Long userId) {

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if (user.getRole() != Role.USER) {
            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.ok(user);
    }

    // ✅ USER LOANS
    @GetMapping("/users/{userId}/loans")
    public ResponseEntity<List<LoanEntity>> getUserLoans(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                loanRepository.findByUser_Id(userId));
    }

    // ✅ ADMIN DASHBOARD (ALL LOANS)
    @GetMapping("/dashboard")
    public ResponseEntity<List<UserLoansDto>> getDashboard() {

        List<UserEntity> users =
                userRepository.findByRole(Role.USER);

        List<UserLoansDto> dashboard =
                users.stream().map(user -> {

                    List<LoanEntity> loans =
                            loanRepository.findByUser_Id(
                                    user.getId());

                    return new UserLoansDto(user, loans);

                }).collect(Collectors.toList());

        return ResponseEntity.ok(dashboard);
    }
}
