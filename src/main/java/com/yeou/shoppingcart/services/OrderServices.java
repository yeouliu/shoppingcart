/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yeou.shoppingcart.services;
import java.sql.*;
import com.yeou.shoppingcart.model.Orders;
import com.yeou.shoppingcart.model.Users;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Yeou Liu (19059124) 
 */

public class OrderServices {
    
    private static final String DBURL = "jdbc:derby://localhost:1527/sample";
    private static final String USER = "app";
    private static final String PASSWORD = "app";
    
    private Connection conn = null;
    private Statement stmt = null;
    private PreparedStatement pstmt = null;
    private ResultSet resultSet = null;
    
    
    //This method will create the ORDERS Table
    public void createOrdersTable(){
        String CREATE_TABLE = "CREATE TABLE ORDERS ("
                    + "ORDERID INT GENERATED ALWAYS AS IDENTITY (START WITH 1000, INCREMENT BY 1),"
                    + "USERID INT NOT NULL,"
                    + "FOREIGN KEY (USERID) REFERENCES USERS(USERID),"
                    + "PRIMARY KEY (ORDERID))";
        
        
         try{
            conn = DriverManager.getConnection(DBURL,USER,PASSWORD);
            stmt = conn.createStatement();
            stmt.executeUpdate(CREATE_TABLE);
            System.out.println("ORDERS Table Created");
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
            if(stmt != null)
                stmt.close();
            if(conn != null)
                conn.close();
            }catch(Exception e){
               System.out.println(e.getMessage());
            }
        }
         
    }
    
    public boolean insertOrders(Orders order){
        boolean status = false;
        String INSERT_DATA = "INSERT INTO ORDERS(userid) VALUES(?)";
        try{
            conn = DriverManager.getConnection(DBURL,USER,PASSWORD);
            pstmt = conn.prepareStatement(INSERT_DATA);
            
            pstmt.setInt(1, order.getUserid());
            if(pstmt.executeUpdate() == 1){
                status = true;
                System.out.println("Row added successfully");
            }
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println("Code: " +e.getErrorCode());
        }finally{
            try{
            if(stmt != null)
                stmt.close();
            if(conn != null)
                conn.close();
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
    }return status;
    }
    
    //This method will fetch a single column from the ITEM table using the passed ID
    public Orders getIndividualOrders(int orderid) {
        Orders order = new Orders();
        String GET_ORDER= "SELECT * FROM ORDERS WHERE ORDERID = ?";
        try{
            conn = DriverManager.getConnection(DBURL,USER,PASSWORD);
            pstmt = conn.prepareStatement(GET_ORDER);
            pstmt.setInt(1, orderid);
            resultSet = pstmt.executeQuery();
            
            while(resultSet.next()) {
                order.setOrderid(resultSet.getInt(1));
                order.setUserid(resultSet.getInt(2));
                System.out.println(resultSet.getInt(1) + "\t" + 
                        resultSet.getInt(2));
            }
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println("Code: " +e.getErrorCode());
        }finally{
            try{
            if(stmt != null)
                stmt.close();
            if(conn != null)
                conn.close();
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        return order;
    }
    
     //This method will fetch all the rows present in the USER table
    public JSONArray getAllOrders(){
        JSONArray orderArray = new JSONArray();
        String GET_DATA = "SELECT * FROM ORDERS";
        try{
            conn = DriverManager.getConnection(DBURL,USER,PASSWORD);
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(GET_DATA);
            
            while (resultSet.next()) {
                JSONObject orderObj = new JSONObject();
                orderObj.put("orderid",resultSet.getInt(1));
                orderObj.put("userid",resultSet.getInt(2));
                
                orderArray.add(orderObj);
            }
            
            System.out.println(orderArray);
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println("Code: " +e.getErrorCode());
        }finally{
            try{
            if(stmt != null)
                stmt.close();
            if(conn != null)
                conn.close();
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        return orderArray;
    }
    
}
