package me.midas.testplugin;

import io.netty.handler.codec.http.HttpContentEncoder;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import net.ess3.api.Economy;
import net.minecraft.server.v1_12_R1.ExceptionPlayerNotFound;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.sqlite.*;

import javax.sql.*;
import java.io.File;
import java.math.BigDecimal;
import java.sql.*;
import java.util.UUID;

class Database {

    private String db = "Data.db";
    private FileHandler files = new FileHandler();
    private File f = new File(files.Dir + "\\" + db);
    private String fullpath = "jdbc:sqlite:" + f.getAbsolutePath();
    private DataSource dataSource;
    private JavaPlugin plugin;


    public Database()
    {
        if (!Checkdbfile())
        {
            CreateDBFile();
            CreateTable();
            dataSource = getSQLDataSource();
            return;
        }
        dataSource = getSQLDataSource();
        TestConnection();
    }

    public boolean executeQueryNoResponse(String sql)
    {
        Connection conn;
        Statement stmt;
        conn = null;
        stmt = null;
        try {
            if (conn == null)
                conn = dataSource.getConnection();
            if(stmt == null)
                stmt = conn.createStatement();
            return stmt.execute(sql);
        }
        catch (Exception er)
        {
            Log.LogWriteConsole("Error when executing non-response query: " + er.getMessage());
        }
        return false;
    }

