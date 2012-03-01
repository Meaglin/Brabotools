package com.leovanhaaren.brabotools.listener;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.leovanhaaren.brabotools.Brabotools;
import com.leovanhaaren.brabotools.util.DisplayTable;

public class BlockListener implements Listener {
	
	private Brabotools brabotools = null;

	public BlockListener(Brabotools bt) {
		brabotools = bt;
	}

	@EventHandler(priority = EventPriority.LOW)
    public void onBlockBreak(BlockBreakEvent event) {
    	Player player = event.getPlayer();
    	Block block = event.getBlock();
    	List<DisplayTable> displaytables = brabotools.getDisplayManager().getDisplayTables();
    	
    	for (DisplayTable table: displaytables) {
			try {
	    		if(table.getBlock().equals(block)) {
					if(table.getPlayer().equals(player)) {
						table.getItem().setPickupDelay(0);
						brabotools.getDisplayManager().removeDisplayTable(table);
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