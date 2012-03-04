package com.leovanhaaren.brabotools.util;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.leovanhaaren.brabotools.Brabotools;
import com.leovanhaaren.brabotools.inventory.ItemManager;

public class TntSnowball {
	
	private Brabotools plugin;

	public TntSnowball(Brabotools brabotools) {
		plugin = brabotools;
	}

	public void Explode(Player player, Entity entity) {		
		if (!plugin.getConfigManager().TNT_SNOWBALL_ENABLED) return;
		
		if (ItemManager.Remove(player.getInventory(), new ItemStack(Material.TNT, 1)))
			entity.getWorld().createExplosion(entity.getLocation(), plugin.getConfigManager().TNT_SNOWBALL_RANGE);
	}
	
}
