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

public class OrderDetails {
    
    private int orderdetailid;
    private int orderid;
    private int itemid;
    private int quantity;

    public OrderDetails() {
    }

    public OrderDetails(int orderdetailid, int orderid, int itemid, int quantity) {
        this.orderdetailid = orderdetailid;
        this.orderid = orderid;
        this.itemid = itemid;
        this.quantity = quantity;
    }
    
    
    public OrderDetails(int orderid, int itemid, int quantity) {
        this.orderid = orderid;
        this.itemid = itemid;
        this.quantity = quantity;
    }

    public int getOrderdetailid() {
        return orderdetailid;
    }

    public void setOrderdetailid(int orderdetailid) {
        this.orderdetailid = orderdetailid;
    }
    
    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public int getItemid() {
        return itemid;
    }

    public void setItemid(int itemid) {
        this.itemid = itemid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderDetails{" + "orderid=" + orderid + ", itemid=" + itemid + ", quantity=" + quantity + '}';
    }
    
    
}
