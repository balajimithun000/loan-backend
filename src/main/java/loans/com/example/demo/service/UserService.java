package loans.com.example.demo.service;

import loans.com.example.demo.dto.AdminRegisterDto;
import loans.com.example.demo.dto.LoginDto;
import loans.com.example.demo.dto.UserDto;
import loans.com.example.demo.entity.UserEntity;

public interface UserService {
   UserEntity register(UserDto dto);
   String login(LoginDto dto);
   UserEntity getProfile(String email);
   UserEntity registerAdmin(AdminRegisterDto dto);
}
