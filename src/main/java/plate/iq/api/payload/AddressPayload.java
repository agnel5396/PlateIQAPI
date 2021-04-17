package plate.iq.api.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import plate.iq.api.model.Address;

/**
 * Created by Agnel 16/04/21
 **/
@Setter
@Getter
@NoArgsConstructor
public class AddressPayload {

    private String recipientName;

    private String companyName;

    private String streetAddress;

    private String city;

    private String state;

    private String zipCode;

    private String phone;

    public AddressPayload(Address address) {
        recipientName = address.getRecipientName();
        companyName = address.getCompanyName();
        streetAddress = address.getStreetAddress();
        city = address.getCity();
        state = address.getState();
        zipCode = address.getZipCode();
        phone = address.getPhone();
    }
}
