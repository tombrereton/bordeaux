package CardGame;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tom on 25/02/17.
 */
public class User {
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private final Date dateRegistered;

    public User() {
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        this.dateRegistered = new Date();
    }

    public User(String userName, String password, String firstName, String lastName) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        this.dateRegistered = new Date();
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        this.dateRegistered = new Date();
    }

    public User(String userName) {
        this.userName = userName;
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        this.dateRegistered = new Date();
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getDateRegistered() {
        return dateRegistered;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateRegistered=" + dateRegistered +
                '}';
    }

    public boolean isUserEmpty() {
        return this.getUserName().equals("") ||
                this.getPassword().equals("") ||
                this.getFirstName().equals("") ||
                this.getLastName().equals("");
    }

    @Override
    public boolean equals(Object o) {
        User user = (User) o;

        return this.getUserName().equals(user.getUserName()) &&
                this.getPassword().equals(user.getPassword()) &&
                this.getFirstName().equals(user.getFirstName()) &&
                this.getLastName().equals(user.getLastName());
    }

    public boolean checkPassword(User user){
        return this.getUserName().equals(user.getUserName()) &&
                this.getPassword().equals(user.getPassword());
    }

    @Override
    public int hashCode() {
        int result = getUserName() != null ? getUserName().hashCode() : 0;
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        result = 31 * result + (getFirstName() != null ? getFirstName().hashCode() : 0);
        result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
        result = 31 * result + (getDateRegistered() != null ? getDateRegistered().hashCode() : 0);
        return result;
    }
}
