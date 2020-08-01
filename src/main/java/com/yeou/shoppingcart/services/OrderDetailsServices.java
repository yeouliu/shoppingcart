/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yeou.shoppingcart.services;
import java.sql.*;
import com.yeou.shoppingcart.model.Orders;
import com.yeou.shoppingcart.model.Item;
import com.yeou.shoppingcart.model.OrderDetails;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
/**
 *
 * @author Yeou Liu (19059124)
 */
public class OrderDetailsServices {
    private static final String DBURL = "jdbc:derby://localhost:1527/sample";
    private static final String USER = "app";
    private static final String PASSWORD = "app";
    
    private Connection conn = null;
    private Statement stmt = null;
    private PreparedStatement pstmt = null;
    private ResultSet resultSet = null;
    
    
    //This method will create the ORDERS Table
    public void createOrderDetailsTable(){
        String CREATE_TABLE = "CREATE TABLE ORDERDETAILS ("
                    + "ORDERDETAILID INT GENERATED ALWAYS AS IDENTITY (START WITH 2000, INCREMENT BY 1),"
                    + "ORDERID INT NOT NULL,"
                    + "ITEMID INT NOT NULL,"
                    + "QUANTITY INT NOT NULL,"
                    + "FOREIGN KEY (ORDERID) REFERENCES ORDERS(ORDERID),"
                    + "FOREIGN KEY (ITEMID) REFERENCES ITEM(ITEMID),"
                    + "PRIMARY KEY (ORDERDETAILID))";
        
        
         try{
            conn = DriverManager.getConnection(DBURL,USER,PASSWORD);
            stmt = conn.createStatement();
            stmt.executeUpdate(CREATE_TABLE);
            System.out.println("ORDERDETAILS Table Created");
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
    
    public boolean insertOrderDetails(OrderDetails orderDetail){
        boolean status = false;
        String INSERT_DATA = "INSERT INTO ORDERDETAILS(ORDERID,ITEMID,QUANTITY) VALUES(?,?,?)";
        try{
            conn = DriverManager.getConnection(DBURL,USER,PASSWORD);
            pstmt = conn.prepareStatement(INSERT_DATA);
            
            pstmt.setInt(1, orderDetail.getOrderid());
            pstmt.setInt(2, orderDetail.getItemid());
            pstmt.setInt(3, orderDetail.getQuantity());
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
    public OrderDetails getIndividualOrderDetails(int orderid) {
        OrderDetails orderdetail = new OrderDetails();
        String GET_ORDER= "SELECT * FROM ORDERDETAILS WHERE ORDERID = ?";
        try{
            conn = DriverManager.getConnection(DBURL,USER,PASSWORD);
            pstmt = conn.prepareStatement(GET_ORDER);
            pstmt.setInt(1, orderid);
            resultSet = pstmt.executeQuery();
            
            while(resultSet.next()) {
                orderdetail.setOrderid(resultSet.getInt(1));
                orderdetail.setItemid(resultSet.getInt(2));
                orderdetail.setQuantity(resultSet.getInt(3));
                System.out.println(resultSet.getInt(1) + "\t" + resultSet.getInt(2) + "\t" + resultSet.getInt(3));
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
        return orderdetail;
    }
    
     //This method will fetch all the rows present in the USER table
    public JSONArray getAllOrderDetails(){
        JSONArray odArray = new JSONArray();
        String GET_DATA = "SELECT * FROM ORDERDETAILS";
        try{
            conn = DriverManager.getConnection(DBURL,USER,PASSWORD);
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(GET_DATA);
            
            while (resultSet.next()) {
                JSONObject odObj = new JSONObject();
                odObj.put("orderdetailid",resultSet.getInt(1));
                odObj.put("orderid",resultSet.getInt(2));
                odObj.put("itemid",resultSet.getInt(3));
                odObj.put("quantity",resultSet.getInt(4));
                
                odArray.add(odObj);
            }
            
            System.out.println(odArray);
            
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
        return odArray;
    }
}
