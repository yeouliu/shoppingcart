/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yeou.shoppingcart.model;
//import java.util.Date;
import java.sql.Date;
import javax.xml.bind.annotation.XmlRootElement;
/**
 *
 * @author Yeou Liu (19059124)
 */

@XmlRootElement

public class Users {
    private int userid; // User ID : auto increment and primary key of table Users
    private String username; // Store user name
    private Date dob; // Store date of birth of users
    //private String city; // store user's city
    //private long phone; // store user's phone number

    public Users() {
        // empty default constructor
    }
    //Parameterized constructor
    public Users(String username, Date dob) {
        this.username = username;
        this.dob = dob;
    }

//    public Users(String city, long phone) {
//        this.city = city;
//        this.phone = phone;
//    }

    //Getters and Setters of Users attributes
    
    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    @Override
    public String toString() {
        return "Users{" + "userid=" + userid + ", username=" + username + ", dob=" + dob + '}';
    }

    
}
