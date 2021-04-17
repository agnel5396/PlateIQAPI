package plate.iq.api.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import plate.iq.api.model.Invoice;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Agnel 16/04/21
 **/
@Getter
@Setter
@NoArgsConstructor
public class InvoicePayload {

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

    private AddressPayload shipTo;

    private AddressPayload billTo;

    private AddressPayload companyAddress;

    private Set<InvoiceItemPayload> items;

    public InvoicePayload(Invoice invoice) {
        invoiceId = invoice.getInvoiceId();
        created = invoice.getCreated();
        subTotal = invoice.getSubTotal();
        tax = invoice.getTax();
        taxPercentage = invoice.getTaxPercentage();
        otherCost = invoice.getOtherCost();
        amountDue = invoice.getAmountDue();
        dueDate = invoice.getDueDate();
        comments = invoice.getComments();
        terms = invoice.getTerms();
        customerId = invoice.getCustomerId();
        if (invoice.getShipTo() != null) {
            shipTo = new AddressPayload(invoice.getShipTo());
        }
        if (invoice.getBillTo() != null) {
            billTo = new AddressPayload(invoice.getBillTo());
        }
        if (invoice.getAddress() != null) {
            companyAddress = new AddressPayload(invoice.getAddress());
        }
    }

    public void addItem(InvoiceItemPayload itemPayload) {
        if (items == null) items = new HashSet<>();
        items.add(itemPayload);
    }
}
