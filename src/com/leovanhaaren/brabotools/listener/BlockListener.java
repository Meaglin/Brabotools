package com.leovanhaaren.brabotools.listener;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import com.leovanhaaren.brabotools.Brabotools;
import com.leovanhaaren.brabotools.models.DisplayTable;

public class BlockListener implements Listener {
	
	private Brabotools plugin;

	public BlockListener(Brabotools brabotools) {
		plugin = brabotools;
	}
	
    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
    	Block  block  	= 	event.getBlock().getRelative(BlockFace.DOWN);
    	Player player	= 	event.getPlayer();
    	
    	DisplayTable table = plugin.getDisplayManager().getTableByBlock(block);
    	if(table != null) {
    	    event.setCancelled(true);
					
			player.sendMessage(plugin.getConfigManager().TABLE_PLACE_BLOCK_MESSAGE);
			table.updatePosition();
			table.removeFire();
    	}
    }

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
    	Block block = event.getBlock();
    	Player player = event.getPlayer();
    	boolean isBlock = plugin.getDisplayManager().getTableByBlock(block) != null;
		
		if (isBlock) {
			if(!plugin.getDisplayManager().removeDisplayTable(player, block))
				event.setCancelled(true);
		}
    }
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onBlockFromTo(BlockFromToEvent  event) {
        if(plugin.getDisplayManager().isTable(event.getBlock().getRelative(BlockFace.DOWN, 2))) {
            event.setCancelled(true);
        }
    }
    
}