package com.leovanhaaren.brabotools.util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Chat {

	public static void playerMessage(Player p, String message) {
		p.sendMessage(ChatColor.DARK_AQUA + message);
	}

	public static void succesMessage(Player p, String message) {
		p.sendMessage(ChatColor.GREEN + message);
	}
	
	public static void errorMessage(Player p, String message) {
		p.sendMessage(ChatColor.RED + message);
	}

}