/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yeou.shoppingcart.controller;

//import com.yeou.grocerystore.model.Users;
import com.yeou.shoppingcart.model.Item;
import com.yeou.shoppingcart.model.OrderDetails;
import com.yeou.shoppingcart.model.Orders;
import com.yeou.shoppingcart.model.UserContact;
import com.yeou.shoppingcart.model.Users;
import com.yeou.shoppingcart.services.UserServices;
import com.yeou.shoppingcart.services.ItemServices;
import com.yeou.shoppingcart.services.OrderServices;
import com.yeou.shoppingcart.services.OrderDetailsServices;
import com.yeou.shoppingcart.services.UserContactServices;
import java.sql.Date;
import java.util.Calendar;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Yeou Liu (19059124)
 */
@Path("/")
public class ShoppingCartController {

    //Create all services objects in order to use the services available in those classes
    UserServices us = new UserServices(); // UserServices Object in order to invoke its services
    ItemServices is = new ItemServices(); // ItemServices Object in order to invoke its services
    OrderServices os = new OrderServices(); // OrderServices Object in order to invoke its services
    OrderDetailsServices ods = new OrderDetailsServices(); // OrderDetailServices Object in order to invoke its services
    UserContactServices ucs = new UserContactServices(); // UserContactServices Object in order to invoke its services

    //ShoppinCartResouces Constructor :: Will create all the tables when invoked
    public ShoppingCartController() {
        us.createUserTable(); //Create Users Table
        is.createItemTable(); //Create Item Table
        os.createOrdersTable(); //Create Orders Table
        ods.createOrderDetailsTable(); //Create OrderDetails Table
        ucs.createUserContactTable(); //Create UserContact Table
    }

    //Insert users in the database
    @GET
    @Path("adduser")
    @Produces(MediaType.TEXT_PLAIN)

    public String insertUsers(@QueryParam("username") String uname, @QueryParam("dob") String dob, String jsonpcallback) {

        String val = "Error Check values!";
        Users user = new Users();

        String[] dateval = dob.split("/|-");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Integer.parseInt(dateval[0]));
        calendar.set(Calendar.MONTH, Integer.parseInt(dateval[1]) - 1);
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateval[2]));
        java.sql.Date sqldob = new java.sql.Date(calendar.getTimeInMillis());

        user.setUsername(uname);
        user.setDob(sqldob);
        boolean status = us.insertUsers(user);
        if (status == true) {
            val = "Added!!";
        }
        return jsonpcallback + "(\"" + val + "\")";
    }

    //Display users available in the system
    @Path("showuser")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getUsers(@QueryParam("jsonpcallback") String jsonpcallback) {
        return jsonpcallback + "(" + us.getAllUsers().toJSONString() + ")";
    }

    //Insert items in the database
    @GET
    @Path("insertitem")
    @Produces(MediaType.TEXT_PLAIN)
    public String insertItem(@QueryParam("itemname") String iname, @QueryParam("price") float iprice, String jsonpcallback) {
        String val = "Error Check Values being added!!!";
        Item item = new Item();
        item.setItemname(iname);
        item.setPrice(iprice);
        boolean status = is.insertItems(item);
        if (status == true) {
            val = "Added!!";
        }
        return jsonpcallback + "(\"" + val + "\")";
        
    }

    //Display all items in the database
    @Path("showitem")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getItem(@QueryParam("jsonpcallback") String jsonpcallback) {
        return jsonpcallback + "(" + is.getAllItems().toJSONString() + ")";
    }

    //Fill User dropdown List used to fill usercontact and order table
    @GET
    @Path("userlist")
    @Produces(MediaType.TEXT_PLAIN)
    public String getUserList(@QueryParam("jsonpcallback") String jsonpcallback) {
        return jsonpcallback + "(" + us.getAllUsers().toJSONString() + ")";
    }

    //Fill order dropdown List used to fill order details
    @GET
    @Path("orderlist")
    @Produces(MediaType.TEXT_PLAIN)
    public String getOrderList(@QueryParam("jsonpcallback") String jsonpcallback) {
        return jsonpcallback + "(" + os.getAllOrders().toJSONString() + ")";
    }

    //Fill item dropdown List used in orderdetails table
    @GET
    @Path("itemlist")
    @Produces(MediaType.TEXT_PLAIN)
    public String getItemList(@QueryParam("jsonpcallback") String jsonpcallback) {
        return jsonpcallback + "(" + is.getAllItems().toJSONString() + ")";
    }

    //1:1 Insert values in usercontact values in table
    //UserContact table has a 1:1 relationship i.e. one user can have only one corresponding contact details
    @Path("adduc")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String insertUserContact(@QueryParam("userid") int uid, @QueryParam("city") String ct, @QueryParam("phone") int tel, @QueryParam("jsonpcallback") String jsonpcallback) {
        // public String insertUserContact(@QueryParam("usercontact") UserContact uc, @QueryParam("jsonpcallback") String jsonpcallback) {
        String val = "Error! Check Constraints";
        UserContact uc = new UserContact();
        uc.setUserid(uid);
        uc.setCity(ct);
        uc.setPhone(tel);
        boolean status = ucs.insertUserContact(uc);
        if (status == true) {
            val = "Added!!";
        }
        return jsonpcallback + "(\"" + val + "\")";
    }

    //Dsiplay all usercontact values in the DB
    @Path("showusercontact")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getUserContact(@QueryParam("jsonpcallback") String jsonpcallback) {
        return jsonpcallback + "(" + ucs.getAllUserContacts().toJSONString() + ")";
    }

    //M:M Insert values in OrderDetails table
    //OrderDetails table has M:M relationship as multiple orders can include same items and viceversa
    @Path("insertorderdetails")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String insertOrderDetails(@QueryParam("jsonpcallback") String jsonpcallback, @QueryParam("orderid") int orderid, @QueryParam("itemid") int itemid, @QueryParam("quantity") int quantity) {
        String val = "Error! Check constraints";
        OrderDetails od = new OrderDetails();
        od.setOrderid(orderid);
        od.setItemid(itemid);
        od.setQuantity(quantity);
        boolean status = ods.insertOrderDetails(od);
        if (status == true) {
            val = "Added!!";
        }
        return jsonpcallback + "(\"" + val + "\")";
    }

    //display order details entries in the database
    @Path("showorderdetails")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getOrderDetails(@QueryParam("jsonpcallback") String jsonpcallback) {
        return jsonpcallback + "(" + ods.getAllOrderDetails().toJSONString() + ")";
    }

    //Insert values in orders table
    //1:M relationship as a user can make multiple orders but one order can not belong to multiple users
    @Path("insertorder")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String insertOrder(@QueryParam("jsonpcallback") String jsonpcallback, @QueryParam("userid") int userid) {
        String val = "Error!Check constraints";
        Orders order = new Orders();
        order.setUserid(userid);
        boolean status = os.insertOrders(order);
        if (status == true) {
            val = "Added!!";
        }
        return jsonpcallback + "(\"" + val + "\")";
    }

    //Display all orders available in the DB
    @Path("showorder")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getOrder(@QueryParam("jsonpcallback") String jsonpcallback) {
        return jsonpcallback + "(" + os.getAllOrders().toJSONString() + ")";
    }

}
