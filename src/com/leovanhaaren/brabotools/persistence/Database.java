package com.leovanhaaren.brabotools.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.plugin.PluginDescriptionFile;

import com.avaje.ebean.config.ServerConfig;
import com.leovanhaaren.brabotools.Brabotools;
import com.leovanhaaren.brabotools.util.DisplayTable;
import com.mysql.jdbc.Statement;
public class Database {
	
    private Brabotools plugin;
	
    private final String url,username,password;
    
    public static final String SAVE_TABLE = "INSERT INTO `displaytables` (id, world, x, y, z, player, itemid, itemdata) VALUES (?,?,?,?,?,?,?,?)";
    
    public static final String DELETE_TABLE =    "DELETE FROM `displaytables` WHERE id = ? LIMIT 1";
    
    public static final String SELECT_WORLD =   "SELECT * FROM `displaytables` WHERE world = ?";
    public static final String SELECT_TABLE =    "SELECT * FROM `displaytables` WHERE id = ? LIMIT 1";
    
    public Database(Brabotools brabotools) {
    	plugin = brabotools;
    	
        ServerConfig db = new ServerConfig();
        brabotools.getServer().configureDbConfig(db);

        try {
            Class.forName(db.getDataSourceConfig().getDriver());
        } catch(Exception e) {
        	PluginDescriptionFile pdfFile = plugin.getDescription();
        	Brabotools.logger.warning(pdfFile.getName() + " Warning JDBC not available.");
        }
        
        this.url = db.getDataSourceConfig().getUrl();
        this.username = db.getDataSourceConfig().getUsername();
        this.password = db.getDataSourceConfig().getPassword();
    }
    
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
    
    public List<DisplayTable> getTables(String world) {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        List<DisplayTable> tables = new ArrayList<DisplayTable>();
        try {
            conn = getConnection();
            st = conn.prepareStatement(SELECT_WORLD);
            st.setString(1, world);
            rs = st.executeQuery();
            while(rs.next()) {
            	DisplayTable table = new DisplayTable(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getString(6), rs.getInt(7), rs.getShort(8));
            	tables.add(table);
            }
        } catch(Exception e) {
        	PluginDescriptionFile pdfFile = plugin.getDescription();
        	Brabotools.logger.warning(pdfFile.getName() + " Error loading tables of world " + world);
            e.printStackTrace();
        } finally {
            try {
                if(conn != null) conn.close();
                if(st != null) st.close();
                if(rs != null) rs.close();
            } catch(Exception e) {}
        }
        return tables;
    }
    
    public boolean save(DisplayTable table) {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            st = conn.prepareStatement(SAVE_TABLE, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, 		table.getId());
            st.setString(2, 	table.getWorld().getName());
            st.setInt(3, (int)  table.getBlock().getLocation().getX());
            st.setInt(4, (int)  table.getBlock().getLocation().getY());
            st.setInt(5, (int)  table.getBlock().getLocation().getZ());
            st.setString(6, 	table.getPlayer());
            st.setInt(7, 		table.getItemId());
            st.setShort(8, 		table.getItemData());
            st.execute();
            rs = st.getGeneratedKeys();
            if(rs.next()) {
                table.setId(rs.getInt(1));
            }
        } catch(Exception e) {
        	PluginDescriptionFile pdfFile = plugin.getDescription();
        	Brabotools.logger.warning(pdfFile.getName() + " Error saving table " + table.getId() + "!");
            e.printStackTrace();
            return false;
        } finally {
            try{
                if(conn != null) conn.close();
                if(st != null) st.close();
                if(rs != null) rs.close();
            } catch(Exception e) {}
        }
        if(table.getId() == 0 ) return false;
        return true;
    }
    
    public boolean delete(DisplayTable table) {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            st = conn.prepareStatement(DELETE_TABLE);
            st.setInt(1, table.getId());
            st.execute();
        } catch(Exception e) {
        	PluginDescriptionFile pdfFile = plugin.getDescription();
        	Brabotools.logger.warning(pdfFile.getName() + " Error deleting table " + table.getId() + "!");
            e.printStackTrace();
            return false;
        } finally {
            try{
                if(conn != null) conn.close();
                if(st != null) st.close();
                if(rs != null) rs.close();
            } catch(Exception e) {}
        }
        return true;
    }
    
}