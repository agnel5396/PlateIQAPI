package plate.iq.api.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import plate.iq.api.exception.DocumentNotFoundException;
import plate.iq.api.model.Address;
import plate.iq.api.model.Document;
import plate.iq.api.model.Invoice;
import plate.iq.api.model.InvoiceItem;
import plate.iq.api.payload.*;
import plate.iq.api.repository.DocumentRepository;
import plate.iq.api.repository.InvoiceRepository;

import java.util.Optional;

/**
 * Created by Agnel 16/04/21
 **/
@Service
@RequiredArgsConstructor
public class ApiService {

    private final ResourceManagementService rmService;
    private final DocumentRepository documentRepository;
    private final InvoiceRepository invoiceRepository;

    public DocumentResponse saveInvoiceFile(Long customerId, MultipartFile file){

        Document document = new Document(customerId);
        if(!isPdfFile(file)){
            throw new IllegalStateException();
        }
        rmService.saveFile(file, document);
        Invoice invoice = new Invoice();
        invoice.setDocument(document);
        document.setInvoice(invoice);
        Document save = documentRepository.save(document);
        return new DocumentResponse(save);
    }

    private boolean isPdfFile(MultipartFile file){
        final String PDF_FILE_CONTENT_TYPE = "application/pdf";
        String contentType = file.getContentType();
        if(file.isEmpty() || contentType == null) return false;
        return contentType.equals(PDF_FILE_CONTENT_TYPE);
    }

    public Document getDocument(Long documentId) {
        Optional<Document> byId = documentRepository.findById(documentId);
        if (byId.isEmpty()){
            throw new DocumentNotFoundException(documentId);
        }
        return byId.get();
    }

    @SneakyThrows
    public InvoicePayload getInvoiceResponse(Long documentId) {
        Document document = getDocument(documentId);
        if(!document.getIsDigitized()){
            throw new IllegalAccessException();
        }
        Invoice invoice = document.getInvoice();
        return getInvoiceResponse(invoice);
    }

    private InvoicePayload getInvoiceResponse(Invoice invoice){
        InvoicePayload invoicePayload = new InvoicePayload(invoice);
        if (invoice.getInvoiceItems() != null){
            for (InvoiceItem invoiceItem:invoice.getInvoiceItems()) {
                InvoiceItemPayload itemPayload = new InvoiceItemPayload(invoiceItem);
                invoicePayload.addItem(itemPayload);
            }

        }
        return invoicePayload;
    }

    public InvoicePayload updateInvoice(Long documentId, InvoicePayload payload) {
        Optional<Invoice> byId = invoiceRepository.findById(documentId);
        if (byId.isEmpty()){
            throw new DocumentNotFoundException(documentId);
        }
        Invoice invoice = byId.get();
        if (payload.getInvoiceId() != null){
            invoice.setInvoiceId(payload.getInvoiceId());
        }

        if (payload.getCreated() != null){
            invoice.setCreated(payload.getCreated());
        }

        if (payload.getSubTotal() != null){
            invoice.setSubTotal(payload.getSubTotal());
        }

        if (payload.getTax() != null){
            invoice.setTax(payload.getTax());
        }

        if (payload.getTaxPercentage() != null){
            invoice.setTaxPercentage(payload.getTaxPercentage());
        }

        if (payload.getOtherCost() != null){
            invoice.setOtherCost(payload.getOtherCost());
        }

        if (payload.getAmountDue() != null ){
            invoice.setOtherCost(payload.getOtherCost());
        }

        if (payload.getDueDate() != null ){
            invoice.setDueDate(payload.getDueDate());
        }

        if (payload.getComments() != null ){
            invoice.setComments(payload.getComments());
        }

        if (payload.getTerms() != null ){
            invoice.setTerms(payload.getTerms());
        }

        if (payload.getCustomerId() != null ){
            invoice.setCustomerId(payload.getCustomerId());
        }

        if (payload.getShipTo() != null ){
            Address address = updateAddress(invoice.getShipTo(), payload.getShipTo());
            invoice.setShipTo(address);
        }

        if (payload.getBillTo() != null ){
            Address address = updateAddress(invoice.getBillTo(), payload.getBillTo());
            invoice.setBillTo(address);
        }

        if (payload.getCompanyAddress() != null ){
            Address address = updateAddress(invoice.getAddress(), payload.getCompanyAddress());
            invoice.setAddress(address);
        }

        if (payload.getItems() != null) {
            for (InvoiceItemPayload itemPayload:payload.getItems()) {
                updateInvoiceItem(invoice, itemPayload);
            }
        }
        Invoice save = invoiceRepository.save(invoice);
        return getInvoiceResponse(save);
    }

    private void updateInvoiceItem(Invoice invoice, InvoiceItemPayload itemPayload) {
        InvoiceItem invoiceItem = new InvoiceItem();

        if (itemPayload.getId() != null) {
            for (InvoiceItem item: invoice.getInvoiceItems()){
                if (item.getId().equals(itemPayload.getId())){
                    invoiceItem = item;
                    break;
                }
            }
        }else {
            invoiceItem.setInvoice(invoice);
            invoice.getInvoiceItems().add(invoiceItem);
        }
        if (itemPayload.getItemNumber() != null){
            invoiceItem.setItemNumber(itemPayload.getItemNumber());
        }

        if (itemPayload.getName() != null){
            invoiceItem.setName(itemPayload.getName());
        }

        if (itemPayload.getDescription() != null){
            invoiceItem.setDescription(itemPayload.getDescription());
        }

        if (itemPayload.getCode() != null){
            invoiceItem.setCode(itemPayload.getCode());
        }

        if (itemPayload.getUnitPrice() != null){
            invoiceItem.setUnitPrice(itemPayload.getUnitPrice());
        }

        if (itemPayload.getQuantity() != null){
            invoiceItem.setQuantity(itemPayload.getQuantity());
        }

        if (itemPayload.getTotal() != null){
            invoiceItem.setTotal(itemPayload.getTotal());
        }

        if (itemPayload.getTax() != null){
            invoiceItem.setTax(itemPayload.getTax());
        }

        if (itemPayload.getTaxPercentage() != null){
            invoiceItem.setTaxPercentage(itemPayload.getTaxPercentage());
        }

    }

    private Address updateAddress(Address address, AddressPayload payload) {
        if (address == null) address = new Address();
        if (payload.getRecipientName() != null) {
            address.setRecipientName(payload.getRecipientName());
        }

        if (payload.getCompanyName() != null) {
            address.setCompanyName(payload.getCompanyName());
        }

        if (payload.getStreetAddress() != null) {
            address.setStreetAddress(payload.getStreetAddress());
        }

        if (payload.getCity() != null) {
            address.setCity(payload.getCity());
        }

        if (payload.getState() != null) {
            address.setState(payload.getState());
        }

        if (payload.getZipCode() != null) {
            address.setZipCode(payload.getZipCode());
        }

        if (payload.getPhone() != null) {
            address.setPhone(payload.getPhone());
        }
        return address;
    }

    public void updateDigitized(Long documentId, UpdateDocumentRequest request) {
        Document document = getDocument(documentId);
        if (request.getCanProceed() != null){
            document.setCanProceed(request.getCanProceed());
        }
        if (request.getIsDigitized() != null){
            document.setIsDigitized(request.getIsDigitized());
        }
        documentRepository.save(document);
    }
}
