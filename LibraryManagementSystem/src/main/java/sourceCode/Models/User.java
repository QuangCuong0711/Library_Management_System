package sourceCode.Models;

public class User {

    private String name;

    private String userId;

    private String identityNumber;

    private String birth;

    private String gender;

    private String phoneNumber;

    private String email;

    private String address;


    public User() {
        name = "";
        userId = "";
        birth = "";
        phoneNumber = "";
        identityNumber = "";
        address = "";
        gender = "";
    }

    public User(String name, String userId, String birth, String phoneNumber, String identityNumber,
            String address, String gender) {
        this.name = name;
        this.userId = userId;
        this.phoneNumber = phoneNumber;
        this.identityNumber = identityNumber;
        this.address = address;
        this.gender = gender;
        this.birth = birth;
    }

    public User(User other) {
        this.name = other.name;
        this.userId = other.userId;
        this.birth = other.birth;
        this.phoneNumber = other.phoneNumber;
        this.identityNumber = other.identityNumber;
        this.address = other.address;
        this.gender = other.gender;
        this.email = other.email;
    }

    public User(String name, String userId, String identityNumber, String birth, Object o1, Object o2,
            Object o3, Object o4) {
        this.name = name;
        this.userId = userId;
        this.identityNumber = identityNumber;
        this.birth = birth;
        o1 = null;
        o2 = null;
        o3 = null;
        o4 = null;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
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
