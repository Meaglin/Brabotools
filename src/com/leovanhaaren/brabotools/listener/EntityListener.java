package com.leovanhaaren.brabotools.listener;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import com.leovanhaaren.brabotools.util.CaptureMob;
import com.leovanhaaren.brabotools.util.ExplosiveSnowball;

public class EntityListener implements Listener {
	
	@EventHandler(priority = EventPriority.LOW)
    public void onProjectileHit(ProjectileHitEvent e) {
		Entity entity = e.getEntity();
		
        if ((entity instanceof Arrow)) {
        	Arrow arrow = (Arrow) entity;
            Entity shooter = arrow.getShooter();
            if((shooter instanceof Player)) {
                arrow.getLocation().getBlock().setType(Material.GLOWSTONE);
            }
        }
		
        if ((entity instanceof Snowball)) {
        	Snowball snowball = (Snowball) entity;
            Entity shooter = snowball.getShooter();
            if((shooter instanceof Player)) {
                Player p = (Player) shooter;
                ExplosiveSnowball.Explode(p, entity);
            }
        }
        
        /*if (entity instanceof Egg) {
        	Egg egg = (Egg) entity;
            Entity shooter = egg.getShooter();
            List<Entity> target = egg.getNearbyEntities(1, 1, 1);
            if(shooter instanceof Player) {
            	if(target instanceof LivingEntity) {
	                Player p = (Player) shooter;
	                
	                p.sendMessage("works");
	                
	                //CraftEgg.Catch(p, entity);
            	}
            }
        }*/
    }
	
	@EventHandler(priority = EventPriority.LOW)
	public void OnEntityDamage(EntityDamageEvent e) {
		if ((e instanceof EntityDamageByEntityEvent)) {
			EntityDamageByEntityEvent event = (EntityDamageByEntityEvent)e;
			
			if ((event.getDamager() instanceof Egg)) {
				Egg egg = (Egg)event.getDamager();
				Entity target = event.getEntity();
	            Entity shooter = egg.getShooter();
	            
	            if((shooter instanceof Player)) {
		            if(!(target instanceof Player)) {
		                Player player = (Player) shooter;
						CaptureMob.Catch(player, target);
		            }
	            }
			}
		}
	}
	
}