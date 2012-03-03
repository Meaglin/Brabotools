package com.leovanhaaren.brabotools.listener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import com.leovanhaaren.brabotools.Brabotools;
import com.leovanhaaren.brabotools.util.DisplayManager;
import com.leovanhaaren.brabotools.util.DisplayTable;

public class PlayerListener implements Listener {

	private Brabotools plugin;

	public PlayerListener(Brabotools brabotools) {
		plugin = brabotools;
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.isCancelled()) return;
        
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (!plugin.getConfigManager().DISPLAY_TABLE_ENABLED) return;
			
			Block block = event.getClickedBlock();
			Player player = event.getPlayer();
			DisplayManager manager = plugin.getDisplayManager();

			if (!player.isSneaking()) return;
			
			if (!block.getRelative(BlockFace.UP).getType().equals(Material.AIR)) return;
			
			if (event.getBlockFace() != BlockFace.UP) return;
			
			if (!plugin.canUse(player, "displayTable")) return;

			if (manager.getTableByBlock(block) == null) {
				ItemStack item = player.getItemInHand();
				if (item.getType() == Material.AIR) return;

				if (!manager.isTableBlock(block)) return;

				manager.createDisplayTable(player, block);
			} else {
				manager.removeDisplayTable(player, block);
			}
			event.setCancelled(true);

		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
		if (event.isCancelled()) return;

		for (DisplayTable table : plugin.getDisplayManager().getDisplayTables()) {
			try {
				 if (table.getBlock().equals(event.getBlockClicked())) {
					 event.setCancelled(true);
					 table.respawn();
				}
			} catch (Exception e) {
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        if (event.isCancelled()) return;
        
		for (DisplayTable table : plugin.getDisplayManager().getDisplayTables()) {
			try {
				if (table.getItem().equals(event.getItem())) {
					table.getItem().setPickupDelay(2500);
					event.setCancelled(true);
				}
			} catch (Exception e) {
			}
		}
	}
}