/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package information;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The Country class utilizes a constructor to create a country object to prepare
 * to add to the country list.  Setters are utilized to tag the information
 * appropriately in the observable list, and getters are utilized to extract the
 * data when it is needed.
 * 
 * @author myers
 */
public class Country {
    
    private int id;
    private String name;

    private static ObservableList<Country> countryList = FXCollections.observableArrayList();
    
    /**
     * This constructor creates a country object to prepare to add to the
     * observable list.
     * 
     * @param id
     * @param name 
     */
    public Country (int id, String name)
    {   //Create new country to add to list
        this.setID(id);
        this.setName(name);
    }

    /**
     * Sets the country ID.
     * 
     * @param id 
     */
    public void setID(int id)
    {   //Set country id
        this.id = id;
    }

    /**
     * Sets the country name.
     * 
     * @param name 
     */
    public void setName(String name)
    {   //Set country name
        this.name = name;
    }

    /**
     * Gets the country ID.
     * 
     * @return 
     */
    public int getID()
    {   //Retrieve country id
        return this.id;
    }
    
    /**
     * Gets the country name.
     * 
     * @return 
     */
    public String getName()
    {   //Retrieve country name
        return this.name;
    }

    /**
     * Adds a country object to the observable list.
     * 
     * @param country 
     */
    public static void addCountryToList(Country country)
    {   //Add country information to list
        countryList.add(country);
    }

    /**
     * Gets the country list.
     * 
     * @return 
     */
    public static ObservableList<Country> getCountryList()
    {   //Retrieve country list
        return countryList;
    }

    /**
     * Converts the hash information to readable text.
     * @return 
     */
    @Override
    public String toString()
    {
        return name;
    }
}
