package com.leovanhaaren.brabotools.util;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.leovanhaaren.brabotools.Config;
import com.leovanhaaren.brabotools.inventory.PlayerItem;

public class TntSnowball {

	public static void Explode(Player player, Entity entity) {
		if(Config.TNT_SNOWBALL_ENABLED && 
                        PlayerItem.Remove(player.getInventory(), new ItemStack(Material.TNT, 1))) {
	    	    entity.getWorld().createExplosion(entity.getLocation(), Config.TNT_SNOWBALL_RANGE);	        
		}
	}
}
