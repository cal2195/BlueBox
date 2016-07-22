package cal.martin.bluebox.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cal
 */
public class Database
{
    private static final Logger LOGGER = Logger.getLogger(Database.class.getName());
    private Connection conn;
    private DirectoryDatabase directoryDatabase;
    private FileDatabase fileDatabase;
    private ChunkDatabase chunkDatabase;
    
    public void connect()
    {
        LOGGER.info("Connecting to local database...");
        try
        {
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection("jdbc:h2:./index.db", "sa", "");
            LOGGER.info("Connected!");
        } catch (ClassNotFoundException | SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void disconnect()
    {
        LOGGER.info("Disconnecting from local database...");
        try
        {
            conn.close();
            LOGGER.info("Connection closed!");
        } catch (SQLException ex)
        {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
