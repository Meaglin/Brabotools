package com.leovanhaaren.brabotools.listener;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import com.leovanhaaren.brabotools.Brabotools;
import com.leovanhaaren.brabotools.util.DisplayTable;

public class BlockListener implements Listener {
	
	private Brabotools plugin;

	public BlockListener(Brabotools brabotools) {
		plugin = brabotools;
	}
	
    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockPlace(BlockPlaceEvent event) {
    	Block  block  	= 	event.getBlock().getRelative(BlockFace.DOWN);
    	Player player	= 	event.getPlayer();
    	
    	for (DisplayTable table: plugin.getDisplayManager().getDisplayTables()) {
			try {
				
	    		if(table.getBlock().equals(block)) {
					event.setCancelled(true);
					
					if(table.getPlayer().equals(player)) {
						player.sendMessage(ChatColor.RED + "Cannot place blocks on your own Display Table!");
					} else {
						player.sendMessage(ChatColor.RED + "Cannot place blocks on " + table.getPlayer().getDisplayName() + ChatColor.RED + "'s Display Table!");
					}
					
					table.updatePosition();
				}
			} catch (Exception e) {}
    	}
    }

	@EventHandler(priority = EventPriority.LOW)
    public void onBlockBreak(BlockBreakEvent event) {
    	Player player = event.getPlayer();
    	Block block = event.getBlock();
    	
    	for (DisplayTable table: plugin.getDisplayManager().getDisplayTables()) {
			try {
	    		if(table.getBlock().equals(block)) {
					if(table.getPlayer().equals(player)) {
						table.getItem().setPickupDelay(0);
						plugin.getDisplayManager().removeDisplayTable(table);
						player.sendMessage(ChatColor.GOLD + "Table removed!");
					} else {
						event.setCancelled(true);
						player.sendMessage(ChatColor.RED + "This table belongs to " + table.getPlayer().getDisplayName() + ChatColor.RED + "!");
					}
				}
			} catch (Exception e) {}
    	}
    }
    
}