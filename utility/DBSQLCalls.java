/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import controller.LoginController;
import java.sql.*;

/**
 * The DBSQLCalls class utilizes several methods to add, update, and delete objects
 * from the database.
 * 
 * @author myers
 */
public class DBSQLCalls {
    
    /**
     * Calls the database to add a customer to the database.
     * 
     * @param name
     * @param address
     * @param postalCode
     * @param phone
     * @param divisionID 
     */
    public static void addCustomer(String name, String address,
            String postalCode, String phone, int divisionID)
    {
        var add = "INSERT INTO customers VALUES(default, ?, ?, ?, ?, NOW(), ?, NOW(), ?, ?)";
        
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(add))
        {
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setString(5, LoginController.username);
            ps.setString(6, LoginController.username);
            ps.setInt(7, divisionID);
            
            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Calls the database to update an existing customer in the database.
     * 
     * @param customerID
     * @param name
     * @param address
     * @param postalCode
     * @param phone
     * @param divisionID 
     */
    public static void updateCustomer(int customerID, String name, String address,
            String postalCode, String phone, int divisionID)
    {
        var update = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?,"
                + " Phone = ?, Last_Update = NOW(), Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";
        
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(update))
        {
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setString(5, LoginController.username);
            ps.setInt(6, divisionID);
            ps.setInt(7, customerID);
            
            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Calls the database to delete a customer from the database.
     * 
     * @param customerID 
     */
    public static void deleteCustomer(int customerID)
    {
        var delete = "DELETE FROM customers WHERE Customer_ID = ?";
        
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(delete))
        {
            ps.setInt(1, customerID);
            
            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Calls the database to add an appointment to the database.
     * 
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param customerID
     * @param userID
     * @param contactID 
     */
    public static void addAppointment(String title, String description, String location, String type,
            Timestamp start, Timestamp end, int customerID, int userID, int contactID)
    {
        var add = "INSERT INTO appointments VALUES(default, ?, ?, ?, ?, ?, ?, NOW(), ?, NOW(), ?, ?, ?, ?)";
        
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(add))
        {
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, start);
            ps.setTimestamp(6, end);
            ps.setString(7, LoginController.username);
            ps.setString(8, LoginController.username);
            ps.setInt(9, customerID);
            ps.setInt(10, userID);
            ps.setInt(11, contactID);
            
            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Calls the database to update an existing appointment in the database.
     * 
     * @param apptID
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param customerID
     * @param userID
     * @param contactID 
     */
    public static void updateAppointment(int apptID, String title, String description, String location, 
            String type, Timestamp start, Timestamp end, int customerID, int userID, int contactID)
    {
        var update = "UPDATE appointments SET Title = ?, Description = ?, Location = ?,"
                + "Type = ?, Start = ?, End = ?, Last_Update = NOW(), Last_Updated_By = ?,"
                + "Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(update))
        {
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, start);
            ps.setTimestamp(6, end);
            ps.setString(7, LoginController.username);
            ps.setInt(8, customerID);
            ps.setInt(9, userID);
            ps.setInt(10, contactID);
            ps.setInt(11, apptID);
            
            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Calls the database to delete an appointment from the database.
     * 
     * @param apptID 
     */
    public static void deleteAppointment(int apptID)
    {
        var delete = "DELETE FROM appointments WHERE Appointment_ID = ?";
        
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(delete))
        {
            ps.setInt(1, apptID);
            
            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
}