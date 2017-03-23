package CardGame;

import java.sql.*;

/**
 * CREATE TABLE users (
 * userID SERIAL,
 * username varchar(50) UNIQUE NOT NULL,
 * password varchar(64) NOT NULL,
 * firstname varchar(50) NOT NULL,
 * lastname varchar(50) NOT NULL,
 * CONSTRAINT users_key PRIMARY KEY (userID)
 * );
 * <p>
 * CREATE TABLE users_games (
 * user_game_ID SERIAL NOT NULL,
 * userID INTEGER NOT NULL,
 * gameID INTEGER NOT NULL,
 * outcomeID INTEGER NOT NULL,
 * chip_amount_changed INTEGER NOT NULL,
 * CONSTRAINT users_games_key PRIMARY KEY (user_game_ID, userID, gameID),
 * FOREIGN KEY (userID) REFERENCES users(userID),
 * FOREIGN KEY (outcomeID) REFERENCES game_outcomes(outcomeID)
 * );
 * <p>
 * CREATE TABLE game_outcomes (
 * outcomeID SERIAL,
 * outcome VARCHAR(100) NOT NULL UNIQUE,
 * CONSTRAINT game_outcomes_key PRIMARY KEY (outcomeID)
 * );
 */


/**
 * The DAO of the card game
 *
 * @author Yifan Wu
 * @version 2017-02-26
 */
public class FunctionDB {
    private Connection con = null;
    private static final String DRIVER = "org.postgresql.Driver";
    private static final String URL = "jdbc:postgresql://mod-fund-databases.cs.bham.ac.uk/tlb646";
    private static final String USER = "tlb646";
    private static final String PASS = "m4lvc9jdq9";

    /**
     * The constructor of FunctionDB
     */
    public FunctionDB() {
        try {
            Class.forName(DRIVER);
            try {
                con = DriverManager.getConnection(URL, USER, PASS);
                System.out.println("Connected to database");
            } catch (SQLException e) {
                System.out.println("Problem with database connection");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public Connection getConnection() throws Exception {
        return con;
    }

    public static void free(Statement sta, Connection con) {
        try {
            if (null != sta) {
                sta.close();
                sta = null;
            }

            if (null != con) {
                con.close();
                con = null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void free(ResultSet rs, Statement sta, Connection con) {
        try {
            if (null != rs) {
                rs.close();
                rs = null;
            }

            if (null != sta) {
                sta.close();
                sta = null;
            }

            if (null != con) {
                con.close();
                con = null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized boolean insertUserIntoDatabase(User user) throws SQLException {
        String sql = "INSERT INTO users(username,password,firstname,lastname) VALUES(?,?,?,?)";
        PreparedStatement stat = con.prepareStatement(sql);
        stat.setString(1, user.getUserName());
        stat.setString(2, user.getPassword());
        stat.setString(3, user.getFirstName());
        stat.setString(4, user.getLastName());
        int update = stat.executeUpdate();
        return update > 0;

    }

    public synchronized User retrieveUserFromDatabase(String username) throws SQLException {
        String sql = "SELECT username,password,firstname,lastname FROM users WHERE username = ?";
        PreparedStatement stat = con.prepareStatement(sql);
        stat.setString(1, username);
        ResultSet rs = stat.executeQuery();
        User user = new User();
        if (rs.next()) {
            user.setUserName(rs.getString(1));
            user.setPassword(rs.getString(2));
            user.setFirstName(rs.getString(3));
            user.setLastName(rs.getString(4));
        }
        return user;

    }

    public synchronized User retrieveUserFromDatabaseWithUserId(int userid) throws SQLException {
        String sql = "SELECT username,password,firstname,lastname FROM users WHERE user_id =?";
        PreparedStatement stat = con.prepareStatement(sql);
        stat.setInt(1, userid);
        ResultSet rs = stat.executeQuery();
        User user = new User();
        if (rs.next()) {
            user.setUserName(rs.getString(1));
            user.setPassword(rs.getString(2));
            user.setFirstName(rs.getString(3));
            user.setLastName(rs.getString(4));
        }

        return user;
    }

    public synchronized boolean isUserRegistered(String username) throws SQLException {
        String sql = "SELECT username FROM users WHERE username =?";
        PreparedStatement stat = con.prepareStatement(sql);
        stat.setString(1, username);
        ResultSet rs = stat.executeQuery();
        return rs.next() != false;
    }

    public synchronized boolean insertNewGameIntoDatabase(int timelength, int totalpot) throws SQLException {
        String sql = "INSERT INTO games(time_length,total_wining_pot) VALUES(?,?)";
        PreparedStatement stat = con.prepareStatement(sql);
        stat.setInt(1, timelength);
        stat.setInt(2, totalpot);
        int update = stat.executeUpdate();
        return update > 0;
    }

    public synchronized int insertGameOutcomeIntoDatabse(String outcomes) throws SQLException {
        String sql = "INSERT INTO game_outcomes(outcome) SELECT ? WHERE NOT EXISTS (SELECT 1 FROM game_outcomes WHERE outcome=?) RETURNING outcome_id";
        PreparedStatement stat = con.prepareStatement(sql);
        stat.setString(1, outcomes);
        stat.setString(2, outcomes);
        ResultSet rs = stat.executeQuery();
        if (rs.next()) {
            int result = rs.getInt(1);
            rs.close();
            return result;
        }
        // if any else happen throw a wrong result
        return -1;
    }

    public synchronized boolean updateGameOutcomeFromDatabse(int outcome_id, String outcomes) throws SQLException {
        String sql = "UPDATE game_outcomes SET outcomes = (?) WHERE outcoms_id = (?)";
        PreparedStatement stat = con.prepareStatement(sql);
        stat.setInt(1, outcome_id);
        stat.setString(2, outcomes);
        ResultSet rs = stat.executeQuery();
        int update = stat.executeUpdate();
        return update > 0;
    }

    public synchronized boolean insertGameStatsIntoDatabase(int gameid, int userid, int chip_amount_changed) throws SQLException {
        // initialise the outcome result
        int outcomeid = insertGameOutcomeIntoDatabse("");
        String sql = "INSERT INTO users_games(user_id,game_id,outcom_id,chip_amount_changed) VALUES(?,?,?,?)";
        PreparedStatement stat = con.prepareStatement(sql);
        stat.setInt(1, userid);
        stat.setInt(2, gameid);
        stat.setInt(3, outcomeid);
        stat.setInt(4, chip_amount_changed);
        int update = stat.executeUpdate();
        return update > 0;
    }


}
