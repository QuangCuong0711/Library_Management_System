package sourceCode.Models;

public class User {

    private String name;

    private String id;

    private String birthDate;

    private String phoneNumber;

    private String indentityCard;

    private String address;

    private String gender;

    public User() {
        name = "";
        id = "";
        birthDate = "";
        phoneNumber = "";
        indentityCard = "";
        address = "";
        gender = "";
    }

    public User(String name, String id, String birthDate, String phoneNumber, String indentityCard,
            String address, String gender) {
        this.name = name;
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.indentityCard = indentityCard;
        this.address = address;
        this.gender = gender;
        this.birthDate = birthDate;
    }

    User(User other) {
        this.name = other.name;
        this.id = other.id;
        this.birthDate = other.birthDate;
        this.phoneNumber = other.phoneNumber;
        this.indentityCard = other.indentityCard;
        this.address = other.address;
        this.gender = other.gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getbirthDate() {
        return birthDate;
    }

    public void setbirthDate(String date) {
        this.birthDate = date;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getindentityCard() {
        return indentityCard;
    }

    public void setindentityCard(String indentityCard) {
        this.indentityCard = indentityCard;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
