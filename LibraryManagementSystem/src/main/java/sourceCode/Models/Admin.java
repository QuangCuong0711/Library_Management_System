package sourceCode.Models;

import java.time.LocalDate;

public class Admin extends User {

    public Admin() {
        super();
    }

    public Admin(String userId, String name, String identityNumber, LocalDate birth, String gender,
            String phoneNumber, String email, String address, String password) {
        super(userId, name, identityNumber, birth, gender, phoneNumber, email, address, password);
    }
}
