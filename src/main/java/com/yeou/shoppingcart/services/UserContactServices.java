/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yeou.shoppingcart.services;

import java.sql.*;
import com.yeou.shoppingcart.model.UserContact;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Yeou Liu (19059124)
 */
public class UserContactServices {

    private static final String DBURL = "jdbc:derby://localhost:1527/sample";
    private static final String USER = "app";
    private static final String PASSWORD = "app";

    private Connection conn = null;
    private Statement stmt = null;
    private PreparedStatement pstmt = null;
    private ResultSet resultSet = null;

    //This method will create the ORDERS Table
    public void createUserContactTable() {
        String CREATE_TABLE = "CREATE TABLE USERCONTACT ("
                + "USERID INT NOT NULL,"
                + "CITY VARCHAR(30) NOT NULL,"
                + "PHONE INT NOT NULL,"
                + "UNIQUE (PHONE),"
                + "FOREIGN KEY (USERID) REFERENCES USERS(USERID),"
                + "PRIMARY KEY (USERID) )";

        try {
            conn = DriverManager.getConnection(DBURL, USER, PASSWORD);
            stmt = conn.createStatement();
            stmt.executeUpdate(CREATE_TABLE);
            System.out.println("USERCONTACT Table Created");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }

    public boolean insertUserContact(UserContact usercontact) {
        boolean status = false;
        String INSERT_DATA = "INSERT INTO USERCONTACT VALUES(?,?,?)";
        try {
            conn = DriverManager.getConnection(DBURL, USER, PASSWORD);
            pstmt = conn.prepareStatement(INSERT_DATA);

            pstmt.setInt(1, usercontact.getUserid());
            pstmt.setString(2, usercontact.getCity());
            pstmt.setInt(3, usercontact.getPhone());

            if (pstmt.executeUpdate() == 1) {
                status = true;
                System.out.println("Row added successfully");
            }
            
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Code: " + e.getErrorCode());
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return status;
    }

    //This method will fetch a single column from the ITEM table using the passed ID
    public UserContact getIndividualUserContacts(int orderid) {
        UserContact usercontact = new UserContact();
        String GET_ORDER = "SELECT * FROM ORDERDETAILS WHERE USERID = ?";
        try {
            conn = DriverManager.getConnection(DBURL, USER, PASSWORD);
            pstmt = conn.prepareStatement(GET_ORDER);
            pstmt.setInt(1, orderid);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                usercontact.setUserid(resultSet.getInt(1));
                usercontact.setCity(resultSet.getString(2));
                usercontact.setPhone(resultSet.getInt(3));
                System.out.println(resultSet.getInt(1) + "\t" + resultSet.getString(2) + "\t" + resultSet.getInt(3));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Code: " + e.getErrorCode());
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return usercontact;
    }

    //This method will fetch all the rows present in the USER table
    public JSONArray getAllUserContacts() {
        JSONArray ucArray = new JSONArray();
        String GET_DATA = "SELECT * FROM USERCONTACT";
        try {
            conn = DriverManager.getConnection(DBURL, USER, PASSWORD);
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(GET_DATA);

            while (resultSet.next()) {
                JSONObject ucObj = new JSONObject();
                ucObj.put("userid",resultSet.getInt(1));
                ucObj.put("city",resultSet.getString(2));
                ucObj.put("phone",resultSet.getInt(3));

                ucArray.add(ucObj);
            }

            System.out.println(ucArray);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Code: " + e.getErrorCode());
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return ucArray;
    }

}
