package com.leovanhaaren.brabotools.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.leovanhaaren.brabotools.util.Explode;

public class ExplodeCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(commandLabel.equalsIgnoreCase("explode")) {
			if(args.length < 1) return false;
			if(!(sender instanceof Player)) return false;
			
			Player target = Bukkit.getServer().getPlayer(args[0]);
			if(target != null) {
				Explode.explodePlayer(target);
				sender.sendMessage("Exploded player.");
			} else {
				sender.sendMessage("Player is offline.");
			}
		}
		return true;
	}
}