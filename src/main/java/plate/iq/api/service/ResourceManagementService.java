package plate.iq.api.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import plate.iq.api.model.Document;

import java.util.UUID;

/**
 * Created by Agnel 16/04/21
 **/
@Service
public class ResourceManagementService {

    public void saveFile(MultipartFile file,Document document){
        //save file in s3 bucket and set temp url and key for access the object
        document.setKey(UUID.randomUUID().toString());
        document.setLink(UUID.randomUUID().toString());

    }
}
