package com.leovanhaaren.brabotools;

import java.util.logging.Logger;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.leovanhaaren.brabotools.listener.EntityListener;
import com.leovanhaaren.brabotools.util.EggType;
import com.leovanhaaren.brabotools.util.ExplosiveSnowball;
import com.zones.Zones;
import com.zones.permissions.Permissions;
import com.zones.permissions.PermissionsResolver;


public class Brabotools extends JavaPlugin {
	
    protected static final Logger       logger          = Logger.getLogger("Minecraft");
    private static Permissions 			permissions 	= null;
    private static Zones 				zones 			= null;

   	public void onDisable(){
   		PluginDescriptionFile pdfFile = this.getDescription();
   		logger.info(pdfFile.getName() + " version " + pdfFile.getVersion() + " is now disabled.");
   	}   
   	
    public void onEnable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		logger.info(pdfFile.getName() + " version " + pdfFile.getVersion() + " is now enabled.");
		
		this.getConfig().options().copyDefaults(true);
		saveConfig();
		
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new EntityListener(), this);
		
		permissions = PermissionsResolver.resolve(this);
		
		Plugin plugin = pm.getPlugin("Zones");
		if(plugin != null) zones = (Zones)plugin;
		
        for (EggType eggType : EggType.values()) {
                String creatureName = eggType.getCreatureType().getName();
                boolean enabled = getConfig().getBoolean("mobs." + creatureName);
	            eggType.setEnabled(enabled);
        }
        
        ExplosiveSnowball.setEnabled(getConfig().getBoolean("tntSnowball.enabled"));
        ExplosiveSnowball.setRange(getConfig().getInt("tntSnowball.range"));
    }
	
	public static boolean canUse(Player player, String node) {
		if(permissions != null) {
			return permissions.canUse(player, node);
		}
		return true;
	}
	
	public static boolean canHit(Player player, Entity entity) {
		if(zones != null) {
			return zones.getApi().canHit(player,entity);
		}
		return true;
	}
	
}
