package com.leovanhaaren.brabotools.listener;

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
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import com.leovanhaaren.brabotools.Brabotools;
import com.leovanhaaren.brabotools.util.CaptureManager;
import com.leovanhaaren.brabotools.util.DisplayTable;
import com.leovanhaaren.brabotools.util.TntSnowball;

public class EntityListener implements Listener {
	
	private Brabotools brabotools = null;

	public EntityListener(Brabotools bt) {
		brabotools = bt;
	}

	@EventHandler(priority = EventPriority.LOW)
    public void onProjectileHit(ProjectileHitEvent e) {
		Entity entity = e.getEntity();
		
        if ((entity instanceof Snowball)) {
        	Snowball snowball = (Snowball) entity;
            Entity shooter = snowball.getShooter();
            if((shooter instanceof Player)) {
                Player player = (Player) shooter;
                if(brabotools.canUse(player, "tntSnowball")) {
                	TntSnowball.Explode(player, entity);
                }
            }
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
		            	if(brabotools.canUse(player, "mobCatch")) {
		            		if(brabotools.canHit(player, target)) {
		            			CaptureManager.Catch(player, target);
		            		} else {
		            			e.setCancelled(true);
			            	}
		            	} else {
		            		e.setCancelled(true);
		            	}
		            }
	            }
			}
		}
	}
	
	@EventHandler(priority = EventPriority.LOW)
    public void onItemDespawn(ItemDespawnEvent event) {
		for (DisplayTable table:  brabotools.getDisplayManager().getDisplayTables()) {
			try {
				if(table.getItem().equals(event.getEntity())){
					table.getItem().setPickupDelay(2500);
					event.setCancelled(true);
					table.respawn();
				}
			} catch (Exception e) {}
		}
    }
	
}