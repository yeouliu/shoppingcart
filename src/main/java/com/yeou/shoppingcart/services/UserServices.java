/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yeou.shoppingcart.services;

import com.yeou.shoppingcart.model.Users;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
/**
 *
 * @author Yeou Liu (19059124)
 */
public class UserServices {
    
    private static final String DBURL = "jdbc:derby://localhost:1527/sample";
    private static final String USER = "app";
    private static final String PASSWORD = "app";
    
    private Connection conn = null; 
    private Statement stmt = null;
    private PreparedStatement pstmt = null;
    private ResultSet resultSet = null;
    
    //This method will create the table USERS
    public void createUserTable(){
        String CREATE_TABLE = "CREATE TABLE USERS ("
                    + "USERID INT GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                    + "USERNAME VARCHAR(40) NOT NULL,"
                    + "DOB DATE NOT NULL,"
                    + "PRIMARY KEY (USERID))";
        
        
         try{
            conn = DriverManager.getConnection(DBURL,USER,PASSWORD);
            stmt = conn.createStatement();
            stmt.executeUpdate(CREATE_TABLE);
            System.out.println("USERS Table Created");
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
    
    //This method will insert values into the USER table
    public boolean insertUsers(Users user){
        //Users userObj = new Users();
        boolean status = false;
        String INSERT_DATA = "INSERT INTO USERS(USERNAME, DOB) VALUES(?,?)";
        
        try{
            conn = DriverManager.getConnection(DBURL,USER,PASSWORD);
            pstmt = conn.prepareStatement(INSERT_DATA);
            //java.sql.Date dobsql = new java.sql.Date(user.getDob().getTime());
            pstmt.setString(1, user.getUsername());
            pstmt.setDate(2, user.getDob());
            
            if(pstmt.executeUpdate() == 1){
                status = true;
                System.out.println("Username : " + user.getUsername()
                        +" and Date of Birth: " + user.getDob() +" have been added to the table");
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println("Code: " +e.getErrorCode());
        }finally{
            try{
            if(pstmt != null)
                stmt.close();
            if(conn != null)
                conn.close();
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
    }
        return status;
    }
    
    //This method will fetch a single column from the USER table using the passed ID
    public Users getIndividualUser(int id) {
        Users user = new Users();
        String GET_USER= "SELECT * FROM USERS WHERE USERID = ?";
        try{
            conn = DriverManager.getConnection(DBURL,USER,PASSWORD);
            pstmt = conn.prepareStatement(GET_USER);
            pstmt.setInt(1, id);
            resultSet = pstmt.executeQuery();
            
            while(resultSet.next()) {
                
                user.setUserid(resultSet.getInt(1));
                user.setUsername(resultSet.getString(2));
                user.setDob(resultSet.getDate(3));
                
                System.out.println(resultSet.getInt(1) + "\t" + 
                        resultSet.getString(2) + "\t" + 
                        resultSet.getDate(3));
            }
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println("Code: " +e.getErrorCode());
        }finally{
            try{
            if(pstmt != null)
                stmt.close();
            if(conn != null)
                conn.close();
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        return user;
    }
    
    //This method will fetch all the rows present in the USER table
    public JSONArray getAllUsers(){
        JSONArray userArray = new JSONArray();
        String GET_DATA = "SELECT * FROM USERS";
        try{
            conn = DriverManager.getConnection(DBURL,USER,PASSWORD);
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(GET_DATA);
            
            while (resultSet.next()) {
                JSONObject userObject = new JSONObject();
                userObject.put("userid",resultSet.getInt(1));
                userObject.put("username",resultSet.getString(2));
                userObject.put("dob",resultSet.getDate(3).toString());
                userArray.add(userObject);
            }
            
            System.out.println(userArray);
            
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
        return userArray;
    }
    
// End of userservices    
}