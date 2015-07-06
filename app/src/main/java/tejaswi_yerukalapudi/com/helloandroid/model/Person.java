package tejaswi_yerukalapudi.com.helloandroid.model;

import android.os.Parcelable;

import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Teja on 7/2/15.
 */
public class Person implements Serializable {

    private static final long serialVersionUID = 0L;

    private String CustomerID;
    private String CompanyName;
    private String ContactName;
    private String ContactTitle;
    private String Address;
    private String City;
    private String Region;
    private String PostalCode;
    private String Country;
    private String Phone;
    private String Fax;
    private String Salary;
    private String DOFB;
    private String ANumber;
    private String ADecimal;

    public String getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(String customerID) {
        CustomerID = customerID;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getContactName() {
        return ContactName;
    }

    public void setContactName(String contactName) {
        ContactName = contactName;
    }

    public String getContactTitle() {
        return ContactTitle;
    }

    public void setContactTitle(String contactTitle) {
        ContactTitle = contactTitle;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getRegion() {
        return Region;
    }

    public void setRegion(String region) {
        Region = region;
    }

    public String getPostalCode() {
        return PostalCode;
    }

    public void setPostalCode(String postalCode) {
        PostalCode = postalCode;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getFax() {
        return Fax;
    }

    public void setFax(String fax) {
        Fax = fax;
    }

    public String getSalary() {
        return Salary;
    }

    public void setSalary(String salary) {
        Salary = salary;
    }

    public String getDOFB() {
        return DOFB;
    }

    public void setDOFB(String DOFB) {
        this.DOFB = DOFB;
    }

    public String getANumber() {
        return ANumber;
    }

    public void setANumber(String ANumber) {
        this.ANumber = ANumber;
    }

    public String getADecimal() {
        return ADecimal;
    }

    public void setADecimal(String ADecimal) {
        this.ADecimal = ADecimal;
    }

    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        out.writeObject(this.ADecimal);
        out.writeObject(this.Address);
        out.writeObject(this.ANumber);
        out.writeObject(this.City);
        out.writeObject(this.CompanyName);
        out.writeObject(this.ContactName);
        out.writeObject(this.ContactTitle);
        out.writeObject(this.Country);
        out.writeObject(this.CustomerID);
        out.writeObject(this.DOFB);
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        this.ADecimal = (String) in.readObject();
        this.Address = (String) in.readObject();
        this.ANumber = (String) in.readObject();
        this.City = (String) in.readObject();
        this.CompanyName = (String) in.readObject();
        this.ContactName = (String) in.readObject();
        this.ContactTitle = (String) in.readObject();
        this.Country = (String) in.readObject();
        this.CustomerID = (String) in.readObject();
        this.DOFB = (String) in.readObject();
    }
}
