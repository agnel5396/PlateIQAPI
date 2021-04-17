package plate.iq.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plate.iq.api.payload.InvoicePayload;
import plate.iq.api.payload.UpdateDocumentRequest;
import plate.iq.api.service.ApiService;

/**
 * Created by Agnel 16/04/21
 **/
@RestController
@RequestMapping("/internal")
@RequiredArgsConstructor
public class InternalController {
    final private ApiService apiService;

    @PutMapping("/invoice/{documentId}/")
    public ResponseEntity<InvoicePayload> updateInvoice(@PathVariable Long documentId,
                                                        @RequestBody InvoicePayload payload){
        InvoicePayload response = apiService.updateInvoice(documentId, payload);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/document/{documentId}/")
    public ResponseEntity<Object> updateIsDigitized(@PathVariable Long documentId,
                                                    @RequestBody UpdateDocumentRequest request){
        apiService.updateDigitized(documentId, request);
        return ResponseEntity.ok().build();
    }
}
