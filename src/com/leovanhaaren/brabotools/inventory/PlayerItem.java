package com.leovanhaaren.brabotools.inventory;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PlayerItem {
	
	public static boolean Remove(Inventory inventory, ItemStack item) {
		ItemStack[] stacks = inventory.getContents();
		
		for(int i = 0; i < stacks.length;i ++) {
			ItemStack s = stacks[i];
			
			if(s == null) continue;
			if(s.getTypeId() == item.getTypeId() && s.getDurability() == item.getDurability()) {
				if(s.getAmount() >= item.getAmount()) {
					s.setAmount(s.getAmount() - item.getAmount());
					if(s.getAmount() <= 0) inventory.setItem(i, null);
					else inventory.setItem(i, s);
					return true;
				} else {
					item.setAmount(item.getAmount() - s.getAmount());
					s.setAmount(0);
					inventory.setItem(i, null);
					return false;
				}
			}
		}
		
		return false;
	}
	
	public static boolean Add(Inventory inventory, ItemStack item) {
		if(inventory.firstEmpty() == -1){
			inventory.addItem(item);
			return true;
		}
		
		return false;
	}
}
