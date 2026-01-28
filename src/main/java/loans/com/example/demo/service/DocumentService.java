package loans.com.example.demo.service;

import loans.com.example.demo.dto.DocumentDto;
import loans.com.example.demo.entity.DocumentEntity;


public interface DocumentService  {
    DocumentEntity uploadDocument(DocumentDto dto, String username);
}
