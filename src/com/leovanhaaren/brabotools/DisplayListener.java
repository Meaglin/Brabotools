package com.leovanhaaren.brabotools;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

@SuppressWarnings("unused")
public class DisplayListener implements Listener {
	private Brabotools plugin;
	
	public void DisplayerListener(Brabotools bt){
		plugin = bt;
	}
	
	/*@EventHandler(priority = EventPriority.LOW)
    public void onPlayerInteract(PlayerInteractEvent e) {
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
	        if(e.getClickedBlock().getType() == Material.STEP) {
	        	Player p = e.getPlayer();
		        //if(p.getItemInHand().getType() == Material.FEATHER)
		        	
		        	//Displaytable dpt = new DisplayTable;
		        	//dpt.placeItem(e.getPlayer(), e.getClickedBlock());
	        }
        }
    }
    
    
    @EventHandler(priority = EventPriority.LOW)
    public void onItemDespawn(ItemDespawnEvent e) {
    		for (DisplayItem item: DisplayTable) {
    			if (item.getItem() == null || item.getItem().isDead()) {
    				item.respawn();
    				//e.setCancelled(true);
    			}
    			item.updatePosition();
    		}
    }
    
   
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerPickupItem(PlayerPickupItemEvent e) {
    		for (DisplayItem item: DisplayTable) {
    			if(item.getItem().equals(e.getItem())){
    				item.getItem().setPickupDelay(2500);
    				e.setCancelled(true);
    			}
    		}
    		
    }
    
    
    @EventHandler(priority = EventPriority.LOW)
    public void onBlockBreak(BlockBreakEvent e) {
    	for (DisplayItem item: DisplayTable) {
			if(item.getBlock().equals(e.getBlock())){
				DisplayTable.remove(item);
				item.getItem().setPickupDelay(0);
			}
		}
    }
	
	@EventHandler(priority = EventPriority.LOW)
    public void onProjectileHit(ProjectileHitEvent e)
    {
        Entity entity = e.getEntity();
        if(entity instanceof org.bukkit.entity.Arrow){
            World w = entity.getWorld();
            w.createExplosion(entity.getLocation(), 5);
        }
        if(entity instanceof org.bukkit.entity.Snowball){
            World w = entity.getWorld();
            w.createExplosion(entity.getLocation(), 5);
        }
        if(entity instanceof org.bukkit.entity.Egg){
            World w = entity.getWorld();
            w.createExplosion(entity.getLocation(), 5);
        }
    }*/
}
