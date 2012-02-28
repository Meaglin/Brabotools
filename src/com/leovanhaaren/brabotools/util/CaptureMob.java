package com.leovanhaaren.brabotools.util;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.leovanhaaren.brabotools.inventory.Item;

public class CaptureMob {

	@SuppressWarnings("deprecation")
	public static void Catch(Player p, LivingEntity e) {
        for (EggType eggType : EggType.values()) {
            if (eggType.isInstance(e)) {
                if (eggType.isEnabled()) {
                	ItemStack cost = new ItemStack(371, 1);
                	if(Item.Remove(p.getInventory(), cost)) {
	                	if(getSucces()) {
	                        e.remove();
	                        ItemStack item = new ItemStack(383, 1, (short) eggType.getId());
	                        p.getWorld().dropItem(e.getLocation(), item);
	                        String mob = eggType.getCreatureType().getName();
	                        p.sendMessage(ChatColor.GOLD + mob + " was caught.");
	                    	p.updateInventory();
	                        break;
	                	}
                	} else {
                    	p.sendMessage(ChatColor.RED + "Sorry, gold nuggets are required.");
                    	break;
                    }
                }  else {
                	p.sendMessage(ChatColor.RED + "Sorry, that mob can't be caught.");
                	break;
                }
            }
        }
	}
	
	public static boolean getSucces() {
		Random r = new Random();
		if(r.nextDouble() > (0.8) ) {
			return true;
		}
		return false;
	}
    
}
