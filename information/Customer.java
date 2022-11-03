/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package information;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The Customer class utilizes a constructor to create a customer object to prepare
 * to add to the customer list.  Setters are utilized to tag the information
 * appropriately in the observable list, and getters are utilized to extract the
 * data when it is needed.
 * @author myers
 */
public class Customer {
    //Create variables to create or retrieve a customer
    private int customerID;
    private String name;
    private String address;
    private String postalCode;
    private String phone;
    private int divisionID;
    private String division;
    
    //Create list to display customer information
    private static ObservableList<Customer> customerList = FXCollections.observableArrayList();
    
    /**
     * This constructor creates a country object to prepare to add the object to
     * the observable list.
     * 
     * @param customerID
     * @param name
     * @param address
     * @param postalCode
     * @param phone
     * @param divisionID
     * @param division 
     */
    public Customer(int customerID, String name, String address,
            String postalCode, String phone, int divisionID, String division)
    {
        this.setCustomerID(customerID);
        this.setName(name);
        this.setAddress(address);
        this.setPostalCode(postalCode);
        this.setPhone(phone);
        this.setDivisionID(divisionID);
        this.setDivision(division);
    }

    /**
     * Sets the customer ID.
     * 
     * @param customerID 
     */
    public void setCustomerID(int customerID)
    {   //Set customer ID
        this.customerID = customerID;
    }

    /**
     * Sets the customer name.
     * 
     * @param name 
     */
    public void setName(String name)
    {   //Set customer name
        this.name = name;
    }

    /**
     * Sets the customer address.
     * 
     * @param address 
     */
    public void setAddress(String address)
    {   //Set customer address
        this.address = address;
    }

    /**
     * Sets the customer postal code.
     * 
     * @param postalCode 
     */
    public void setPostalCode(String postalCode)
    {   //Set customer postal code
        this.postalCode = postalCode;
    }
 
    /**
     * Sets the customer phone.
     * 
     * @param phone 
     */
    public void setPhone(String phone)
    {   //Set customer phone
        this.phone = phone;
    }

    /**
     * Sets the customer division ID.
     * 
     * @param divisionID 
     */
    public void setDivisionID(int divisionID)
    {   //Set customer division ID
        this.divisionID = divisionID;
    }

    /**
     * Sets the customer division.
     * 
     * @param division 
     */
    public void setDivision(String division)
    {   //Set customer division
        this.division = division;
    }

    /**
     * Gets the customer ID.
     * 
     * @return 
     */
    public int getCustomerID()
    {   //Retrieve customer ID
        return customerID;
    }

    /**
     * Gets the customer name.
     * 
     * @return 
     */
    public String getName()
    {   //Retrieve customer name
        return this.name;
    }

    /**
     * Gets the customer address.
     * 
     * @return 
     */
    public String getAddress()
    {   //Retrieve customer address
        return this.address;
    }

    /**
     * Gets the customer postal code.
     * 
     * @return 
     */
    public String getPostalCode()
    {   //Retrieve customer postal code
        return this.postalCode;
    }

    /**
     * Gets the customer phone.
     * 
     * @return 
     */
    public String getPhone()
    {   //Retrive customer phone
        return this.phone;
    }

    /**
     * Gets the customer division ID.
     * 
     * @return 
     */
    public int getDivisonID()
    {   //Retrieve customer division ID
        return this.divisionID;
    }

    /**
     * Gets the customer division.
     * 
     * @return 
     */
    public String getDivision()
    {   //Retrieve customer division
        return this.division;
    }
 
    /**
     * Adds customer object to the observable list.
     * 
     * @param newCustomer 
     */
    public static void addCustomerToList(Customer newCustomer)
    {   //Add customer information to list
        customerList.add(newCustomer);
    }

    /**
     * Gets the customer list.
     * 
     * @return 
     */
    public static ObservableList<Customer> getCustomerList()
    {   //Return customer list to display in table on main page
        return customerList;
    }

    /**
     * Clears the customer list.
     */
    public static void clearCustomerList()
    {   //Remove all objects from customer list
        customerList.clear();
    }

    /**
     * Deletes the customer from the customer list.
     * 
     * @param customer 
     */
    public static void deleteCustomer(Customer customer)
    {   //Delete a specific customer from customer list
        customerList.remove(customer);
    }

    /**
     * Converts the hash information to readable text.
     * 
     * @return 
     */
    @Override
    public String toString()
    {
        return (customerID + ": " + name);
    }
}
