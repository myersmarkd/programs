/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package information;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The User class utilizes a constructor to create a user object to prepare
 * to add to the user list.  Setters are utilized to tag the information
 * appropriately in the observable list, and getters are utilized to extract the
 * data when it is needed.
 * @author myers
 */
public class User {
    
    private int userID;
    private String userName;
    
    private static ObservableList<User> userList = FXCollections.observableArrayList();
    
    /**
     * This constructor creates a user object to prepare to add the object to the
     * observable list.
     * @param userID
     * @param userName 
     */
    public User(int userID, String userName)
    {
        this.setUserID(userID);
        this.setUserName(userName);
    }

    /**
     * Sets the user ID.
     * 
     * @param userID 
     */
    public void setUserID(int userID)
    {
        this.userID = userID;
    }

    /**
     * Sets the user name.
     * 
     * @param userName 
     */
    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    /**
     * Gets the user ID.
     * 
     * @return 
     */
    public int getUserID()
    {
        return this.userID;
    }
 
    /**
     * Gets the user name.
     * 
     * @return 
     */
    public String getUserName()
    {
        return this.userName;
    }

    /**
     * Adds a user object to the observable list.
     * 
     * @param user 
     */
    public static void addUserToList(User user)
    {
        userList.add(user);
    }
 
    /**
     * Gets the user list.
     * 
     * @return 
     */
    public static ObservableList<User> getUserList()
    {
        return userList;
    }

    /**
     * Converts the hash information to readable text.
     * 
     * @return 
     */
    @Override
    public String toString()
    {
        return (userID + ": " + userName);
    }
}
