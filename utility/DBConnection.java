/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import java.sql.*;

/**
 * The DBConnection class establishes and closes a connection to the database.
 * 
 * @author myers
 */
public class DBConnection {
    
    //JDBC URL connection parts
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String serverName = "//wgudb.ucertify.com/WJ079e0";
    
    //JDBC URL
    private static final String jdbcURL = protocol + vendor + serverName;
    
    //Driver and connection interface reference
    private static final String mySQLJDBCDriver = "com.mysql.cj.jdbc.Driver";
    private static Connection conn = null;
    
    //Username for DB access
    private static final String userName = "U079e0";
    
    //Password for DB access
    private static final String password = "53688964288";
    
    /**
     * Creates a connection to the database.
     * 
     * @return 
     */
    public static Connection startConnection()
    {   //Establish a connection to DB and print exception if exists
        try
        {
            Class.forName(mySQLJDBCDriver);
            conn = DriverManager.getConnection(jdbcURL, userName, password);
        }
        catch(ClassNotFoundException e) 
        {
           System.out.println(e.getMessage());
        }
        catch(SQLException e) 
        {
           System.out.println(e.getMessage());
        }
        
        return conn;
    }
    
    /**
     * Closes the connection to the database.
     */
    public static void closeConnection()
    {   //Close the DB connection and print exception if exists
        try
        {
            conn.close();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Returns the database connection to the caller.
     * 
     * @return 
     */
    public static Connection getConnection()
    {   //Send connection to caller
        return conn;
    }
}
