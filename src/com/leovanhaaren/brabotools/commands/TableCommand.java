package com.leovanhaaren.brabotools.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.leovanhaaren.brabotools.display.DisplayTable;
import com.leovanhaaren.brabotools.util.Chat;

public class TableCommand implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(commandLabel.equalsIgnoreCase("table")) {
			if(args.length > 1) return false;
			if(!(sender instanceof Player)) return false;
			
			if(args.length == 1) {
	            try {
	            	int item = Integer.parseInt(args[0]);
	            	
	            	DisplayTable.setPrefferedItem((Player) sender, item);
	            }
	            catch(NumberFormatException e) {
					Chat.errorMessage((Player) sender, "Invalid argument.");
	            }
			}
		}
		return true;
	}
	
}
