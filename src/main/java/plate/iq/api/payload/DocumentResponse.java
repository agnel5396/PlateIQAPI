package plate.iq.api.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import plate.iq.api.model.Document;


/**
 * Created by Agnel 16/04/21
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DocumentResponse {
    private Long documentId;

    private Long customerId;

    private String link;

    private Boolean isDigitized;

    private Boolean canProceed;

    public DocumentResponse(Document document) {
        documentId = document.getId();
        customerId = document.getCustomerId();
        link = document.getLink();
        isDigitized = document.getIsDigitized();
        canProceed = document.getCanProceed();
    }
}
