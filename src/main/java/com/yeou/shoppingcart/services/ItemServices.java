/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yeou.shoppingcart.services;

import com.yeou.shoppingcart.model.Item;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Yeou Liu (19059124)
 */
public class ItemServices {
    
    private static final String DBURL = "jdbc:derby://localhost:1527/sample";
    private static final String USER = "app";
    private static final String PASSWORD = "app";
    
    private Connection conn = null;
    private Statement stmt = null;
    private PreparedStatement pstmt = null;
    private ResultSet resultSet = null;
    
    //This method will create the Item Table
    public void createItemTable(){
        String CREATE_TABLE = "CREATE TABLE ITEM ("
                    + "ITEMID INT GENERATED ALWAYS AS IDENTITY (START WITH 500, INCREMENT BY 1),"
                    + "ITEMNAME VARCHAR(30) NOT NULL,"
                    + "PRICE REAL NOT NULL,"
                    + "PRIMARY KEY (ITEMID))";
        
        
         try{
            conn = DriverManager.getConnection(DBURL,USER,PASSWORD);
            stmt = conn.createStatement();
            stmt.executeUpdate(CREATE_TABLE);
            System.out.println("ITEM Table Created");
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
    
    public boolean insertItems(Item item){
        boolean status = false;
        String INSERT_DATA = "INSERT INTO ITEM(ITEMNAME, PRICE) VALUES(?,?)";
        try{
            conn = DriverManager.getConnection(DBURL,USER,PASSWORD);
            pstmt = conn.prepareStatement(INSERT_DATA);
            
            pstmt.setString(1, item.getItemname());
            pstmt.setFloat(2, item.getPrice());
            // execute statement
            if(pstmt.executeUpdate() == 1){
                status = true;
                System.out.println("Item Name : " + item.getItemname()
                        +" and Price: " + item.getPrice()+" have been added to the table");
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
    }   return status;
    }
    
    //This method will fetch a single column from the ITEM table using the passed ID
    public Item getIndividualItem(int id) {
        Item item = new Item();
        String GET_ITEM= "SELECT * FROM ITEM WHERE ITEMID = ?";
        try{
            conn = DriverManager.getConnection(DBURL,USER,PASSWORD);
            pstmt = conn.prepareStatement(GET_ITEM);
            pstmt.setInt(1, id);
            resultSet = pstmt.executeQuery();
            
            while(resultSet.next()) {
                
                item.setItemid(resultSet.getInt(1));
                item.setItemname(resultSet.getString(2));
                item.setPrice(resultSet.getFloat(3));
                
                System.out.println(resultSet.getInt(1) + "\t" + 
                        resultSet.getString(2) + "\t" + 
                        resultSet.getFloat(3));
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
        return item;
    }
    
     //This method will fetch all the rows present in the USER table
   public JSONArray getAllItems(){
        JSONArray itemArray = new JSONArray();
        String GET_DATA = "SELECT * FROM ITEM";
        try{
            conn = DriverManager.getConnection(DBURL,USER,PASSWORD);
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(GET_DATA);
            
            while (resultSet.next()) {
                
                JSONObject itemObject = new JSONObject();
                itemObject.put("itemid",resultSet.getInt(1));
                itemObject.put("itemname",resultSet.getString(2));
                itemObject.put("price",resultSet.getFloat(3));
                itemArray.add(itemObject);
            }
            
            System.out.println(itemArray);
            
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
        return itemArray;
    }
    
    
}
