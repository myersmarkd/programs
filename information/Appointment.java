/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package information;

import controller.CustomerMenuController;
import controller.LoginController;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The Appointment class utilizes a constructor to add an appointment to the
 * observable list.  Setters are utilized to tag the information
 * appropriately in the observable list, and getters are utilized to extract the 
 * data when it is needed.  There are methods to return the appointment list, as 
 * well as checking for timing overlaps.
 * 
 * @author myers
 */
public class Appointment {
    
    private int apptID;
    private String title;
    private String description;
    private String location;
    private String type;
    private Timestamp startDateTime;
    private Timestamp endDateTime;
    private int customerID;
    private int userID;
    private int contactID;
    
    private static ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
    private static ObservableList<Appointment> apptListByUser = FXCollections.observableArrayList();
    private static ObservableList<Appointment> apptListByCustomer = FXCollections.observableArrayList();
    
    /**
     * The constructor creates a new appointment object to prepare to add to the
     * observable list.
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
    public Appointment(int apptID, String title, String description, String location, String type,
            Timestamp start, Timestamp end, int customerID, int userID, int contactID)
    {
        this.setApptID(apptID);
        this.setTitle(title);
        this.setDescription(description);
        this.setLocation(location);
        this.setType(type);
        this.setStartDateTime(start);
        this.setEndDateTime(end);
        this.setCustomerID(customerID);
        this.setUserID(userID);
        this.setContactID(contactID);
    }

    /**
     * Sets the appointment ID.
     * 
     * @param apptID 
     */
    public void setApptID(int apptID)
    {
        this.apptID = apptID;
    }
    
    /**
     * Sets the appointment title.
     * 
     * @param title 
     */
    public void setTitle(String title)
    {
        this.title = title;
    }
 
    /**
     * Sets the appointment description.
     * 
     * @param description 
     */
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    /**
     * Sets the appointment location.
     * 
     * @param location 
     */
    public void setLocation(String location)
    {
        this.location = location;
    }
    
    /**
     * Sets the appointment type.
     * 
     * @param type 
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Sets the appointment start date and time.
     * 
     * @param start 
     */
    public void setStartDateTime(Timestamp start)
    {
        this.startDateTime = start;
    }
 
    /**
     * Sets the appointment end date and time.
     * @param end 
     */
    public void setEndDateTime(Timestamp end)
    {
        this.endDateTime = end;
    }

    /**
     * Sets the appointment's customer ID.
     * 
     * @param customerID 
     */
    public void setCustomerID(int customerID)
    {
        this.customerID = customerID;
    }

    /**
     * Sets the appointment's user ID.
     * 
     * @param userID 
     */
    public void setUserID(int userID)
    {
        this.userID = userID;
    }

    /**
     * Sets the appointment's contact ID.
     * 
     * @param contactID 
     */
    public void setContactID(int contactID)
    {
        this.contactID = contactID;
    }

    /**
     * Gets the appointment ID.
     * 
     * @return 
     */
    public int getApptID()
    {
        return apptID;
    }

    /**
     * Gets the appointment title.
     * 
     * @return 
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * Gets the appointment description.
     * 
     * @return 
     */
    public String getDescription()
    {
        return description;
    }
 
    /**
     * Gets the appointment location.
     * 
     * @return 
     */
    public String getLocation()
    {
        return location;
    }

    /**
     * Gets the appointment type.
     * 
     * @return 
     */
    public String getType()
    {
        return type;
    }

    /**
     * Gets the appointment start date and time.
     * 
     * @return 
     */
    public Timestamp getStartDateTime()
    {
       return startDateTime;
    }

    /**
     * Gets the appointment end date and time.
     * 
     * @return 
     */
    public Timestamp getEndDateTime()
    {
        return endDateTime;
    }

    /**
     * Gets the appointment's customer ID.
     * 
     * @return 
     */
    public int getCustomerID()
    {
        return customerID;
    }

    /**
     * Gets the appointment's user ID.
     * 
     * @return 
     */
    public int getUserID()
    {
        return userID;
    }

    /**
     * Gets the appointment's contact ID.
     * 
     * @return 
     */
    public int getContactID()
    {
        return contactID;
    }

    /**
     * Adds an appointment to the observable list.
     * @param appointment 
     */
    public static void addApptToList(Appointment appointment)
    {
        appointmentList.add(appointment);
    }

    /**
     * Gets the appointment list.
     * @return 
     */
    public static ObservableList<Appointment> getApptList()
    {
        return appointmentList;
    }
    
    /**
     * Clears the appointment list.
     */
    public static void clearApptList()
    {
        appointmentList.clear();
    }

    /**
     * Deletes the provided appointment and updates an appointment list by user.
     * 
     * @param appointment 
     */
    public static void deleteAppointment(Appointment appointment)
    {
        appointmentList.remove(appointment);
        getApptListByUser();
    }

    /**
     * Gets an appointment list assigned to the logged in user.
     * 
     * @return 
     */
    public static ObservableList<Appointment> getApptListByUser()
    {
        apptListByUser.clear();
        
        for (Appointment appt : appointmentList)
        {   
            if (appt.getUserID() == LoginController.userid)
            {
                apptListByUser.add(appt);
            }
        }
        
        return apptListByUser;
    }
 
    /**
     * Gets an appointment list by customers.
     * 
     * @return 
     */
    public static ObservableList<Appointment> getApptListByCustomer()
    {
        apptListByCustomer.clear();
        
        for (Appointment appt : appointmentList)
        {
            if (appt.getCustomerID() == CustomerMenuController.getSelectedCustomer().getCustomerID())
            {
                apptListByCustomer.add(appt);
            }
        }
        
        return apptListByCustomer;
    }
    
    /**
     * This method takes local dates and local times and converts them to timestamps.
     * It then compares to existing appointments to see if the proposed times
     * overlap appointments for the customer.
     * 
     * @param startDate
     * @param startTime
     * @param endDate
     * @param endTime
     * @param customerID
     * @return 
     */
    public static boolean checkOverLap(LocalDate startDate, LocalTime startTime,
            LocalDate endDate, LocalTime endTime, int customerID)
    {
        LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
        Timestamp start = Timestamp.valueOf(startDateTime);
        LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);
        Timestamp end = Timestamp.valueOf(endDateTime);
        
        for (Appointment appointment : appointmentList)
        {
            if (appointment.getCustomerID() == customerID)
            {
                if ((appointment.getStartDateTime().before(end) && appointment.getStartDateTime().after(start)) ||
                        (appointment.getEndDateTime().after(start) && appointment.getEndDateTime().before(end)) ||
                        (appointment.getStartDateTime().before(start) && appointment.getEndDateTime().after(end)) ||
                        (appointment.getStartDateTime().after(start) && appointment.getEndDateTime().before(end)) ||
                        (appointment.getStartDateTime().equals(start) && appointment.getEndDateTime().equals(end)))
                {
                    return true;
                }
            }
        }
        
        return false;
    }
}