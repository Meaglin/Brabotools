package com.leovanhaaren.brabotools;

import java.util.logging.Logger;

import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.avaje.ebean.EbeanServer;
import com.leovanhaaren.brabotools.listener.BlockListener;
import com.leovanhaaren.brabotools.listener.EntityListener;
import com.leovanhaaren.brabotools.listener.PistonListener;
import com.leovanhaaren.brabotools.listener.PlayerListener;
import com.leovanhaaren.brabotools.listener.WorldListener;
import com.leovanhaaren.brabotools.persistence.Database;
import com.leovanhaaren.brabotools.util.CaptureManager;
import com.leovanhaaren.brabotools.util.DisplayManager;
import com.zones.Zones;
import com.zones.permissions.Permissions;
import com.zones.permissions.PermissionsResolver;


public class Brabotools extends JavaPlugin {
	
    public static Logger      	logger			= Logger.getLogger("Minecraft");
    private ConfigManager 		config 			= null;
    private Database			database		= null;
	private CaptureManager 		capture			= null;
    private DisplayManager 		manager			= null;
    private Permissions 		permissions 	= null;
    private Zones 				zones 			= null;

   	public void onDisable(){
   		PluginDescriptionFile pdfFile = this.getDescription();
   		logger.info(pdfFile.getName() + " version " + pdfFile.getVersion() + " is now disabled.");
   		manager.unloadTables();
   	}   
   	
    public void onEnable() {
    	PluginDescriptionFile pdfFile = this.getDescription();
		logger.info(pdfFile.getName() + " version " + pdfFile.getVersion() + " is now enabled.");
		
		PluginManager pm = getServer().getPluginManager();
		
		config = new ConfigManager(this);
		config.loadConfig();
		
		Plugin plugin = pm.getPlugin("Zones");
		if(plugin != null) zones = (Zones)plugin;
		
		database 		= new Database(this);
		capture 		= new CaptureManager(this);
		manager 		= new DisplayManager(this);
		permissions 	= PermissionsResolver.resolve(this);
		

		pm.registerEvents(new WorldListener(this), this);
		pm.registerEvents(new BlockListener(this), this);
		pm.registerEvents(new PistonListener(this), this);
		pm.registerEvents(new EntityListener(this), this);
		pm.registerEvents(new PlayerListener(this), this);
		
		manager.loadTables();
    }
    
    public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]) {
		if (cmd.getName().equals("displaytables")) {
			if (args.length != 1) return false;
			if (!args[0].equalsIgnoreCase("reload")) return false;
			
			Player player = (Player) sender;
			if (!canUse(player, "displaytable.reload")) return false;
			
			manager.reloadTableItems();
			player.sendMessage(config.TABLES_RELOADED_MESSAGE);
			return true;
		}
		return false;
	}
	
	public boolean canUse(Player player, String node) {
		if (permissions != null) {
			return permissions.canUse(player, node);
		}
		return true;
	}
	
	public boolean canHit(Player player, Entity entity) {
		if (zones != null) {
			return zones.getApi().canHit(player,entity);
		}
		return true;
	}
	
	public boolean canHit(Player player, Block block) {
		if (zones != null) {
			return zones.getApi().canHit(player,block);
		}
		return true;
	}
	
    public ConfigManager getConfigManager() {
        return config;
    }
	
    public Database getMysqlDatabase() {
        return database;
    }
    
    public CaptureManager getCaptureManager() {
        return capture;
    }
    
	public DisplayManager getDisplayManager() {
		return manager;
	}
	
	@Deprecated // Prevent usage.
	public EbeanServer getDatabase() {
	    return super.getDatabase();
	}
	
}
