package CardGame;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Yifan Wu
 * @version 2017-02-26
 */
public class FunctionDB {
    private Connection con = null;  
    private static final String DRIVER = "org.postgresql.Driver";  
    private static final String URL = "jdbc:postgresql://localhost:5432/cardgame";  
    private static final String USER = "root";  
    private static final String PASS = "test";  
    private PreparedStatement stat = null;  
    
    public FunctionDB(){  
		try {
			Class.forName(DRIVER);
			try {
				con = DriverManager.getConnection(URL, USER, PASS);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
        
    }  
    
    public Connection getConnection()throws Exception{  
        return con;  
    }  
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
	public boolean isUserRegistered(String username) throws SQLException{
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
    
}
