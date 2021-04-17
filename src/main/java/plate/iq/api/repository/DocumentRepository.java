package plate.iq.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import plate.iq.api.model.Document;

/**
 * Created by Agnel 16/04/21
 **/
@Repository
public interface DocumentRepository extends CrudRepository<Document, Long> {

}
