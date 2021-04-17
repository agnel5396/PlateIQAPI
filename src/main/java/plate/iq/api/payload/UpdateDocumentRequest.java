package plate.iq.api.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Agnel 17/04/21
 **/
@Setter
@Getter
@NoArgsConstructor
public class UpdateDocumentRequest {
    private Boolean isDigitized;

    private Boolean canProceed;
}
