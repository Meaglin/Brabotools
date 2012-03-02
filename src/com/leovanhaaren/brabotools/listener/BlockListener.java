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
import com.leovanhaaren.brabotools.util.DisplayManager;
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
					
					player.sendMessage(ChatColor.RED + plugin.getConfigManager().TABLE_PLACE_BLOCK_MESSAGE);
					table.updatePosition();
				}
			} catch (Exception e) {}
    	}
    }

	@EventHandler(priority = EventPriority.LOW)
    public void onBlockBreak(BlockBreakEvent event) {
    	Block block = event.getBlock();
    	Player player = event.getPlayer();
    	DisplayManager manager = plugin.getDisplayManager();
    	
    	for (DisplayTable table: manager.getDisplayTables()) {
			try {
	    		if(table.getBlock().equals(block)) {
	    			player.sendMessage(ChatColor.RED + plugin.getConfigManager().TABLE_REMOVE_MESSAGE);
	    			manager.removeDisplayTable(player, block);
				}
			} catch (Exception e) {}
    	}
    }
    
}