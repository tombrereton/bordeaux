package CardGame;


import java.util.Random;

/**
 * Created by tom on 25/02/17.
 * updated by lois 11/04/17
 * updates by Alex 15/03/2017
 */
public class User {
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String avatarID;



    public User(String userName, String password, String firstName, String lastName, String emailAddress) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        Random r = new Random();
        this.avatarID = Integer.toString(r.nextInt(39)+2);
    }

    public User(String userName, String password, String firstName, String lastName) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        Random r = new Random();
        this.avatarID = Integer.toString(r.nextInt(39)+2);
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
        Random r = new Random();
        this.avatarID = Integer.toString(r.nextInt(39)+2);
    }

    public User(String userName) {
        this.userName = userName;
    }

    public User(){
        Random r = new Random();
        this.avatarID = Integer.toString(r.nextInt(39)+2);
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
    
    public String getEmailAddress() {
        return emailAddress;
    }

    public String getAvatarID() {
        return avatarID;
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
    
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setAvatarID(String avatarID) {
        this.avatarID = avatarID;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                '}';
    }

    public boolean isEmpty() {
        return this.getUserName().equals("") ||
                this.getPassword().equals("") ||
                this.getFirstName().equals("") ||
                this.getLastName().equals("");
        
    }

    public boolean isUserNull() {
        return this.getUserName() == null || this.getPassword() == null ||
                this.getFirstName() == null || this.getLastName() == null;
    }


    @Override
    public boolean equals(Object o) {
        User user = (User) o;

        return this.getUserName().equals(user.getUserName()) &&
                this.getPassword().equals(user.getPassword());
    }

    public boolean checkPassword(User user) {
        return this.getUserName().equals(user.getUserName()) &&
                this.getPassword().equals(user.getPassword());
    }

    @Override
    public int hashCode() {
        int result = getUserName() != null ? getUserName().hashCode() : 0;
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        result = 31 * result + (getFirstName() != null ? getFirstName().hashCode() : 0);
        result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
        result = 31 * result + (getEmailAddress() != null ? getEmailAddress().hashCode() : 0);
        return result;
    }
}
