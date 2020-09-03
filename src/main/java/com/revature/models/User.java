package com.revature.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "project_1.ers_users")
public class User {
    @Id
    @Column(name = "id")
    private int userId;

    @Column(name = "username")
    private String username;

    private String password;

    @Column(name = "first_name")
    private String firstname;

    @Column(name = "last_name")
    private String lastname;

    @Column(name = "email")
    private String email;

    //private Role userRole;

    public User() {
        super();
    }

    public User(String username, String password, String firstname, String lastname, String email) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public User(int userId, String username, String password, String firstname, String lastname, String email, Role userRole) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        //this.userRole = userRole;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public Role getUserRole() {
//        return userRole;
//    }
//
//    public void setUserRole(Role userRole) {
//        this.userRole = userRole;
//    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof User)) return false;
//        User user = (User) o;
//        return getUserId() == user.getUserId() &&
//                Objects.equals(getUsername(), user.getUsername()) &&
//                Objects.equals(getPassword(), user.getPassword()) &&
//                Objects.equals(getFirstname(), user.getFirstname()) &&
//                Objects.equals(getLastname(), user.getLastname()) &&
//                Objects.equals(getEmail(), user.getEmail()) &&
//                getUserRole() == user.getUserRole();
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(getUserId(), getUsername(), getPassword(), getFirstname(), getLastname(), getEmail(), getUserRole());
//    }
//
//    @Override
//    public String toString() {
//        return "User{" +
//                "userId=" + userId +
//                ", username='" + username + '\'' +
//                ", password='" + password + '\'' +
//                ", firstname='" + firstname + '\'' +
//                ", lastname='" + lastname + '\'' +
//                ", email='" + email + '\'' +
//                ", userRole=" + userRole +
//                '}';
//    }
}

