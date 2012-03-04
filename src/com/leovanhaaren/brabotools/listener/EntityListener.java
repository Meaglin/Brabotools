package com.leovanhaaren.brabotools.listener;

import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.entity.Egg;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import com.leovanhaaren.brabotools.Brabotools;
import com.leovanhaaren.brabotools.util.DisplayTable;
import com.leovanhaaren.brabotools.util.TntSnowball;

public class EntityListener implements Listener {
	
	private Brabotools plugin;

	public EntityListener(Brabotools brabotools) {
		plugin = brabotools;
	}

	@EventHandler(priority = EventPriority.LOW)
    public void onProjectileHit(ProjectileHitEvent event) {
		Entity entity = event.getEntity();
		
        if ((entity instanceof Snowball)) {
        	Snowball snowball = (Snowball) entity;
            Entity shooter = snowball.getShooter();
            if((shooter instanceof Player)) {
                Player player = (Player) shooter;
                if(plugin.canUse(player, "tntSnowball")) {
                	new TntSnowball(plugin).Explode(player, entity);
                }
            }
        }
    }
	
	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void OnEntityDamage(EntityDamageEvent event) {
		if ((event instanceof EntityDamageByEntityEvent)) {
			EntityDamageByEntityEvent entity = (EntityDamageByEntityEvent)event;
			
			if ((entity.getDamager() instanceof Egg)) {
				Egg egg = (Egg)entity.getDamager();
				LivingEntity target = (LivingEntity) event.getEntity();
	            Entity shooter = egg.getShooter();
	            
	            if((shooter instanceof Player)) {
		            if(!(target instanceof Player)) {
		            	Player player = (Player) shooter;
		            	if(plugin.canUse(player, "mobCatch")) {
		            		if(plugin.canHit(player, target)) {
		            			plugin.getCaptureManager().Catch(player, target);
		            		} else {
		            			event.setCancelled(true);
			            	}
		            	} else {
		            		event.setCancelled(true);
		            	}
		            }
	            }
			}
		}
	}
	
	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void onEntityExplode(EntityExplodeEvent event) {
        List<Block> blocks = event.blockList();
        
		for (Block block: blocks) {
		    DisplayTable table = plugin.getDisplayManager().getTableByBlock(block);
		    if(table != null) {
		        event.setCancelled(true);
		        table.respawn();
    		}
    	}
    }
	
	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onItemDespawn(ItemDespawnEvent event) {
	    if(event.getEntity().getPickupDelay() >= 5000) {
	        event.getEntity().setPickupDelay(DisplayTable.PICKUP_DELAY);
	        event.getEntity().setTicksLived(DisplayTable.AGE);
            event.setCancelled(true);
        }
    }
	
}