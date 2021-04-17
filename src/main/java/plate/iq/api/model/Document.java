package plate.iq.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by Agnel 16/04/21
 **/
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "documents")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long customerId;

    // key from resource management eg. s3 bucket
    @Column(nullable = false)
    private String key;

    private String link;

    private Boolean isDigitized;

    private Boolean canProceed;

    @OneToOne(mappedBy = "document", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Invoice invoice;

    public Document(Long customerId){
        this.customerId = customerId;
        this.isDigitized = false;
        this.canProceed = true;
    }
}
