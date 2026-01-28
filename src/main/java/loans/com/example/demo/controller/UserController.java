package loans.com.example.demo.controller;


import jakarta.validation.Valid;
import loans.com.example.demo.dto.AdminRegisterDto;
import loans.com.example.demo.dto.LoginDto;
import loans.com.example.demo.dto.UserDto;


import loans.com.example.demo.entity.Role;
import loans.com.example.demo.entity.UserEntity;
import loans.com.example.demo.security.JwtUtil;
import loans.com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<UserEntity> register(@RequestBody @Valid UserDto dto) {
        return ResponseEntity.ok(userService.register(dto));
    }

    @PostMapping("/admin/register")
    public ResponseEntity<UserEntity> registerAdmin(@RequestBody @Valid AdminRegisterDto dto) {
        return ResponseEntity.ok(userService.registerAdmin(dto));
    }



    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto dto) {
        String token = userService.login(dto);
        return ResponseEntity.ok(token);
    }



    @GetMapping("/profile")
    public ResponseEntity<UserEntity> getProfile(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);
        return ResponseEntity.ok(userService.getProfile(username));
    }
}
