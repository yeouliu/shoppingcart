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

public class Item {
    
    private int itemid; // Unique ID associated with all items in grocery store - Primary Key for table Item
    private String itemname; // Name of Items
    private float price; // Prices of each item

    public Item() {
        // Empty Default constructor
    }

    public Item(String itemname, float price) {
        this.itemname = itemname;
        this.price = price;
    }
    
    // Getters and Setters for all item attributes
    
    public int getItemid() {
        return itemid;
    }

    public void setItemid(int itemid) {
        this.itemid = itemid;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Item{" + "itemid=" + itemid + ", itemname=" + itemname + ", price=" + price + '}';
    }
    
    
    
}
