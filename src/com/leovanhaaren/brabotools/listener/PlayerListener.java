package com.leovanhaaren.brabotools.listener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import com.leovanhaaren.brabotools.Brabotools;
import com.leovanhaaren.brabotools.Config;
import com.leovanhaaren.brabotools.util.DisplayManager;
import com.leovanhaaren.brabotools.util.DisplayTable;

public class PlayerListener implements Listener {
	
	private Brabotools plugin;

	public PlayerListener(Brabotools brabotools) {
		plugin = brabotools;
	}
	
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
        	if(!Config.DISPLAY_TABLE_ENABLED) return;
    
        	Block block 			= event.getClickedBlock();
        	Player player 			= event.getPlayer();
        	DisplayManager manager  = plugin.getDisplayManager();
        	
	        if(manager.isTableBlock(block)) {
		        if(player.isSneaking()) {
		        	event.setCancelled(true);

		    		if(manager.getTableByBlock(block) == null) {
		    			ItemStack item = player.getItemInHand();
		    			if(item.getType() == Material.AIR) return;
		    			manager.createDisplayTable(player, block);
		    		} else {
		    			manager.removeDisplayTable(player, block);
		    		}
		        }
        	}
        }
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
		for (DisplayTable table: plugin.getDisplayManager().getDisplayTables()) {
			try {
				if(table.getItem().equals(event.getItem())){
					table.getItem().setPickupDelay(2500);
					event.setCancelled(true);
				}
			} catch (Exception e) {}
		}
    }
    
}