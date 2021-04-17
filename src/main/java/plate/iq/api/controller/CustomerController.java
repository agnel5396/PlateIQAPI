package plate.iq.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import plate.iq.api.model.Document;
import plate.iq.api.payload.DocumentResponse;
import plate.iq.api.payload.InvoicePayload;
import plate.iq.api.service.ApiService;

/**
 * Created by Agnel 16/04/21
 **/
@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final ApiService apiService;

    @PostMapping("/upload")
    public ResponseEntity<DocumentResponse> uploadInvoice (@RequestParam MultipartFile invoice,
                                                           @RequestParam Long customerId) {
        DocumentResponse response = apiService.saveInvoiceFile(customerId, invoice);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/document")
    public ResponseEntity<DocumentResponse> getDocumentStatus(@RequestParam Long documentId){
        Document document = apiService.getDocument(documentId);
        return ResponseEntity.ok(new DocumentResponse(document));
    }

    @GetMapping("/invoice")
    public ResponseEntity<InvoicePayload> getInvoice(@RequestParam Long documentId) {
        InvoicePayload response = apiService.getInvoiceResponse(documentId);
        if (response == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(response);
    }
}
