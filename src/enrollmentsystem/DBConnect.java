/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enrollmentsystem;

import java.sql.*;
import java.util.Calendar;


/**
 *
 * @author User1
 */
public class DBConnect {
    public Connection con;
    public Statement st;
    public ResultSet rs;  
    
    static String db;
    static String uname;
    static String pswd;
    
   
    
    public DBConnect(){
    try{
        
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/enrollmentsystem?zeroDateTimeBehavior=convertToNull&useSSL=false","root","nX7yKtiLszhEivcD73B4QSCkt");
        st = con.createStatement();  
    }catch (Exception ex) {
      System.out.println("Failed to Connect" + ex);
    }  
    }
    
   
 
}
