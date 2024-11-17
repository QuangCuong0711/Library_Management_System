package sourceCode.Models;

import java.time.LocalDate;

public class User {

    private String userId;

    private String name;

    private String identityNumber;

    private LocalDate birth;

    private String gender;

    private String phoneNumber;

    private String email;

    private String address;


    public User() {
        userId = "";
        name = "";
        identityNumber = "";
        birth = LocalDate.now();
        gender = "";
        phoneNumber = "";
        email = "";
        address = "";
    }

    public User(String userId, String name, String identityNumber, LocalDate birth, String gender,
            String phoneNumber, String email, String address) {
        this.userId = userId;
        this.name = name;
        this.identityNumber = identityNumber;
        this.birth = birth;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
    }

    public User(User other) {
        this.userId = other.userId;
        this.name = other.name;
        this.identityNumber = other.identityNumber;
        this.birth = other.birth;
        this.gender = other.gender;
        this.phoneNumber = other.phoneNumber;
        this.email = other.email;
        this.address = other.address;
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

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
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
