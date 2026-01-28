package loans.com.example.demo.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "documents")
@Getter
@Setter
public class DocumentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long documentId;

    @Enumerated(EnumType.STRING)
    private DocumentType documentType;

    private String filePath;

    private String status = "UPLOADED";

    @ManyToOne
    @JoinColumn(name = "loan_id")
    private LoanEntity loan;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;


}
