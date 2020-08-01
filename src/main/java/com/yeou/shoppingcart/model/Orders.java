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

public class Orders {
    private int orderid;
    private int userid;

    public Orders() {
    }

    public Orders(int orderid, int userid) {
        this.orderid = orderid;
        this.userid = userid;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    @Override
    public String toString() {
        return "Orders{" + "orderid=" + orderid + ", userid=" + userid + '}';
    }
    
    
}
