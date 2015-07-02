package tejaswi_yerukalapudi.com.helloandroid.model;

import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Teja on 7/2/15.
 */
public class Person implements Serializable {
    private String personId;
    private String firstName;
    private String lastName;

    public Person() {
        this.personId = UUID.randomUUID().toString();
    }

    public Person(String lastName, String firstName) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getPersonId() {
        return this.personId;
    }

    public String getFirstName() {
        return  this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return this.lastName + ", " + this.firstName;
    }

    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        out.writeObject(this.firstName);
        out.writeObject(this.lastName);
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        this.firstName = (String) in.readObject();
        this.lastName = (String) in.readObject();
    }
}