    public ResultSet executeQueryWithResponse(String sql)
    {
        Connection conn;
        Statement stmt;
        ResultSet rs;
        try {
                conn = dataSource.getConnection();
                stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);

            return rs;
        }
        catch (Exception er)
        {
            Log.LogWriteConsole("Error when executing with-response query: " + er.toString());
        }
        return null;
    }

    private boolean CreateDBFile()
    {
        try
        {
            f.createNewFile();
            Log.LogWriteConsole("New Database Was Created...");
            return true;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private boolean Checkdbfile()
    {
        try
        {
          if(f.exists() && !f.isDirectory())
          {
              return true;
          }
          else {
              Log.LogWriteConsole("Database File Not Found!");
              return false;
          }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }




    /* /Functions For Database */

    private boolean TestConnection()
    {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try
        {
            conn = dataSource.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM 'Clients'");
            if(rs.next())
            {
                return true;
            }
            //Log.LogWriteConsole("Successfully connected to the database.");
            return  true;
        }
        catch (Exception e)
        {
            Log.LogWriteConsole("There was an error while testing database connection, contact support. - " + e.getMessage());
            System.out.println(e.getMessage());
            System.out.println("-----------------------Stack Trace-----------------------");
            e.printStackTrace();
            System.out.println("-----------------------End of Stack Trace-----------------------");
            plugin.onDisable();
            return false;
        }
        finally {
            try {
                rs.close();
                stmt.close();
                conn.close();
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private DataSource getSQLDataSource()
    {
        String Path = fullpath;
        try {
            SQLiteDataSource sqlsrc = new SQLiteDataSource();
            sqlsrc.setUrl(Path);
            return sqlsrc;
        }
        catch (Exception e)
        {
            Log.LogWriteConsole("There was an error while accessing DataSource...");
            return null;
        }
    }

    private boolean CreateTable()
    {
        Connection conn;
        Statement stmt;
        ResultSet rs;
        conn = null;
        stmt = null;
        try {
            conn = getSQLDataSource().getConnection();
            stmt = conn.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS Clients ( [ID] INTEGER PRIMARY KEY AUTOINCREMENT, [Username] TEXT NOT NULL , [LookUpName] TEXT NOT NULL , [UUID] TEXT NOT NULL , [IP] TEXT NOT NULL , [Balance] DOUBLE NOT NULL )");
            return true;

        }
        catch (Exception e)
        {
            Log.LogWriteConsole("There was an error while creating Tables...");
            Log.LogWriteConsole(e.getMessage());
            e.printStackTrace();
            return false;
        }
        finally {
            try {
                if(!conn.isClosed()) {
                    conn.close();
                }
                if(!stmt.isClosed()) {
                    stmt.close();
                }
            }catch (Exception e){
            }
        }
    }

    public boolean AddPlayerToDB(String username, String UUID, String IP)
    {
        Connection conn = null;
        Statement stmt = null;
        if(checkIfPlayerInDB(username,UUID))
        {
            return false;
        }
        try
        {
            double balance = PlayerEssentialsAcc(username);
            Log.ColorLogWriteConsole(username + " balance - " + String.valueOf(balance));
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement("INSERT INTO 'Clients'(Username,LookUpName,UUID,IP,Balance) VALUES (?,?,?,?,?)");
            ((PreparedStatement) stmt).setString(1,username);
            ((PreparedStatement) stmt).setString(2,username.toLowerCase());
            ((PreparedStatement) stmt).setString(3,UUID);
            ((PreparedStatement) stmt).setString(4,IP);
            ((PreparedStatement) stmt).setDouble(5,balance);
            ((PreparedStatement) stmt).execute();
                Log.LogWriteConsole("Player " + username + " was successfully added to database...");
                return true;
        }
        catch (Exception e)
        {
            Log.LogWriteConsole("There was an error while adding player to DB... - " + e.toString());
            return false;
        }
        finally {
            try {
                if(!conn.isClosed())
                conn.close();
                if(!stmt.isClosed())
                stmt.close();
            }catch (Exception e){
            }
        }
    }

    private double PlayerEssentialsAcc(String username){
        try {
            BigDecimal bal = Economy.getMoneyExact(username);
            return bal.doubleValue();
        }catch (Exception e){
            return Double.valueOf("300");
        }
    }

    public boolean checkIfPlayerInDB(String username, String UUID) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM 'Clients' WHERE UUID = ? " +
                    "AND Username = ?;";
            conn = dataSource.getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, UUID);
            st.setString(2, username);
            rs = st.executeQuery();
            return rs.next();
        }
        catch (Exception e)
        {
            Log.LogWriteConsole("There was an error while checking Player in Database! - " + e.toString());
            e.printStackTrace();
            return  false;
        }
        finally {
            try {
                rs.close();
                conn.close();
            }catch (Exception e)
            {
                Log.LogWriteConsole(e.getMessage());
                e.printStackTrace();
            }
        }

    }

    public boolean setPlayerBalance(String username, String UUID, double balance)
    {

        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            if(balance < 0)
            {
                throw new ValueException("Error 405: The balance would be negative!");
            }
            String sql = "UPDATE 'Clients' SET Balance = ? "
                    + "WHERE Username = ? "
                    + "AND UUID = ?;";
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setDouble(1,balance);
            stmt.setString(2,username);
            stmt.setString(3,UUID);
            stmt.execute();
            return true;
        }catch (Exception e){
            Log.LogWriteConsole("There was an error while setting balance of a Player...  - " + e.getMessage());
            e.printStackTrace();
            return false;
        }
        finally {
            try {
                stmt.close();
                conn.close();
            }catch (Exception e)
            {
                Log.LogWriteConsole(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public boolean addPlayerBalance(String username, String UUID, double balance)
    {
        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            if(balance < 0)
            {
                throw new ValueException("Error 406: Can't add negative funds!");
            }
            double oldbal = getBalance(username,UUID);
            double newbal = oldbal + balance;
            String sql = "UPDATE 'Clients' SET Balance = ? "
                    + "WHERE Username = ? "
                    + "AND UUID = ?;";
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setDouble(1,newbal);
            stmt.setString(2,username);
            stmt.setString(3,UUID);
            stmt.execute();
            return true;
        }catch (Exception e){
            Log.LogWriteConsole("There was an error while adding balance to Player...  - " + e.getMessage());
            e.printStackTrace();
            return false;
        }
        finally {
            try {
                stmt.close();
                conn.close();
            }catch (Exception e)
            {
                Log.LogWriteConsole(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public boolean removePlayerBalance(String username, String UUID, double balance)
    {
        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            double oldbal = getBalance(username,UUID);
            if(balance > oldbal)
            {
                throw new ValueException("Error 405: The balance would be negative!");
            }
            double newbal = oldbal - balance;
            String sql = "UPDATE 'Clients' SET Balance = ? "
                    + "WHERE Username = ? "
                    + "AND UUID = ?;";
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setDouble(1,newbal);
            stmt.setString(2,username);
            stmt.setString(3,UUID);
            stmt.execute();
            return true;
        }catch (Exception e){
            Log.LogWriteConsole("There was an error while removing balance from Player...  - " + e.getMessage());
            e.printStackTrace();
            return false;
        }
        finally {
            try {
                stmt.close();
                conn.close();
            }catch (Exception e)
            {
                Log.LogWriteConsole(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @Deprecated
    public boolean exchangeBalanceBetweenPlayers(Client Sender, Client Receiver)
    {

        return false;
    }

    public double getBalance(String username, String UUID)
    {
        Connection conn =null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM 'Clients' WHERE UUID = ? " +
                    "AND Username = ?;";
            conn = dataSource.getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, UUID);
            st.setString(2, username);
            rs = st.executeQuery();
            if(rs.next()) {
                return rs.getDouble("Balance");
            }
            else {
                throw new ExceptionPlayerNotFound("Error 400: Player was not found in the databse!");
                //return Double.NaN;
            }
        }
        catch (Exception er)
        {
            Log.LogWriteConsole("Error when getting balance for [" + username + ", " + UUID + "]: " + er.getMessage());
            return Double.NaN;
        }
        finally {
            try {
                rs.close();
                conn.close();
            }catch (Exception e)
            {
                Log.LogWriteConsole(e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
