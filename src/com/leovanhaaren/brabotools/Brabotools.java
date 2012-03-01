package com.leovanhaaren.brabotools;

import java.util.logging.Logger;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.leovanhaaren.brabotools.listener.BlockListener;
import com.leovanhaaren.brabotools.listener.EntityListener;
import com.leovanhaaren.brabotools.listener.PlayerListener;
import com.leovanhaaren.brabotools.listener.WorldListener;
import com.leovanhaaren.brabotools.util.DisplayManager;
import com.zones.Zones;
import com.zones.permissions.Permissions;
import com.zones.permissions.PermissionsResolver;


public class Brabotools extends JavaPlugin {
	
    private Logger      		logger			= Logger.getLogger("Minecraft");
    private DisplayManager 		displaymanager	= null;
    private Permissions 		permissions 	= null;
    private Zones 				zones 			= null;

   	public void onDisable(){
   		PluginDescriptionFile pdfFile = this.getDescription();
   		logger.info(pdfFile.getName() + " version " + pdfFile.getVersion() + " is now disabled.");
   	}   
   	
    public void onEnable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		logger.info(pdfFile.getName() + " version " + pdfFile.getVersion() + " is now enabled.");
		
		Config config = new Config(this);
		config.loadConfig();
		
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new WorldListener(this), this);
		pm.registerEvents(new BlockListener(this), this);
		pm.registerEvents(new EntityListener(this), this);
		pm.registerEvents(new PlayerListener(this), this);
		
		permissions 	= PermissionsResolver.resolve(this);
		displaymanager 	= new DisplayManager(this);
		
		Plugin plugin = pm.getPlugin("Zones");
		if(plugin != null) zones = (Zones)plugin;
    }
	
	public boolean canUse(Player player, String node) {
		if(permissions != null) {
			return permissions.canUse(player, node);
		}
		return true;
	}
	
	public boolean canHit(Player player, Entity entity) {
		if(zones != null) {
			return zones.getApi().canHit(player,entity);
		}
		return true;
	}

	public DisplayManager getDisplayManager() {
		return displaymanager;
	}
	
	public Logger getLogger() {
		return logger;
	}
	
}
