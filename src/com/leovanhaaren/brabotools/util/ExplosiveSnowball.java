package com.leovanhaaren.brabotools.util;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.leovanhaaren.brabotools.inventory.Item;

public class ExplosiveSnowball {

	public static void Explode(Player player, Entity entity) {
        if(Item.Remove(player.getInventory(), new ItemStack(Material.TNT, 1))){
    	    entity.getWorld().createExplosion(entity.getLocation(), 5);
        }
	}
}
