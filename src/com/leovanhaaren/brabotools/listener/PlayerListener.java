package com.leovanhaaren.brabotools.listener;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import com.leovanhaaren.brabotools.Brabotools;
import com.leovanhaaren.brabotools.util.DisplayTable;

public class PlayerListener implements Listener {
	
	private Brabotools brabotools = null;

	public PlayerListener(Brabotools bt) {
		brabotools = bt;
	}
	
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
        	Block block = event.getClickedBlock();
        	Player player = event.getPlayer();
	        if(block.getType().equals(Material.GOLD_BLOCK))
		        if(player.isSneaking()) {
		        	brabotools.getDisplayManager().createDisplayTable(player, block);
		        	event.setCancelled(true);
		        } else {
		        	DisplayTable table = brabotools.getDisplayManager().getTableByBlock(block);
		        	if(table != null) {
		        		event.setCancelled(true);
		        		table.updatePosition();
		        		player.sendMessage(ChatColor.RED + "Sorry, you cannot place blocks on top of a Display Table!");
		        	}
		        }
        }
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
		for (DisplayTable table: brabotools.getDisplayManager().getDisplayTables()) {
			try {
				if(table.getItem().equals(event.getItem())){
					table.getItem().setPickupDelay(2500);
					event.setCancelled(true);
				}
			} catch (Exception e) {}
		}
    }
    
}