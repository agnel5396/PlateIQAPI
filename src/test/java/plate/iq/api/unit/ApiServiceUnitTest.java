package plate.iq.api.unit;


import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import plate.iq.api.exception.DocumentNotFoundException;
import plate.iq.api.model.Document;
import plate.iq.api.model.Invoice;
import plate.iq.api.payload.AddressPayload;
import plate.iq.api.payload.DocumentResponse;
import plate.iq.api.payload.InvoiceItemPayload;
import plate.iq.api.payload.InvoicePayload;
import plate.iq.api.repository.DocumentRepository;
import plate.iq.api.repository.InvoiceRepository;
import plate.iq.api.service.ApiService;
import plate.iq.api.service.ResourceManagementService;

import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Agnel 16/04/21
 **/
@ExtendWith(MockitoExtension.class)
public class ApiServiceUnitTest {

    @InjectMocks
    private ApiService apiService;

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private ResourceManagementService rmService;

    @Test
    public void saveInvoiceFile_whenContentTypeNotMatch(){
        Long customerId =1L;
        MockMultipartFile file = new MockMultipartFile(
                "data","file.sql","application/octet-stream" ,"k".getBytes());

        assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> apiService.saveInvoiceFile(customerId,file));
    }

    @Test
    public void saveInvoiceFile(){
        Long customerId =1L;
        MockMultipartFile file = new MockMultipartFile(
                "data","file.pdf","application/pdf" ,"k".getBytes());
        when(documentRepository.save(any())).thenAnswer(args -> {
                    Document doc = args.getArgument(0, Document.class);
                    doc.setId(1L);
                    return doc;
                });
        DocumentResponse response = apiService.saveInvoiceFile(customerId, file);
        assertThat(response.getDocumentId()).isEqualTo(1L);
        assertThat(response.getIsDigitized()).isFalse();
    }

    @Test
    public void getDocument_whenDocumentNotFound() {
        when(documentRepository.findById(any())).thenReturn(Optional.empty());

        assertThatExceptionOfType(DocumentNotFoundException.class)
                .isThrownBy(() -> apiService.getDocument(1L));
    }

    @Test
    public void getInvoiceResponse_whenDocumentIsNotDigitized() {
        Document document = new Document(1L);
        when(documentRepository.findById(any())).thenReturn(Optional.of(document));

        assertThatExceptionOfType(IllegalAccessException.class)
                .isThrownBy(() -> apiService.getInvoiceResponse(1L));
    }

    @Test
    public void getInvoiceResponse() {
        Document document = new Document(1L);
        document.setIsDigitized(true);
        document.setInvoice(new Invoice());
        when(documentRepository.findById(any())).thenReturn(Optional.of(document));

        assertThat(apiService.getInvoiceResponse(1L)).isNotNull();
    }

    @Test
    public void updateInvoice_whenInvoiceNotFound(){
        when(invoiceRepository.findById(any())).thenReturn(Optional.empty());
        assertThatExceptionOfType(DocumentNotFoundException.class)
                .isThrownBy(() -> apiService.updateInvoice(1L,new InvoicePayload()));

    }

    @Test
    public void updateInvoice() {
        Invoice invoice = new Invoice();
        invoice.setInvoiceItems(new HashSet<>());
        invoice.setDocumentId(1L);
        when(invoiceRepository.findById(any())).thenReturn(Optional.of(invoice));
        InvoicePayload invoicePayload = new InvoicePayload();
        invoicePayload.setComments("comments");
        invoicePayload.setInvoiceId("45");
        invoicePayload.setBillTo(new AddressPayload());
        invoicePayload.getBillTo().setCity("earth");
        invoicePayload.setShipTo(new AddressPayload());
        invoicePayload.getShipTo().setCity("mars");
        invoicePayload.setCompanyAddress(new AddressPayload());
        invoicePayload.getCompanyAddress().setCity("venus");
        InvoiceItemPayload itemPayload = new InvoiceItemPayload();
        itemPayload.setCode("12345");
        invoicePayload.setItems(new HashSet<>());
        invoicePayload.getItems().add(itemPayload);
        when(invoiceRepository.save(any(Invoice.class))).thenAnswer(arg -> arg.getArgument(0));

        InvoicePayload response = apiService.updateInvoice(invoice.getDocumentId(), invoicePayload);
        assertThat(response).usingRecursiveComparison().isEqualTo(invoicePayload);
        ArgumentCaptor<Invoice> captor = ArgumentCaptor.forClass(Invoice.class);
        verify(invoiceRepository).save(captor.capture());
        assertThat(captor.getValue()).extracting(Invoice::getComments,Invoice::getInvoiceId
                , i->i.getBillTo().getCity(), i -> i.getShipTo().getCity())
                .isEqualTo(Lists.list(invoicePayload.getComments(), invoicePayload.getInvoiceId(),
                        invoicePayload.getBillTo().getCity(),invoicePayload.getShipTo().getCity()));
    }
}
