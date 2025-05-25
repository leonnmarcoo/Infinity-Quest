package Database;

import java.sql.*;

import Objects.Admin;
import Objects.User;
import Objects.Content;

public class DatabaseHandler {
    private static DatabaseHandler handler = null;
    private static Statement stmt = null;
    private static PreparedStatement pstatement = null;

    public static String dburl = DatabaseCredentials.ignoreDburl;
    public static String userName = DatabaseCredentials.ignoreUserName;
    public static String password = DatabaseCredentials.ignorePassword;

    public static DatabaseHandler getInstance() {
        if (handler == null) {
            handler = new DatabaseHandler();
        }
        return handler;
    }

    public static Connection getDBConnection()
    {
        Connection connection = null;

        try
        {
            connection = DriverManager.getConnection(dburl, userName, password);

        } catch (Exception e){
            e.printStackTrace();
        }

        return connection;
    }

    public ResultSet execQuery(String query) {
        ResultSet result;
        try {
            stmt = getDBConnection().createStatement();
            result = stmt.executeQuery(query);
        }
        catch (SQLException ex) {
            System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
            return null;
        }
        finally {
        }
        return result;
    }

// ============================ VALIDATE ADMIN LOGIN ===============================

    public static boolean validateadminLogin(String adminName, String adminPassword){

        getInstance();
        String query = "SELECT * FROM Admin WHERE adminName = '" + adminName + "' AND adminPassword = '" + adminPassword + "'";
            
        System.out.println(query);
    
        ResultSet result = handler.execQuery(query);
        try {
            if (result.next()) {
                return true;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

// ============================ CRUD ADMIN ===============================

    public static ResultSet getAdmin() {
        
        ResultSet result = null;
        try {
            String query = "SELECT * FROM Admin";
            result = handler.execQuery(query);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean createAdmin(Admin admin) {

        try {
            pstatement = getDBConnection().prepareStatement("INSERT INTO Admin (adminName, adminPassword) VALUES (?, ?)");
            pstatement.setString(1, admin.getAdminName());
            pstatement.setString(2, admin.getAdminPassword());

            return pstatement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteAdmin(Admin admin) {

        try {
            pstatement = getDBConnection().prepareStatement("DELETE FROM Admin WHERE adminID = ?");
            pstatement.setInt(1, admin.getAdminID());

            int res = pstatement.executeUpdate();
            if (res > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updateAdmin(Admin admin) {

        try {
            pstatement = getDBConnection().prepareStatement("UPDATE Admin SET adminName = ?, adminPassword = ? WHERE adminID = ?");
            pstatement.setString(1, admin.getAdminName());
            pstatement.setString(2, admin.getAdminPassword());
            pstatement.setInt(3, admin.getAdminID());

            int res = pstatement.executeUpdate();
            if (res > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

// ============================ CRUD USER ===============================

    public static ResultSet getUser() { 
    getInstance();
    ResultSet result = null;

        try {
            String query = "SELECT * FROM User";
            result = handler.execQuery(query);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public static boolean createUser(User user) {

        try {
            pstatement = getDBConnection().prepareStatement("INSERT INTO User (userName, userPassword, userEmail, userBio) VALUES (?, ?, ?, ?)");
            pstatement.setString(1, user.getUserName());
            pstatement.setString(2, user.getUserPassword());
            pstatement.setString(3, user.getUserEmail());
            pstatement.setString(4, user.getUserBio());
            return pstatement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteUser(User user) {

        try {
            pstatement = getDBConnection().prepareStatement("DELETE FROM User WHERE userName = ?");
            pstatement.setString(1, user.getUserName());

            int res = pstatement.executeUpdate();
            if (res > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updateUser(User user) {

        try {
            pstatement = getDBConnection().prepareStatement("UPDATE User SET userPassword = ?, userEmail = ?, userBio = ? WHERE userName = ?");
            pstatement.setString(1, user.getUserPassword());
            pstatement.setString(2, user.getUserEmail());
            pstatement.setString(3, user.getUserBio());
            pstatement.setString(4, user.getUserName());
            
            int res = pstatement.executeUpdate();
            if (res > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

// ============================ CRUD CONTENT ===============================

    public static ResultSet getContent() { 
        getInstance();
        ResultSet result = null;

    try {
        String query = "SELECT * FROM Content";
        result = handler.execQuery(query);
        }
     catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static ResultSet getAllDirectors() {
        getInstance();
        ResultSet result = null;

    try {
        String query = "SELECT DISTINCT directorName FROM Director ORDER BY directorName ASC";
        result = handler.execQuery(query);
    } catch (Exception e) {
        e.printStackTrace();
    }

        return result;
    }

    public static boolean createContent(Content content) {
    try {
        pstatement = getDBConnection().prepareStatement(
            "INSERT INTO Content (contentTitle, contentRuntime, contentSeason, contentEpisode, contentReleaseDate, contentSynopsis, contentDirector, contentPhase, contentAgeRating, contentChronologicalOrder, contentPoster, contentTrailer) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
        );

        pstatement.setString(1, content.getContentTitle());
        pstatement.setString(2, content.getContentRuntime());

        if (content.getContentSeason() != null) {
            pstatement.setInt(3, content.getContentSeason());
        } else {
            pstatement.setNull(3, java.sql.Types.INTEGER);
        }

        if (content.getContentEpisode() != null) {
            pstatement.setInt(4, content.getContentEpisode());
        } else {
            pstatement.setNull(4, java.sql.Types.INTEGER);
        }

        if (content.getContentReleaseDate() != null) {
            pstatement.setDate(5, java.sql.Date.valueOf(content.getContentReleaseDate()));
        } else {
            pstatement.setNull(5, java.sql.Types.DATE);
        }

        pstatement.setString(6, content.getContentSynopsis());
        pstatement.setString(7, content.getContentDirector());
        pstatement.setInt(8, content.getContentPhase());
        pstatement.setString(9, content.getContentAgeRating());
        pstatement.setInt(10, content.getContentChronologicalOrder());
        pstatement.setString(11, content.getContentPoster());
        pstatement.setString(12, content.getContentTrailer());

        return pstatement.executeUpdate() > 0;
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
 }



    public static boolean deleteContent(Content content) {

        try {
            pstatement = getDBConnection().prepareStatement("DELETE FROM Content WHERE contentID = ?");
            pstatement.setInt(1, content.getContentID());

            int res = pstatement.executeUpdate();
            if (res > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

public static boolean updateContent(Content content) {
    try {
        pstatement = getDBConnection().prepareStatement(
            "UPDATE Content SET contentTitle = ?, contentRuntime = ?, contentSeason = ?, contentEpisode = ?, contentReleaseDate = ?, contentSynopsis = ?, contentDirector = ?, contentPhase = ?, contentAgeRating = ?, contentChronologicalOrder = ?, contentPoster = ?, contentTrailer = ? WHERE contentID = ?"
        );

        pstatement.setString(1, content.getContentTitle());
        pstatement.setString(2, content.getContentRuntime());

        if (content.getContentSeason() != null) {
            pstatement.setInt(3, content.getContentSeason());
        } else {
            pstatement.setNull(3, java.sql.Types.INTEGER);
        }

        if (content.getContentEpisode() != null) {
            pstatement.setInt(4, content.getContentEpisode());
        } else {
            pstatement.setNull(4, java.sql.Types.INTEGER);
        }

        if (content.getContentReleaseDate() != null) {
            pstatement.setDate(5, java.sql.Date.valueOf(content.getContentReleaseDate()));
        } else {
            pstatement.setNull(5, java.sql.Types.DATE);
        }

        pstatement.setString(6, content.getContentSynopsis());
        pstatement.setString(7, content.getContentDirector());
        pstatement.setInt(8, content.getContentPhase());
        pstatement.setString(9, content.getContentAgeRating());
        pstatement.setInt(10, content.getContentChronologicalOrder());
        pstatement.setString(11, content.getContentPoster());
        pstatement.setString(12, content.getContentTrailer());
        pstatement.setInt(13, content.getContentID());

        int res = pstatement.executeUpdate();
        if (res > 0) {
            return true;
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}
}   