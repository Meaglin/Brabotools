package com.leovanhaaren.brabotools.unused;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockListener implements Listener {
    
    @EventHandler(priority = EventPriority.LOW)
    public void onBlockBreak(BlockBreakEvent e) {
    	/*for (DisplayItem item: DisplayTable) {
			if(item.getBlock().equals(e.getBlock())){
				DisplayTable.remove(item);
				item.getItem().setPickupDelay(0);
			}
		}*/
    }

}