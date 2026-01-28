package loans.com.example.demo.service.Impl;

import loans.com.example.demo.dto.DocumentDto;
import loans.com.example.demo.entity.DocumentEntity;
import loans.com.example.demo.entity.DocumentType;
import loans.com.example.demo.entity.LoanEntity;
import loans.com.example.demo.entity.UserEntity;
import loans.com.example.demo.repository.DocumentRepository;
import loans.com.example.demo.repository.LoanRepository;
import loans.com.example.demo.repository.UserRepository;
import loans.com.example.demo.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public DocumentEntity uploadDocument(DocumentDto dto, String username) {
        UserEntity user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        LoanEntity loan = loanRepository.findById(dto.getLoanId())
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        String filePath = "uploads/" + dto.getFile().getOriginalFilename();

        DocumentEntity doc = new DocumentEntity();
        doc.setDocumentType(DocumentType.valueOf(dto.getDocumentType()));
        doc.setFilePath(filePath);
        doc.setLoan(loan);
        doc.setUser(user);

        return documentRepository.save(doc);
    }
}
