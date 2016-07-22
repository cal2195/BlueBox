package cal.martin.bluebox.database;

import cal.martin.bluebox.metadata.MetaDirectory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cal
 */
public class DirectoryDatabase
{
    private static final Logger LOGGER = Logger.getLogger(FileDatabase.class.getName());
    private final Connection conn;
    
    public DirectoryDatabase(Connection databaseConnection)
    {
        conn = databaseConnection;
        setup();
    }
    
    public boolean createDirectory(MetaDirectory metadata, int parentID)
    {
        String sql = "INSERT INTO directories "
                + "(metadata, parent_id) "
                + "VALUES (?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setObject(1, metadata);
            stmt.setInt(2, parentID);
            stmt.execute();
            return true;
        } catch (SQLException ex)
        {
            Logger.getLogger(DirectoryDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public ArrayList<MetaDirectory> listDirectories(int id)
    {
        String sql = "SELECT metadata FROM directories "
                + "WHERE parent_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();
            ArrayList<MetaDirectory> directorys = new ArrayList<>();
            while (resultSet.next())
            {
                directorys.add(resultSet.getObject("metadata", MetaDirectory.class));
            }
            return directorys;
        } catch (SQLException ex)
        {
            Logger.getLogger(DirectoryDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private void setup()
    {
        try
        {
            LOGGER.info("Creating directory table if needed...");
            if (!tableExists("directories"))
            {
                try (Statement stmt = conn.createStatement())
                {
                    String sql = "CREATE TABLE directories "
                            + "(`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,"
                            + " metadata BLOB,"
                            + " parent_id INT NOT NULL,"
                            + " PRIMARY KEY ( id ))";
                    stmt.executeUpdate(sql);
                }
            }
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean tableExists(String table)
    {
        try (Statement stmt = conn.createStatement())
        {
            String sql = "SELECT * FROM " + table + " LIMIT 1";
            stmt.executeQuery(sql);
            return true;
        } catch (SQLException ex)
        {
            return false;
        }
    }
}
