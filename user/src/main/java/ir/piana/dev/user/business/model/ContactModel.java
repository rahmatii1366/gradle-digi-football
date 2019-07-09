package ir.piana.dev.user.business.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Mohamad Rahmati (rahmatii1366@gmail.com)
 * Date: 6/23/2019 2:50 PM
 **/
@Data
public class ContactModel {
    private String email;
    private String password;

    public ContactModel() {
    }

    public ContactModel(String email, String password) {
        this.email = email;
        this.password = password;
    }
}


