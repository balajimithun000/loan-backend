package loans.com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String name;
    @Email
    private String email;
    private String phone;

    @NotBlank
    @Size(min=6,message = "password must be 6+ chars")
    private String password;

    private Integer age;

    private double salary;
}
