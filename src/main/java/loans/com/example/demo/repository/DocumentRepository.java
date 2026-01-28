package loans.com.example.demo.repository;

import loans.com.example.demo.entity.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<DocumentEntity ,Long> {

}
