/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package information;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The Division class utilizes a constructor to create a division object to prepare
 * to add to the division list.  Setters are utilized to tag the information
 * appropriately in the observable list, and getters are utilized to extract the
 * data when it is needed.
 * 
 * @author myers
 */
public class Division {
    
    private int divisionID;
    private String division;
    private int countryID;
    
    private static ObservableList<Division> divisionList = FXCollections.observableArrayList();
    
    /**
     * This constructor creates a division object to prepare to add to the
     * observable list.
     * 
     * @param divisionID
     * @param division
     * @param countryID 
     */
    public Division(int divisionID, String division, int countryID)
    {
        this.setDivisionID(divisionID);
        this.setDivision(division);
        this.setCountryID(countryID);
    }

    /**
     * Sets the division ID.
     * 
     * @param divisionID 
     */
    public void setDivisionID(int divisionID)
    {   //Set division ID
        this.divisionID = divisionID;
    }

    /**
     * Sets the division.
     * 
     * @param division 
     */
    public void setDivision(String division)
    {   //Set division
        this.division = division;
    }

    /**
     * Sets the country ID.
     * 
     * @param countryID 
     */
    public void setCountryID(int countryID)
    {
        this.countryID = countryID;
    }

    /**
     * Gets the division ID.
     * 
     * @return 
     */
    public int getDivisionID()
    {   //Retrieve division ID
        return this.divisionID;
    }

    /**
     * Gets the division.
     * 
     * @return 
     */
    public String getDivision()
    {   //Retrieve division
        return this.division;
    }

    /**
     * Gets the country ID.
     * 
     * @return 
     */
    public int getCountryID()
    {   //Retrieve country ID
        return this.countryID;
    }

    /**
     * Adds a division object to the observable list.
     * 
     * @param division 
     */
    public static void addDivisionToList(Division division)
    {   //Add division to list
        divisionList.add(division);
    }

    /**
     * Gets the division list.
     * 
     * @return 
     */
    public static ObservableList<Division> getDivisionList()
    {   //Retrieve division list
        return divisionList;
    }
}
