/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yeou.shoppingcart.model;

import javax.xml.bind.annotation.XmlRootElement;


/**
 *
 * @author Yeou Liu (19059124)
 */

@XmlRootElement

public class UserContact {
    private int userid;
    private String city;
    private int phone;

    public UserContact() {
    }

    public UserContact(int userid, String city, int phone) {
        this.userid = userid;
        this.city = city;
        this.phone = phone;
    }

    public UserContact(String city, int phone) {
        this.city = city;
        this.phone = phone;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "UserContact{" + "userid=" + userid + ", city=" + city + ", phone=" + phone + '}';
    }
    
    
}
