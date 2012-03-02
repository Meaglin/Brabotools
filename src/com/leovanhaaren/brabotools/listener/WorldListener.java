package com.leovanhaaren.brabotools.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

import com.leovanhaaren.brabotools.Brabotools;
import com.leovanhaaren.brabotools.util.DisplayTable;

public class WorldListener implements Listener {
	
	private Brabotools plugin;

	public WorldListener(Brabotools brabotools) {
		plugin = brabotools;
	}

	@EventHandler(priority = EventPriority.LOW)
    public void onChunkLoad(ChunkLoadEvent event) {
		for (DisplayTable table : plugin.getDisplayManager().getDisplayTables()) {
			try {
				if (event.getChunk().equals(table.getBlock().getChunk())) {
					table.respawn();
				}
			} catch (Exception e) {}
		}
    }
    
    @EventHandler(priority = EventPriority.LOW)
	public void onChunkUnload(ChunkUnloadEvent event) {
		for (DisplayTable table : plugin.getDisplayManager().getDisplayTables()) {
			try {
				if (event.getChunk().equals(table.getBlock().getChunk())) {
					table.remove();
				}
			} catch (Exception e) {}
		}
	}
    
}