/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package information;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The Contact class utilizes a constructor to create a contact object to prepare
 * to add to the contact list.  Setters are utilized to tag the information
 * appropriately in the observable list, and getters are utilized to extract the
 * data when it is needed.
 * 
 * @author myers
 */
public class Contact {
    
    private int contactID;
    private String contactName;
    private String contactEmail;
    
    private static ObservableList<Contact> contactList = FXCollections.observableArrayList();
    
    /**
     * This constructor takes supplied information and make it to a contact object
     * to prepare to add the object to the observable list.
     * 
     * @param id
     * @param name
     * @param email 
     */
    public Contact(int id, String name, String email)
    {
        this.setContactID(id);
        this.setContactName(name);
        this.setContactEmail(email);
    }
 
    /**
     * Sets the contact ID.
     * 
     * @param id 
     */
    public void setContactID(int id)
    {   //Set contact ID
        this.contactID = id;
    }

    /**
     * Sets the contact name.
     * 
     * @param name 
     */
    public void setContactName(String name)
    {   //Set contact name
        this.contactName = name;
    }

    /**
     * Sets the contact email.
     * 
     * @param email 
     */
    public void setContactEmail(String email)
    {   //Set contact email
        this.contactEmail = email;
    }

    /**
     * Gets the contact ID.
     * 
     * @return 
     */
    public int getContactID()
    {   //Retrieve contact ID
        return this.contactID;
    }

    /**
     * Gets the contact name.
     * 
     * @return 
     */
    public String getContactName()
    {   //Retrieve contact name
        return this.contactName;
    }

    /**
     * Gets the contact email.
     * 
     * @return 
     */
    public String getContactEmail()
    {   //Retrieve contact email
        return this.contactEmail;
    }

    /**
     * Adds a contact object to the observable list.
     * 
     * @param contact 
     */
    public static void addContactToList(Contact contact)
    {   //Add contact to list
        contactList.add(contact);
    }

    /**
     * Gets the contact list.
     * 
     * @return 
     */
    public static ObservableList<Contact> getContactList()
    {   //Retrieve contact list
        return contactList;
    }

    /**
     * Converts the hash information to readable text.
     * 
     * @return 
     */
    @Override
    public String toString()
    {
        return (contactID + ": " + contactName);
    }
}
