package com.leovanhaaren.brabotools.unused;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import com.leovanhaaren.brabotools.display.DisplayTable;
import com.leovanhaaren.brabotools.util.Hammer;

public class PlayerListener implements Listener {
	
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerInteract(PlayerInteractEvent e) {
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
	        if(e.getClickedBlock().getType() == Material.STEP) {
				Player p = e.getPlayer();
		        if(p.getItemInHand().getType() == Material.FEATHER) {
		        	new DisplayTable(e.getPlayer(), e.getClickedBlock());
		        }
	        }
        }
    }
   
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerPickupItem(PlayerPickupItemEvent e) {
    		/*for (DisplayItem item: DisplayTable) {
    			if(item.getItem().equals(e.getItem())){
    				item.getItem().setPickupDelay(2500);
    				e.setCancelled(true);
    			}
    		}*/
    }
    
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerMove(PlayerMoveEvent e) {
		if(Hammer.HammerList.containsKey(e.getPlayer())) e.setCancelled(true);
	}
    
}