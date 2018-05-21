package ENTITY;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Javier
 */

public class contact implements Serializable {
    private String id;
    private String name;
    private String surname;
    private String street;
    private String phone;
    private Date birthdate;

    public contact() {}

    public contact(String id, String name, String surname, String street, String phone, Date birthdate) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.street = street;
        this.phone = phone;
        this.birthdate = birthdate;
    }

    @Override
    public String toString() {
        return "\nID: " + getId() + "\nName: " + getName() + "\nSurname: " + getSurname() + "\nStreet: " + getStreet() + "\nPhone: " + getPhone() + "\nBirthdate: " + format_date();
    }
    
    public String format_date (){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String birthday_formated = sdf.format(getBirthdate());
        return birthday_formated;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * @param surname the surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * @return the street
     */
    public String getStreet() {
        return street;
    }

    /**
     * @param street the street to set
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the birthdate
     */
    public Date getBirthdate() {
        return birthdate;
    }

    /**
     * @param birthdate the birthdate to set
     */
    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }
}
