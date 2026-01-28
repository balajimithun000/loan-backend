package loans.com.example.demo.controller;


import loans.com.example.demo.dto.DocumentDto;
import loans.com.example.demo.entity.DocumentEntity;
import loans.com.example.demo.security.JwtUtil;
import loans.com.example.demo.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/users/documents")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/upload")
    public ResponseEntity<DocumentEntity> uploadDocument(@RequestParam Long loanId,
                                                         @RequestParam String documentType,
                                                         @RequestParam MultipartFile file,
                                                         @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);

        DocumentDto dto = new DocumentDto();
        dto.setLoanId(loanId);
        dto.setDocumentType(documentType);
        dto.setFile(file);

        return ResponseEntity.ok(documentService.uploadDocument(dto, username));
    }
}

