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
@NoArgsConstructor
@Entity
@Table(name = "invoice_items")
public class InvoiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Invoice invoice;

    private Long itemNumber;

    private String name;

    private String description;

    private String code;

    private Long unitPrice;

    private Long quantity;

    private Long total;

    private Long tax;

    private Long taxPercentage;
}
