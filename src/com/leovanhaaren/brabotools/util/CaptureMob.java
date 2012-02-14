package com.leovanhaaren.brabotools.util;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CaptureMob {
	
	static HashMap<String, Integer> creatureTypes = new HashMap<String, Integer>();

	@SuppressWarnings("deprecation")
	public static void Catch(Player p, Entity e) {
		registerMobTypes();
		
		String mob = getMobType(e);
		if(creatureTypes.containsKey(mob)) {
			short type = (short) creatureTypes.get(mob).intValue();
			
			p.getInventory().addItem(new ItemStack(383, 1, type));
			p.updateInventory();
			p.sendMessage(ChatColor.GOLD + "You caught a " + mob + "!");
			e.remove();
		} else {
			p.sendMessage(ChatColor.RED + "Sorry, but a " + mob + " can't be caught!");
		}

	}
	
	public static String getMobType(Entity e) {
		String s = e.getClass().getName();
		return s.substring(s.lastIndexOf('.') + 6);
	}
	
	public static void registerMobTypes() {
		creatureTypes.put("Creeper", 50);
		creatureTypes.put("Skeleton", 51);
		creatureTypes.put("Spider", 52);
		creatureTypes.put("Zombie", 54);
		creatureTypes.put("Slime", 55);
		creatureTypes.put("Ghast", 56);
		creatureTypes.put("PigZombie", 57);
		creatureTypes.put("Enderman", 58);
		creatureTypes.put("CaveSpider", 59);
		creatureTypes.put("Silverfish", 60);
		creatureTypes.put("Blaze", 61);
		creatureTypes.put("MagmaCube", 62);
		creatureTypes.put("Pig", 90);
		creatureTypes.put("Sheep", 91);
		creatureTypes.put("Cow", 92);
		creatureTypes.put("Chicken", 93);
		creatureTypes.put("Squid", 94);
		creatureTypes.put("Wolf", 95);
		creatureTypes.put("MushroomCow", 96);
		creatureTypes.put("Villager", 120);
	}
	
}
