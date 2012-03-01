package com.leovanhaaren.brabotools.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.config.ServerConfig;
import com.leovanhaaren.brabotools.Brabotools;
import com.leovanhaaren.brabotools.util.DisplayTable;
import com.mysql.jdbc.Statement;
import com.zones.Zones;
public class Database {
    
	private Brabotools plugin;
	
    private final String url,username,password;
    
    public static final String SAVE_TABLE = "INSERT INTO `displaytables` (id, world, x, y, z, player, itemid, itemdata) VALUES (?,?,?,?,?,?,?,?)";
    
    public static final String UPDATE_ZONE = "UPDATE `displaytables` SET " +
    		"`id` = ?, `world` = ?, `x` = ?, `y` = ?, `z` = ?, " +
    		"`player` = ?, `itemid` = ?, `itemdata` = ? " +
    		"WHERE id = ? LIMIT 1";
    
    public static final String DELETE_TABLE =    "DELETE FROM `displaytables` WHERE id = ? LIMIT 1";
    
    public static final String SELECT_WORLD =   "SELECT * FROM `displaytables` WHERE world = ? ";
    public static final String SELECT_TABLE =    "SELECT * FROM `displaytables` WHERE id = ? LIMIT 1";
    
    public Database(Zones plugin) {
        ServerConfig db = new ServerConfig();
        plugin.getServer().configureDbConfig(db);

        try {
            Class.forName(db.getDataSourceConfig().getDriver());
        } catch(Exception e) {
            Zones.log.warning("[Brabotools] Warning JDBC not available.");
        }
        
        this.url = db.getDataSourceConfig().getUrl();
        this.username = db.getDataSourceConfig().getUsername();
        this.password = db.getDataSourceConfig().getPassword();
    }
    
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
    
    public List<DisplayTable> get(String world) {
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
            	DisplayTable table = new DisplayTable(rs.getInt(1), world, rs.getDouble(2), rs.getDouble(3), rs.getDouble(4),  rs.getString(5),  rs.getInt(6),  rs.getShort(7));
                plugin.getDisplayManager().addTable(table);
            }
        } catch(Exception e) {
            Zones.log.warning("[Brabotools] Error loading tables of world " + world + ":");
            e.printStackTrace();
        } finally {
            try{
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
            st.setString(1, table.getBlock().getWorld().getName());
            st.setDouble(2, table.getBlock().getLocation().getX());
            st.setDouble(3, table.getBlock().getLocation().getY());
            st.setDouble(4, table.getBlock().getLocation().getZ());
            st.setString(5, table.getPlayer().getDisplayName());
            st.setInt(6, table.getItemId());
            st.setShort(7, table.getItemData());
            st.execute();
            rs = st.getGeneratedKeys();
            if(rs.next()) {
                table.setId(rs.getInt(1));
            }
        } catch(Exception e) {
            Zones.log.warning("[Brabotools]Error deleting table " + table.getId() + "!");
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
    
    public boolean update(DisplayTable table) {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            st = conn.prepareStatement(UPDATE_ZONE);
            st.setString(1, table.getBlock().getWorld().getName());
            st.setDouble(2, table.getBlock().getLocation().getX());
            st.setDouble(3, table.getBlock().getLocation().getY());
            st.setDouble(4, table.getBlock().getLocation().getZ());
            st.setString(5, table.getPlayer().getDisplayName());
            st.setInt(6, table.getItemId());
            st.setShort(7, table.getItemData());
            st.executeUpdate();
        } catch(Exception e) {
            Zones.log.warning("[Brabotools]Error updating table " + table.getId() + "!");
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
            Zones.log.warning("[Zones]Error deleting table " + table.getId() + "!");
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