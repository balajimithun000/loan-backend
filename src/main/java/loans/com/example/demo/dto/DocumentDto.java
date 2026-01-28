package loans.com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class DocumentDto {
    private Long loanId;
    private Long userId;
    private String documentType;
    private MultipartFile file;
}
