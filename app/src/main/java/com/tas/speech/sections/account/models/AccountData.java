package com.tas.speech.sections.account.models;

public class AccountData {

    private int BornYear;
    private String Email, FirstName, Gender, HomeCountry, LastName, NativeLanguage, Password;

    public AccountData() {

    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public int getBornYear() {
        return BornYear;
    }

    public void setBornYear(int bornYear) {
        BornYear = bornYear;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getHomeCountry() {
        return HomeCountry;
    }

    public void setHomeCountry(String homeCountry) {
        HomeCountry = homeCountry;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getNativeLanguage() {
        return NativeLanguage;
    }

    public void setNativeLanguage(String nativeLanguage) {
        NativeLanguage = nativeLanguage;
    }
}
