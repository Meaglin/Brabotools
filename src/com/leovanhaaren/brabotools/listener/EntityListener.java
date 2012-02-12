package com.leovanhaaren.brabotools.listener;

import org.bukkit.entity.Snowball;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import com.leovanhaaren.brabotools.util.ExplosiveSnowball;

public class EntityListener implements Listener {
    
    @EventHandler(priority = EventPriority.LOW)
    public void onItemDespawn(ItemDespawnEvent e) {
    		/*for (DisplayItem item: DisplayTable) {
    			if (item.getItem() == null || item.getItem().isDead()) {
    				item.respawn();
    				//e.setCancelled(true);
    			}
    			item.updatePosition();
    		}*/
    }
	
	@EventHandler(priority = EventPriority.LOW)
    public void onProjectileHit(ProjectileHitEvent e) {
		Entity entity = e.getEntity();
		
        if (entity instanceof Snowball) {
        	Snowball travel = (Snowball) entity;
            Entity shooter = travel.getShooter();
            if(shooter instanceof Player) {
                Player p = (Player) shooter;
                ExplosiveSnowball.Explode(p, entity);
            }
        }
    }
	
}