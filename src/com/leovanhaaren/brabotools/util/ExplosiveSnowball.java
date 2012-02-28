package com.leovanhaaren.brabotools.util;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.leovanhaaren.brabotools.inventory.Item;

public class ExplosiveSnowball {
	
	private static boolean 	enabled = false;
	private static int 		range	= 1;

	public static void Explode(Player player, Entity entity) {
		if(enabled) {
	        if(Item.Remove(player.getInventory(), new ItemStack(Material.TNT, 1))){
	    	    entity.getWorld().createExplosion(entity.getLocation(), range);
	        }
		}
	}
	
	public static void setEnabled(boolean e){
		enabled = e;
	}
	
	public static void setRange(int r){
		range = r;
	}
}
