package com.leovanhaaren.brabotools.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.leovanhaaren.brabotools.util.Slap;

public class SlapCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(commandLabel.equalsIgnoreCase("slap")) {
			if(args.length < 1) return false;
			if(!(sender instanceof Player)) return false;
			
			Player target = Bukkit.getServer().getPlayer(args[0]);
			if(target != null) {
				Slap.slapPlayer(target);
				sender.sendMessage("Slapped!");
			} else {
				sender.sendMessage("Player is offline.");
			}
		}
		return true;
	}
}