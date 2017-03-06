package CardGame;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import readfile.ReadFile;

/**
 * @author Yifan Wu
 * @author Lois Holman
 * 
 * @version 2017-02-26
 * @version 2017-03-05
 * 
 * In class FunctionDB we have all the methods relating to database use. 
 * Method createTables() only needs to be called once ever and creates the database and 
 * inital table structure. 
 * The other methods are called based on user interaction with the GUI interface. 
 * At login a user is checked against the database using method doesUserExist().
 * 
 * 
 * 
 * 
 */



public class FunctionDB {
    private Connection con = null; //why not private static?  
    private static final String DRIVER = "org.postgresql.Driver";  
    private static final String URL = "jdbc:postgresql://localhost:5432/cardgame";  
    private static final String USER = "lgh312"; //"root";  
    private static final String PASS = "help";//"test";  
    private PreparedStatement stat = null;  
    
    
    //connecting to DB
    public FunctionDB(){  
		try {
			Class.forName(DRIVER);
			try {
				
				//temporarily using local DB for testing
				con = DriverManager.getConnection("jdbc:postgresql:bordeauxDB");
				//con = DriverManager.getConnection(URL, USER, PASS);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}   
		
		if (con != null) {
			System.out.println("Database accessed!");
		} else {
			System.out.println("Failed to make connection");
		}
    }  
    
    public Connection getConnection()throws Exception{  
        return con;  
      
    }  
    
    //method to run once, creating DB and tables   
    
    public void createTables() throws SQLException { // does this want to be static?
		Statement stmt = con.createStatement();
		
		stmt.executeUpdate("CREATE TABLE Artists ("
				+ "artistID VARCHAR(64) UNIQUE NOT NULL,"
				+ "artistName CHAR(60) NOT NULL,"
				+ "CONSTRAINT artistKey PRIMARY KEY(artistID));");
    }
    /*
     * 		try {
		createTables();
		ReadFile.readToDB(conn);
		conn.close();
	} catch (SQLException e) {
		e.printStackTrace();
     * 
     */
    
    
    
    
    /**
     * 
     * A method named free to close connections
     * @param rs
     * @param sta
     * @param con
     */
    public static void free(Statement sta , Connection con)  
    {  
    	try{
            if(null != sta)  
            {  
                sta.close();  
                sta = null ;  
            }  
              
            if(null != con)  
            {  
                con.close();  
                con = null ;  
            }  
            
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }

    /**
     * 
     * A method named free to close connections
     * @param rs
     * @param sta
     * @param con
     */
    public static void free(ResultSet rs, Statement sta , Connection con)  
    {  
        try {  
            if(null != rs)  
            {  
                rs.close();  
                rs = null ;  
            }  
              
            if(null != sta)  
            {  
                sta.close();  
                sta = null ;  
            }  
              
            if(null != con)  
            {  
                con.close();  
                con = null ;  
            }  
            
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }
    
    // I DONT UNDERSTAND HOW THIS ONE WORKS
    public boolean insertUserIntoDatabase(User user) throws SQLException{  
        String sql = "INSERT INTO users(username,password,first_name,last_name,data_registered) VALUES(?,?,?,?,?)";  
        stat = con.prepareStatement(sql);  
        stat.setString(1,user.getUserName());  
        stat.setString(2,user.getPassword());  
        stat.setString(3,user.getFirstName());  
        stat.setString(4,user.getLastName());  
        stat.setDate(5,(Date) user.getDateRegistered());  
        int update = stat.executeUpdate();  
        free(stat,con);
        if(update>0){  
            return true;  
        }  
        else{  
            return false;  
        } 
        
    }  
    
    
    public User retrieveUserFromDatabase(String username) throws SQLException{
        String sql = "SELECT username,password,first_name,last_name,data_registered FROM user WHERE username =?";  
        stat = con.prepareStatement(sql);  
        ResultSet rs = stat.executeQuery();  
        User user = null;  
        while(rs.next()){  
        	user.setUserName(rs.getString(1));
        	user.setPassword(rs.getString(2));
        	user.setFirstName(rs.getString(3));
        	user.setLastName(rs.getString(4));
        }  
        free(rs,stat,con);
        return user;  
    	
    }
    
	public User retrieveUserFromDatabase(int id) throws SQLException{
        String sql = "SELECT username,password,first_name,last_name,data_registered FROM user WHERE id =?";  
        stat = con.prepareStatement(sql);  
        ResultSet rs = stat.executeQuery();  
        User user = null;  
        while(rs.next()){  
        	user.setUserName(rs.getString(1));
        	user.setPassword(rs.getString(2));
        	user.setFirstName(rs.getString(3));
        	user.setLastName(rs.getString(4));
        }  
        free(rs,stat,con);

        return user;  
    	
    }
	
	// check to see if the user is in database when they login
	// is this statement doing what i want it to do?
	
	public boolean doesUserExist(String username) throws SQLException{
        String sql = "SELECT username FROM user WHERE user =?";  
        stat = con.prepareStatement(sql);  
        ResultSet rs = stat.executeQuery();  
        if(rs.next() == false){
        	free(rs,stat,con);
        	return false;
        }else{
        	free(rs,stat,con);
        	return true;
        }
    }
	
	
	public static void main(String[] args){
	}
	
    
}
