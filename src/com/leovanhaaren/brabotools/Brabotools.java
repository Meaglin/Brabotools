package com.leovanhaaren.brabotools;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.leovanhaaren.brabotools.util.CaptureMob;
import com.leovanhaaren.brabotools.util.EggType;
import com.zones.Zones;
import com.zones.permissions.Permissions;
import com.zones.permissions.PermissionsResolver;


public class Brabotools extends JavaPlugin implements Listener {
	
    protected static final Logger       logger          = Logger.getLogger("Minecraft");
    private Permissions 				permissions 	= null;
    private Zones 						zones 			= null;

   	public void onDisable(){
   		PluginDescriptionFile pdfFile = this.getDescription();
   		logger.info(pdfFile.getName() + " version " + pdfFile.getVersion() + " is now disabled.");
   	}   
   	
    public void onEnable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		logger.info(pdfFile.getName() + " version " + pdfFile.getVersion() + " is now enabled.");
		
		permissions = PermissionsResolver.resolve(this);
		Plugin plugin = getServer().getPluginManager().getPlugin("Zones");
		if(plugin != null) {
			Zones zones = (Zones)plugin;
		}
		
		getServer().getPluginManager().registerEvents(this, this);
		
        for (EggType eggType : EggType.values()) {
                String creatureName = eggType.getCreatureType().getName();
                boolean enabled = getConfig().getBoolean("mobs." + creatureName);
	            eggType.setEnabled(enabled);
        }
    }
    
	@EventHandler(priority = EventPriority.LOW)
	public void OnEntityDamage(EntityDamageEvent e) {
		if ((e instanceof EntityDamageByEntityEvent)) {
			EntityDamageByEntityEvent event = (EntityDamageByEntityEvent)e;
			
			if ((event.getDamager() instanceof Egg)) {
				Egg egg = (Egg)event.getDamager();
				LivingEntity target = (LivingEntity) event.getEntity();
	            Entity shooter = egg.getShooter();
	            
	            if((shooter instanceof Player)) {
		            if(!(target instanceof Player)) {
		            	Player player = (Player) shooter;
		            	if(canUse(player, "mobCatch")) {
		            		if(canHit(player, target)) {
		            			CaptureMob.Catch(player, target);
		            		} else {
			            		player.sendMessage(ChatColor.RED + "Sorry, you need hit rights.");
			            	}
		            	} else {
		            		player.sendMessage(ChatColor.RED + "Sorry, you need permissions to do this.");
		            	}
		            }
	            }
			}
		}
	}
	
	public boolean canUse(Player player, String node) {
		return permissions == null ? false : permissions.canUse(player, node);
	}
	
	public boolean canHit(Player player, Entity entity) {
		return zones == null ? false : zones.getApi().canHit(player,entity);
	}
	
}
