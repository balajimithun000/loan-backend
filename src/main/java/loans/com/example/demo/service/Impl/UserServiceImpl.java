package loans.com.example.demo.service;

import loans.com.example.demo.dto.AdminRegisterDto;
import loans.com.example.demo.dto.LoginDto;
import loans.com.example.demo.dto.UserDto;
import loans.com.example.demo.entity.Role;
import loans.com.example.demo.entity.UserEntity;
import loans.com.example.demo.repository.UserRepository;
import loans.com.example.demo.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserEntity register(UserDto dto) {
        if(userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        UserEntity user = new UserEntity();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setAge(dto.getAge());
        user.setSalary(dto.getSalary());
        user.setRole(Role.USER);

        return userRepository.save(user);
    }

    public UserEntity registerAdmin(AdminRegisterDto dto) {
        if(userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        UserEntity admin = new UserEntity();
        admin.setName(dto.getFullName());
        admin.setEmail(dto.getEmail());
        admin.setPassword(passwordEncoder.encode(dto.getPassword()));
        admin.setRole(Role.ADMIN); // force admin role

        return userRepository.save(admin);
    }

    @Override
    public String login(LoginDto dto) {
        Optional<UserEntity> userOpt = userRepository.findByEmail(dto.getEmail());
        if(userOpt.isEmpty()) {
            throw new RuntimeException("Invalid email or password");
        }

        UserEntity user = userOpt.get();
        if(!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        return jwtUtil.generateToken(user.getEmail(), user.getRole());
    }

    @Override
    public UserEntity getProfile(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
