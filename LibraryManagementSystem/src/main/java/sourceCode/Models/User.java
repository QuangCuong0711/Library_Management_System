package sourceCode.Models;

public class User {

    private String name;

    private String id;

    private String date;

    private String phoneNumber;

    private String CCCD;

    private String address;

    private String gender;

    public User() {
        name = "";
        id = "";
        date = "";
        phoneNumber = "";
        CCCD = "";
        address = "";
        gender = "";
    }

    public User(String name, String id, String date, String phoneNumber, String CCCD,
            String address, String gender) {
        this.name = name;
        this.id = id;
        this.date = date;
        this.phoneNumber = phoneNumber;
        this.CCCD = CCCD;
        this.address = address;
        this.gender = gender;
    }

    User(User s) {
        this.name = s.name;
        this.id = s.id;
        this.date = s.date;
        this.phoneNumber = s.phoneNumber;
        this.CCCD = s.CCCD;
        this.address = s.address;
        this.gender = s.gender;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCCCD() {
        return CCCD;
    }

    public void setCCCD(String CCCD) {
        this.CCCD = CCCD;
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
