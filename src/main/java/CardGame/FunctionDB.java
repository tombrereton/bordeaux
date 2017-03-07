package CardGame;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The DAO of the card game
 * @author Yifan Wu
 * @version 2017-02-26
 */
public class FunctionDB {
    private Connection con = null;  
    private static final String DRIVER = "org.postgresql.Driver";  
    private static final String URL = "jdbc:postgresql://mod-fund-databases.cs.bham.ac.uk/tlb646";
    private static final String USER = "tlb646";
    private static final String PASS = "m4lvc9jdq9";
    private PreparedStatement stat = null;  
    /**
     * The constructor of FunctionDB
     */
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
        if(rs.next()){  
        	user.setUserName(rs.getString(1));
        	user.setPassword(rs.getString(2));
        	user.setFirstName(rs.getString(3));
        	user.setLastName(rs.getString(4));
        }  
        free(rs,stat,con);
        return user;  
    	
    }
    
	public User retrieveUserFromDatabase(int userid) throws SQLException{
        String sql = "SELECT username,password,first_name,last_name,data_registered FROM user WHERE user_id =?";  
        stat = con.prepareStatement(sql);  
        stat.setInt(1,userid);
        ResultSet rs = stat.executeQuery();  
        User user = null;  
        if(rs.next()){  
        	user.setUserName(rs.getString(1));
        	user.setPassword(rs.getString(2));
        	user.setFirstName(rs.getString(3));
        	user.setLastName(rs.getString(4));
        }  
        free(rs,stat,con);

        return user;  
    }
	public boolean isUserRegistered(String username) throws SQLException{
        String sql = "SELECT username FROM user WHERE username =?";  
        stat = con.prepareStatement(sql);  
        stat.setString(1,username);
        ResultSet rs = stat.executeQuery();  
        if(rs.next() == false){
        	free(rs,stat,con);
        	return false;
        }else{
        	free(rs,stat,con);
        	return true;
        }
    }
	
	public boolean insertNewGameIntoDatabase(int timelength,int totalpot) throws SQLException{  
        String sql = "INSERT INTO games(time_length,total_wining_pot) VALUES(?,?)";  
        stat = con.prepareStatement(sql);  
        stat.setInt(1,timelength);  
        stat.setInt(2,totalpot);  
        int update = stat.executeUpdate();  
        free(stat,con);
        if(update>0){  
            return true;  
        }  
        else{  
            return false;  
        } 
	}
	public int insertGameOutcomeIntoDatabse(String outcomes) throws SQLException{  
		String sql = "INSERT INTO game_outcomes(outcome) SELECT ? WHERE NOT EXISTS (SELECT 1 FROM game_outcomes WHERE outcome=?) RETURNING outcome_id";
		stat = con.prepareStatement(sql);
		stat.setString(1, outcomes);
		stat.setString(2, outcomes);
		ResultSet rs = stat.executeQuery();
		if (rs.next()) {
			int result = rs.getInt(1);
			rs.close();
			return result;
		}
        free(stat,con);
        // if any else happen throw a wrong result
		return -1;
	}
	public boolean updateGameOutcomeFromDatabse(int outcome_id, String outcomes) throws SQLException{  
		String sql = "UPDATE game_outcomes SET outcomes = (?) WHERE outcoms_id = (?)";
		stat = con.prepareStatement(sql);
		stat.setInt(1, outcome_id);
		stat.setString(2, outcomes);
		ResultSet rs = stat.executeQuery();
		free(stat,con);
        int update = stat.executeUpdate();  
        if(update>0){  
            return true;  
        }  
        else{  
            return false;  
        } 
	}
    public boolean insertGameStatsIntoDatabase(int gameid,int userid,int chip_amount_changed) throws SQLException{  
    	// initialise the outcome result
    	int outcomeid = insertGameOutcomeIntoDatabse("");
        String sql = "INSERT INTO users_games(user_id,game_id,outcom_id,chip_amount_changed) VALUES(?,?,?,?)";  
        stat = con.prepareStatement(sql);
        stat.setInt(1, userid);
        stat.setInt(2, gameid);
        stat.setInt(3, outcomeid);
        stat.setInt(4, chip_amount_changed);
        int update = stat.executeUpdate();  
        free(stat,con);
        if(update>0){  
            return true;  
        }  
        else{  
            return false;  
        } 
    }
    

}
