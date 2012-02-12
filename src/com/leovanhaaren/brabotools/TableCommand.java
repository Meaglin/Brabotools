package com.leovanhaaren.brabotools;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class TableCommand implements CommandExecutor {
	
	@SuppressWarnings("unused")
	private Brabotools plugin;

	public TableCommand (Brabotools plugin) {
		this.plugin = plugin;
	}
	
	public boolean onCommand (CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "You must be a player!");
			return true;
	    }
	    Player player = (Player) sender;
	
		if(commandLabel.equalsIgnoreCase("table")) {
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("remove")) {
					//player.sendMessage(ChatColor.RED + "Removed " + removeTables() + " tables!");
				} else {
					//tableItem = Integer.parseInt(args[0]);
					//player.sendMessage(ChatColor.GOLD + "Table item is now set to: " + tableItem + "!");
				}
			}
			if (args.length == 0) {
				//player.sendMessage(ChatColor.RED + "Table error: Please provide a item id!");
						
				slapPlayer(player);
				
				return true;
			}
			if (args.length > 1) {
				player.sendMessage(ChatColor.RED + "Table error: Too many arguments!");
				return true;
			}
		}

		return false;
	}
	
	public static void slapPlayer(Player p) {
		p.setVelocity(new Vector(Math.random(), Math.random(), Math.random()));
	}
}
