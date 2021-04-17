package plate.iq.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import plate.iq.api.model.Invoice;

/**
 * Created by Agnel 16/04/21
 **/
@Repository
public interface InvoiceRepository extends CrudRepository<Invoice, Long> {

}
