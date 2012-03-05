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
	
	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void onPistonExtend(BlockPistonExtendEvent event) {
		for (Block block: event.getBlocks()) {
		    DisplayTable table = plugin.getDisplayManager().getTableByBlock(block);
            if(table != null) {
                event.setCancelled(true);
                table.updatePosition();
            }
		}
    }
	
	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void onPistonRetract(BlockPistonRetractEvent event) {
        DisplayTable table = plugin.getDisplayManager().getTableByBlock(event.getBlock().getRelative(event.getDirection(), 2));
        if(table != null) {
            event.setCancelled(true);
            table.updatePosition();
        }
    }

}
