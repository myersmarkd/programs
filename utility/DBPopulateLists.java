/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import information.Appointment;
import information.Contact;
import information.Country;
import information.Customer;
import information.Division;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * The DBPopulateLists class utilizes different methods to populate observable
 * lists.
 * 
 * @author myers
 */
public class DBPopulateLists {
    
    /**
     * Calls the database to populate the appointment list.
     */
    public static void populateAppointmentList()
    {
        try (var ps = DBConnection.getConnection().prepareStatement("SELECT * FROM appointments"))
        {
            var apptSet = ps.executeQuery();
            
            while(apptSet.next())
            {
                int apptID = apptSet.getInt("Appointment_ID");
                String title = apptSet.getString("Title");
                String description = apptSet.getString("Description");
                String location = apptSet.getString("Location");
                String type = apptSet.getString("type");
                Timestamp start = apptSet.getTimestamp("Start");
                Timestamp end = apptSet.getTimestamp("End");
                int customerID = apptSet.getInt("Customer_ID");
                int userID = apptSet.getInt("User_ID");
                int contactID = apptSet.getInt("Contact_ID");
                
                Appointment appointment = new Appointment(apptID, title, description, location,
                        type, start, end, customerID, userID, contactID);
                
                Appointment.addApptToList(appointment);
                
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Calls the database to populate the contact list.
     */
    public static void populateContactList()
    {
            try (var ps = DBConnection.getConnection().prepareStatement("SELECT * FROM contacts"))
            {
                var contactSet = ps.executeQuery();

                while (contactSet.next())
                {
                    int id = contactSet.getInt("Contact_ID");
                    String name = contactSet.getString("Contact_Name");
                    String email = contactSet.getString("Email");

                    Contact contact = new Contact(id, name, email);

                    Contact.addContactToList(contact);
                }
            }
            catch (SQLException e)
            {
                System.out.println(e.getMessage());
            }
    }
    
    /**
     * Calls the database to populate the country list.
     */
    public static void populateCountryList()
    {
        try (var countries = DBConnection.getConnection().prepareStatement("SELECT * FROM countries"))
        {   //Assign data from DB to result set
            var countrySet = countries.executeQuery();
            //Loop through set to utilize data        
            while (countrySet.next())
            {
                //Assign data to variables
                String countryName = countrySet.getString("Country");
                int countryID = countrySet.getInt("Country_ID");

                //Call constructor for Country and add to observable list
                Country country = new Country(countryID, countryName);
                Country.addCountryToList(country);
            }
        }
        catch (SQLException e)
        {   //Print exception should one occur
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Calls the database to populate the customer list.
     */
    public static void populateCustomerList()
    {
        //Variable ps to query customers table.  Varible ps2 to query first_level_divisions table.
        try (var ps = DBConnection.getConnection().prepareStatement("SELECT * FROM customers");
            var ps2 = DBConnection.getConnection().prepareStatement("SELECT * FROM first_level_divisions", 
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE))
        {   //Execute queries
            var customerSet = ps.executeQuery();
            var divisionSet = ps2.executeQuery();

            //Iterate through results from customers table
            while (customerSet.next())
            {   //Take information from customers table and assign to variables
                int customerID = customerSet.getInt("Customer_ID");
                String customerName = customerSet.getString("Customer_Name");
                String customerAddress = customerSet.getString("Address");
                String customerPostalCode = customerSet.getString("Postal_Code");
                String customerPhone = customerSet.getString("Phone");
                int customerDivisionID = customerSet.getInt("Division_ID");

                //Iterate through first_level_divisions table
                while (divisionSet.next())
                {
                    int customerDivision = divisionSet.getInt("Division_ID");
                    //Compare if division codes match per table
                    if (customerDivisionID == customerDivision)
                    {
                        String division = divisionSet.getString("Division");
                        //Call constructor to create customer in observable list
                        Customer customer = new Customer(customerID, customerName, customerAddress,
                            customerPostalCode, customerPhone, customerDivisionID, division);
                        //Add customer information to observable list
                        Customer.addCustomerToList(customer);

                        //Restart the first_level_divisions results to check for more matches
                        divisionSet.beforeFirst();
                        //Exit nested loop
                        break;
                    }
                }
            }
            //Check to see if division list is empty to avoid duplicates
            if (Division.getDivisionList().isEmpty())
            {   //Restart division set from the beginning
                divisionSet.beforeFirst();
                //Iterate through the division set
                while (divisionSet.next())
                {   //Retrieve information from the division set
                    int divisionID = divisionSet.getInt("Division_ID");
                    String division = divisionSet.getString("Division");
                    int countryID = divisionSet.getInt("COUNTRY_ID");

                    //Add the information calling the constructor and adding to list
                    Division newDivision = new Division(divisionID, division, countryID);
                    Division.addDivisionToList(newDivision);
                }
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    
}
