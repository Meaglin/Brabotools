package com.leovanhaaren.brabotools;

import java.util.logging.Logger;

import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

//import com.leovanhaaren.brabotools.permissions.Permissions;
//import com.leovanhaaren.brabotools.permissions.PermissionsResolver;
import com.leovanhaaren.brabotools.util.CaptureMob;
import com.leovanhaaren.brabotools.util.EggType;


public class Brabotools extends JavaPlugin implements Listener {
	
	public static Brabotools			plugin  		= null;
	//private static final String 		prefix 			= "brabotools.";
    protected static final Logger       logger          = Logger.getLogger("Minecraft");
    //private Permissions permissions;

   	public void onDisable(){
   		plugin = null;
   		PluginDescriptionFile pdfFile = this.getDescription();
   		logger.info(pdfFile.getName() + " version " + pdfFile.getVersion() + " is now disabled.");
   	}   
   	
    public void onEnable() {
    	plugin = this;
		PluginDescriptionFile pdfFile = this.getDescription();
		logger.info(pdfFile.getName() + " version " + pdfFile.getVersion() + " is now enabled.");
		
		//permissions = PermissionsResolver.resolve(this);
		getServer().getPluginManager().registerEvents(this, plugin);
		
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
		            	//if(canUse(player, "mobCatch")) {
							CaptureMob.Catch(player, target);
		            	//}
		            }
	            }
			}
		}
	}
	
	/*public boolean canUse(Player player, String node) {
	    return permissions.canUse(player, prefix + node);
	}*/
    
    public static Brabotools getInstance(){
        return plugin;
    }
	
}
