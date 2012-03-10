package com.leovanhaaren.brabotools.listener;

import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.inventory.ItemStack;

import com.leovanhaaren.brabotools.Brabotools;
import com.leovanhaaren.brabotools.ConfigManager;
import com.leovanhaaren.brabotools.models.DisplayTable;
import com.leovanhaaren.brabotools.util.InventoryUtil;

public class EntityListener implements Listener {
	
	private Brabotools plugin;
	Random random = new Random();

	public EntityListener(Brabotools brabotools) {
		plugin = brabotools;
	}

	/* @EventHandler(priority = EventPriority.NORMAL)
    public void onProjectileHit(ProjectileHitEvent event) {
		Entity entity = event.getEntity();
		
        if ((entity instanceof Snowball)) {
        	Snowball snowball = (Snowball) entity;
            Entity shooter = snowball.getShooter();
            if((shooter instanceof Player)) {
                Player player = (Player) shooter;
                if(plugin.canUse(player, "tntSnowball")) {
            		if (!plugin.getConfigManager().TNT_SNOWBALL_ENABLED) return;
            		
            		if (ItemManager.Remove(player.getInventory(), new ItemStack(Material.TNT, 1)))
            			entity.getWorld().createExplosion(entity.getLocation(), plugin.getConfigManager().TNT_SNOWBALL_RANGE);
                }
            }
        }
    } */
	
	@SuppressWarnings("deprecation")
    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void OnEntityDamage(EntityDamageEvent event) {
		if ((event instanceof EntityDamageByEntityEvent)) {
			EntityDamageByEntityEvent entity = (EntityDamageByEntityEvent)event;
			
			if(!(entity.getDamager() instanceof Egg)) return;
			
			Egg egg = (Egg)entity.getDamager();
			LivingEntity target = (LivingEntity) event.getEntity();
			EntityType entitytype = target.getType();
			Entity shooter = egg.getShooter();
			
			if(!(shooter instanceof Player)) return;
			Player player = (Player) shooter;
			ConfigManager config = plugin.getConfigManager();
			
			if (!plugin.getConfig().getBoolean("mobCatch.mobs." + entitytype.getName())) {
			    player.sendMessage(config.MOB_DISABLED_MESSAGE);
			    return;
			}

			if (target instanceof Tameable) {
			    if (((Tameable)target).isTamed()) {
			        player.sendMessage(config.MOB_TAMED_MESSAGE);
			        return;
			    }
			}
			
			if (target instanceof Animals) {
			    if (!((Animals)target).isAdult()) {
			        player.sendMessage(config.MOB_BABY_MESSAGE);
			        return;
			    }
			}

			if(!InventoryUtil.has(player.getInventory(), plugin.getConfigManager().MOBCATCH_ITEM)) {
			    return;
			}
			
			if(!plugin.canUse(player, "mobCatch")) {
			    player.sendMessage(ChatColor.YELLOW + "You're not allowed to catch mobs.");
			    return;
			}
			
			if(!plugin.canHit(player, target)) {
			    player.sendMessage(ChatColor.YELLOW + "You're not allowed to catch mobs in this zone.");
			    return;
			}
	        
	        if (!InventoryUtil.Remove(player.getInventory(), config.MOBCATCH_ITEM)) {
	            player.sendMessage(config.MOB_COST_MESSAGE);
	            return;
	        }
	        
	        if (random.nextInt(100) < config.MOBCATCH_CHANCE) {
	            World world       = player.getWorld();
	            Location location = target.getLocation();
	            
	            target.remove();
	                
	            ItemStack item = new ItemStack(383, 1, target.getType().getTypeId());
	            world.dropItem(location, item);
	            
	            String mob = target.getType().getName();
	            player.sendMessage(ChatColor.GOLD + mob + config.MOB_CAUGHT_MESSAGE);
	            
	            player.updateInventory();
	            return;
	        }
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
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
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onItemDespawn(ItemDespawnEvent event) {
	    if(event.getEntity().getPickupDelay() >= 5000) {
	        event.getEntity().setPickupDelay(DisplayTable.PICKUP_DELAY);
            event.setCancelled(true);
        }
    }
	
}