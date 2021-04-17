package plate.iq.api.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import plate.iq.api.model.InvoiceItem;


/**
 * Created by Agnel 16/04/21
 **/
@Getter
@Setter
@NoArgsConstructor
public class InvoiceItemPayload {

    private Long id;

    private Long itemNumber;

    private String name;

    private String description;

    private String code;

    private Long unitPrice;

    private Long quantity;

    private Long total;

    private Long tax;

    private Long taxPercentage;

    public InvoiceItemPayload(InvoiceItem invoiceItem) {
        id = invoiceItem.getId();
        itemNumber = invoiceItem.getItemNumber();
        name = invoiceItem.getName();
        description = invoiceItem.getDescription();
        code = invoiceItem.getCode();
        unitPrice = invoiceItem.getUnitPrice();
        quantity = invoiceItem.getQuantity();
        total = invoiceItem.getTotal();
        tax = invoiceItem.getTax();
        taxPercentage = invoiceItem.getTaxPercentage();
    }
}
