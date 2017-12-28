package com.example.yinon.mycloset;
/**
 * Created by yinon on 02/12/17.
 */
public class Person {
    private String username, password, email, firstname, lastname, city, street, image;
    public Person(String username, String password, String email, String firstname, String lastname, String city, String street, String image){
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.city = city;
        this.street = street;
        this.image = image;
    }
    public String getCity() {
        return city;
    }
    public String getImage() { return image; }
    public String getUsername() { return username; }
    public String getPassword() {
        return password;
    }
    public String getEmail() {
        return email;
    }
    public String getFirstname() {
        return firstname;
    }
    public String getLastname() {
        return lastname;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public void setStreet(String street) { this.street = street; }
    public void setImage(String image) {
        this.image = image;
    }
    public String getStreet() {
        return street;
    }
    public boolean equals(Person p) {
        if (this == p) return true;
        if (p == null) return false;
        if (!username.equals(p.username)) return false;
        if (!email.equals(p.email)) return false;
        return true;
    }
}
