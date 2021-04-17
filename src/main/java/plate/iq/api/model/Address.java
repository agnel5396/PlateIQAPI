package plate.iq.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by Agnel 16/04/21
 **/
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String recipientName;

    private String companyName;

    private String streetAddress;

    private String city;

    private String state;

    private String zipCode;

    private String phone;
}
