package plate.iq.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

/**
 * Created by Agnel 16/04/21
 **/
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "invoices")
public class Invoice {

    @Id
    private Long documentId;

    @MapsId
    @OneToOne
    @JoinColumn(name = "documentId", unique = true, nullable = false, referencedColumnName = "id")
    private Document document;

    private String invoiceId;

    private Date created;

    private Long subTotal;

    private Long tax;

    private Long taxPercentage;

    private Long otherCost;

    private Long amountDue;

    private Date dueDate;

    private String comments;

    private String terms;

    private String customerId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ship_to", referencedColumnName = "id")
    private Address shipTo;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bill_to", referencedColumnName = "id")
    private Address billTo;

    // address of company as printed on invoice
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address", referencedColumnName = "id")
    private Address address;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "invoice", fetch = FetchType.LAZY)
    private Set<InvoiceItem> invoiceItems;
}
