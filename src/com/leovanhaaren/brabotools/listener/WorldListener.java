package com.leovanhaaren.brabotools.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

import com.leovanhaaren.brabotools.Brabotools;
import com.leovanhaaren.brabotools.util.DisplayTable;

public class WorldListener implements Listener {
	
	private Brabotools brabotools = null;

	public WorldListener(Brabotools bt) {
		brabotools = bt;
	}

	@EventHandler(priority = EventPriority.LOW)
    public void onChunkLoad(ChunkLoadEvent event) {
		for (DisplayTable item : brabotools.getDisplayManager().getDisplayTables()) {
			try {
				if (event.getChunk().equals(item.getBlock().getChunk())) {

				}
			} catch (Exception e) {}
		}
    }
    
    @EventHandler(priority = EventPriority.LOW)
	public void onChunkUnload(ChunkUnloadEvent event) {
		for (DisplayTable displayTable : brabotools.getDisplayManager().getDisplayTables()) {
			try {
				if (event.getChunk().equals(displayTable.getBlock().getChunk())) {

				}
			} catch (Exception e) {}
		}
	}
    
}