package plate.iq.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Agnel 16/04/21
 **/
@ResponseStatus(HttpStatus.NOT_FOUND)
public class DocumentNotFoundException extends RuntimeException {
    public DocumentNotFoundException(Long documentId) {
        super(String.format("Document not found for id %s", documentId));

    }
}
