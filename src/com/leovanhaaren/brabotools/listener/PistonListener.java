package com.leovanhaaren.brabotools.listener;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;

import com.leovanhaaren.brabotools.Brabotools;
import com.leovanhaaren.brabotools.util.DisplayTable;

public class PistonListener implements Listener {
	
	private Brabotools plugin;

	public PistonListener(Brabotools brabotools) {
		plugin = brabotools;
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onPistonExtend(BlockPistonExtendEvent event) {
        if (event.isCancelled()) return;
        
    	for (DisplayTable table: plugin.getDisplayManager().getDisplayTables()) {
    		for (Block block: event.getBlocks()) {
				try {
		    		if(table.getBlock().equals(block)) {
		    			event.setCancelled(true);
		    			table.updatePosition();
		    			break;
		    		}
				} catch (Exception e) {}
    		}
    	}
    }
	
	@EventHandler(priority = EventPriority.LOW)
	public void onPistonRetract(BlockPistonRetractEvent event) {
        if (event.isCancelled()) return;
        
    	for (DisplayTable table: plugin.getDisplayManager().getDisplayTables()) {
			try {
	    		if(table.getBlock().equals(event.getBlock().getRelative(event.getDirection(), 2))) {
	    			event.setCancelled(true);
	    			table.updatePosition();
	    			break;
	    		}
			} catch (Exception e) {}
    	}
    }

}
