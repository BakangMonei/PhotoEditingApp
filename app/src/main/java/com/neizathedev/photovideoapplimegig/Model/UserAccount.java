package com.neizathedev.photovideoapplimegig.Model;

import java.util.Objects;

/**
 * This class is for the CRUDE functions
 *
 * @Author: Monei Bakang Mothuti
 * @Date: Thursday July 2023
 * @Time: 1:34 AM
 */

public class UserAccount {
    // Attributes
    String email, username, firstName, lastName, omang, dob, gender, country, phone, postalAddress, physicalAddress, Occupation;
    int cardNumber, cvc, expiryDate;

    // Constructors
    public UserAccount(){
        super();
    }

    // Getters

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getOmang() {
        return omang;
    }

    public String getDob() {
        return dob;
    }

    public String getGender() {
        return gender;
    }

    public String getCountry() {
        return country;
    }

    public String getPhone() {
        return phone;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public String getPhysicalAddress() {
        return physicalAddress;
    }

    public String getOccupation() {
        return Occupation;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public int getCvc() {
        return cvc;
    }

    public int getExpiryDate() {
        return expiryDate;
    }

    // Setters

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setOmang(String omang) {
        this.omang = omang;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public void setPhysicalAddress(String physicalAddress) {
        this.physicalAddress = physicalAddress;
    }

    public void setOccupation(String occupation) {
        Occupation = occupation;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setCvc(int cvc) {
        this.cvc = cvc;
    }

    public void setExpiryDate(int expiryDate) {
        this.expiryDate = expiryDate;
    }

    // toString()

    @Override
    public String toString() {
        return "UserAccount{" +
                "email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", omang='" + omang + '\'' +
                ", dob='" + dob + '\'' +
                ", gender='" + gender + '\'' +
                ", country='" + country + '\'' +
                ", phone='" + phone + '\'' +
                ", postalAddress='" + postalAddress + '\'' +
                ", physicalAddress='" + physicalAddress + '\'' +
                ", Occupation='" + Occupation + '\'' +
                ", cardNumber=" + cardNumber +
                ", cvc=" + cvc +
                ", expiryDate=" + expiryDate +
                '}';
    }

    // equals() && hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAccount that = (UserAccount) o;
        return cardNumber == that.cardNumber && cvc == that.cvc && expiryDate == that.expiryDate && email.equals(that.email) && username.equals(that.username) && firstName.equals(that.firstName) && lastName.equals(that.lastName) && omang.equals(that.omang) && dob.equals(that.dob) && gender.equals(that.gender) && country.equals(that.country) && phone.equals(that.phone) && postalAddress.equals(that.postalAddress) && physicalAddress.equals(that.physicalAddress) && Occupation.equals(that.Occupation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, username, firstName, lastName, omang, dob, gender, country, phone, postalAddress, physicalAddress, Occupation, cardNumber, cvc, expiryDate);
    }
}
